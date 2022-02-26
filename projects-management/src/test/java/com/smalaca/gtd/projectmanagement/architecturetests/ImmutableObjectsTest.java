package com.smalaca.gtd.projectmanagement.architecturetests;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.projectmanagement.architecturetests.ProjectManagementClasses.projectClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class ImmutableObjectsTest {
    @Test
    void shouldAllImmutableObjectsHasGotFinalFields() {
        classes().that()
                .haveSimpleNameEndingWith("Dto")
                .or().haveSimpleNameEndingWith("Command")
                .or().haveSimpleNameEndingWith("Event")
                .should()
                .haveOnlyFinalFields()
                .check(projectClasses());
    }
}
