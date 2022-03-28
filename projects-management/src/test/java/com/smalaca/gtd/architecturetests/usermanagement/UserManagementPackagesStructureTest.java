package com.smalaca.gtd.architecturetests.usermanagement;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.allClasses;
import static com.smalaca.gtd.architecturetests.GtdClasses.userManagementClasses;
import static com.smalaca.gtd.architecturetests.packages.Dependency.hibernateConstrainsPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedLibrariesValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.userManagement;
import static com.smalaca.gtd.architecturetests.packages.Gtd.userManagementPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.javaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.jpaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.validationPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springBeansPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springContextPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springDataPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springHttpPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springSecurityPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springStereotypesPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springWebPackages;
import static com.smalaca.gtd.architecturetests.packages.StaticAnalysis.findbugsPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@ArchitectureTest
class UserManagementPackagesStructureTest {
    private static final String CONTROLLER = userManagement() + ".controller..";
    private static final String DOMAIN = userManagement() + ".domain..";
    private static final String PERSISTENCE = userManagement() + ".persistence..";

    @Test
    void controllersShouldDependOnDomain() {
        classes().that()
                .resideInAPackage(CONTROLLER)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), validationPackages(), springWebPackages(), springHttpPackages(), hibernateConstrainsPackages(),
                        sharedLibrariesValidationPackages(),
                        CONTROLLER, DOMAIN)

                .because("0004-user-management-layered-architecture.md")
                .check(userManagementClasses());
    }

    @Test
    void domainShouldDependOnPersistence() {
        classes().that()
                .resideInAPackage(DOMAIN)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), springContextPackages(), springBeansPackages(), springSecurityPackages(),
                        DOMAIN, PERSISTENCE)

                .because("0004-user-management-layered-architecture.md")
                .check(userManagementClasses());
    }

    @Test
    void persistenceShouldBeIndependent() {
        classes().that()
                .resideInAPackage(PERSISTENCE)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), jpaPackages(), springStereotypesPackages(), springDataPackages(), findbugsPackages(),
                        PERSISTENCE)

                .because("0004-user-management-layered-architecture.md")
                .check(userManagementClasses());
    }

    @Test
    void otherContextsShouldNotDependOnClassesFromUserManagementContext() {
        noClasses().that()
                .resideOutsideOfPackage(userManagementPackages())
                .should().dependOnClassesThat()
                .resideInAPackage(userManagementPackages())

                .because("0001-component-based-architecture.md")
                .check(allClasses());
    }
}
