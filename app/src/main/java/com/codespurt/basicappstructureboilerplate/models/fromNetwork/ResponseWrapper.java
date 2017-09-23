package com.codespurt.basicappstructureboilerplate.models.fromNetwork;

import lombok.Data;

@Data
public class ResponseWrapper {

    // volley
    private String name;
    private String email;
    private Phone phone;

    // retrofit
    private String date;
    private String horoscope;
    private String sunsign;
}