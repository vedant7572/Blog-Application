package com.vedant.blogApp.service;

import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;



    @Test
    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "dasdddtan",
            "ved"
    })
    public void testFindByUserName(String userName){
        assertNotNull(userRepository.findByUserName(userName),"failed for "+userName);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,4,6",
            "5,6,1"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b,"failed for "+ expected);
    }
}
