package com.ua.foxminded.task_13.controllers.textContexts;


import com.ua.foxminded.task_13.services.GroupServices;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContextGroup {

    @Bean
    public GroupServices groupService() {
        return Mockito.mock(GroupServices.class);
    }
}
