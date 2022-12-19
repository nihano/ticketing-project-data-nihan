package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }

    //normally steps are adding bean in the container through @Bean annotation because we need ModelMapper obj. to convert entity to DTO
    //create a class annotate with @Configuration (@SpringBootApplication covers @Configuration)
    //Write a method which returns the object that you trying to add in the container
    //Annotate these method with @Bean

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}


