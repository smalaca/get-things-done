package com.smalaca.gtd.tests.annotation;

import com.smalaca.gtd.client.rest.ProjectsManagementClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SystemTest
@SpringBootTest
@Import({ProjectsManagementClient.class})
@AutoConfigureMockMvc
public @interface RestControllerTest {
}
