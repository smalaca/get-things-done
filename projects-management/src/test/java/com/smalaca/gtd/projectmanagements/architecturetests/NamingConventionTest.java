package com.smalaca.gtd.projectmanagements.architecturetests;

import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.projectmanagements.architecturetests.GetThingsDoneClasses.projectClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class NamingConventionTest {
    @Test
    void shouldNotContainWordsThatSuggestBrokenAbstractionForInterfaces() {
        classes().that().areInterfaces()
                .should().haveSimpleNameNotContaining("Interface")
                .because("it is a smell the abstraction is unnecessary")
                .check(projectClasses());
    }

    @Test
    void shouldNotContainWordsThatSuggestBrokenAbstractionForClasses() {
        classes().that().areNotInterfaces()
                .should().haveSimpleNameNotContaining("Impl")
                .andShould().haveSimpleNameNotContaining("Base")
                .andShould().haveSimpleNameNotContaining("Abstract")
                .because("it is a smell the abstraction is unnecessary")
                .check(projectClasses());
    }
}
