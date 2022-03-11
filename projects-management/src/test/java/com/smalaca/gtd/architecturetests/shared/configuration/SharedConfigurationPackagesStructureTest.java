package com.smalaca.gtd.architecturetests.shared.configuration;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.sharedConfigurationClasses;
import static com.smalaca.gtd.architecturetests.packages.Dependency.apacheCommons;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedConfigurationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedLibrariesValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.javaPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springHttpPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springWebPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class SharedConfigurationPackagesStructureTest {
    @Test
    void shouldBeIndependent() {
        classes().that()
                .resideInAPackage(sharedConfigurationPackages())
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), apacheCommons(),
                        springWebPackages(), springValidationPackages(), springHttpPackages(),
                        sharedLibrariesValidationPackages(),

                        sharedConfigurationPackages())

                .check(sharedConfigurationClasses());
    }
}
