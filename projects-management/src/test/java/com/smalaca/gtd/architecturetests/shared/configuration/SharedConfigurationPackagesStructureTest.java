package com.smalaca.gtd.architecturetests.shared.configuration;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.sharedConfigurationClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class SharedConfigurationPackagesStructureTest {
    private static final String JAVA = "java..";
    private static final String APACHE_COMMONS = "org.apache.commons.lang3..";
    private static final String SPRING_WEB = "org.springframework.web..";
    private static final String SPRING_HTTP = "org.springframework.http..";
    private static final String SPRING_VALIDATION = "org.springframework.validation..";

    private static final String SHARED_CONFIGURATION = "com.smalaca.gtd.shared.configuration..";

    @Test
    void shouldBeIndependent() {
        classes().that()
                .resideInAPackage(SHARED_CONFIGURATION)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, APACHE_COMMONS,
                        SPRING_WEB, SPRING_VALIDATION, SPRING_HTTP,
                        SHARED_CONFIGURATION)
                .check(sharedConfigurationClasses());
    }
}
