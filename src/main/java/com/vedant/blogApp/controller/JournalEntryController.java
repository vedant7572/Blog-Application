package com.vedant.blogApp.controller;

import com.vedant.blogApp.entity.JournalEntry;
import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.service.JournalEntryService;
import com.vedant.blogApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    //controller calls the service which in turn uses the repo to do the operations on the db

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//
    //this will be put in the admin side

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all !=null && !all.isEmpty())
            return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(all, HttpStatus.NO_CONTENT);
    }

    //the journal entry will be stored inside the journal_entries collection
    //also the id of that entry will be stored in the journalEntries list of the user which will be passed as the path variable
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //user should only be able to see his entries ,
    // it should not be like you are ram apko hath lagi sham ki entry id aur app usse api bhej rahe ho
    //how-> we get the user from the authentication then we get all the journal entries of that user and compare
    //the id coming in the request with the ids of the journal entries and store them in a list
    //if that list is not empty meaning we have found the matching entry

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user=userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = Optional.ofNullable(journalEntryService.findById(myId));
            if(journalEntry.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    //we need to implement delete cascade
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryByIdAndUserName(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>( HttpStatus.OK);
    }

//    @DeleteMapping("id/{myId}")
//    public ResponseEntity<?> deleteEntryByIdAndUserName(@PathVariable ObjectId myId){
//        journalEntryService.deleteById(myId);
//        return new ResponseEntity<>( HttpStatus.OK);
//    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable ObjectId id,
            @PathVariable String userName,
            @RequestBody JournalEntry newEntry
    ){
        JournalEntry oldEntry=journalEntryService.findById(id);

        if(oldEntry!=null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty()? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty()? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
