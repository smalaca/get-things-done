package com.smalaca.gtd.projectmanagements.architecturetests;

import com.smalaca.gtd.projectmanagements.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.projectmanagements.architecturetests.GetThingsDoneClasses.projectClasses;
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
