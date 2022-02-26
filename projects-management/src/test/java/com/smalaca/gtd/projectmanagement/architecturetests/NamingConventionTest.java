package com.smalaca.gtd.projectmanagement.architecturetests;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.projectmanagement.architecturetests.ProjectManagementClasses.projectClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class NamingConventionTest {
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
