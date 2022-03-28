package com.smalaca.gtd.architecturetests.shared.configuration;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.allClasses;
import static com.smalaca.gtd.architecturetests.GtdClasses.sharedConfigurationClasses;
import static com.smalaca.gtd.architecturetests.packages.Dependency.apacheCommonsPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedConfigurationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedLibrariesValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.javaPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springHttpPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springWebPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@ArchitectureTest
class SharedConfigurationPackagesStructureTest {
    @Test
    void shouldBeIndependent() {
        classes().that()
                .resideInAPackage(sharedConfigurationPackages())
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), apacheCommonsPackages(),
                        springWebPackages(), springValidationPackages(), springHttpPackages(),
                        sharedLibrariesValidationPackages(),

                        sharedConfigurationPackages())

                .because("0001-component-based-architecture.md")
                .check(sharedConfigurationClasses());
    }

    @Test
    void otherContextsShouldNotDependOnClassesFromSharedConfiguration() {
        noClasses().that()
                .resideOutsideOfPackage(sharedConfigurationPackages())
                .should().dependOnClassesThat()
                .resideInAPackage(sharedConfigurationPackages())

                .because("0001-component-based-architecture.md")
                .check(allClasses());
    }
}
