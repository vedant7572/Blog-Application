package com.vedant.blogApp.api.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class WeatherResponse{

    public Current current;
    @Data
    public static class Condition{
        public String text;
        public int code;
    }

    @Data
    public static class Current{
        public double temp_c;
        public Condition condition;

    }







}







