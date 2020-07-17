package com.ua.foxminded.task_13.controllers.textContexts;


import com.ua.foxminded.task_13.model.Faculty;
import com.ua.foxminded.task_13.services.FacultyServices;
import com.ua.foxminded.task_13.services.GroupServices;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class TestContext {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public FacultyServices facultyServices() {
        return Mockito.mock(FacultyServices.class);
    }
}
