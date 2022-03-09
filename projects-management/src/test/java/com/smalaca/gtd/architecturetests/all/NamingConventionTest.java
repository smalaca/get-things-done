package com.smalaca.gtd.architecturetests.all;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.allClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class NamingConventionTest {
    @Test
    void shouldNotContainWordsThatSuggestBrokenAbstractionForInterfaces() {
        classes().that().areInterfaces()
                .should().haveSimpleNameNotContaining("Interface")
                .because("it is a smell the abstraction is unnecessary")
                .check(allClasses());
    }

    @Test
    void shouldNotContainWordsThatSuggestBrokenAbstractionForClasses() {
        classes().that().areNotInterfaces()
                .should().haveSimpleNameNotContaining("Impl")
                .andShould().haveSimpleNameNotContaining("Base")
                .andShould().haveSimpleNameNotContaining("Abstract")
                .because("it is a smell the abstraction is unnecessary")
                .check(allClasses());
    }
}
