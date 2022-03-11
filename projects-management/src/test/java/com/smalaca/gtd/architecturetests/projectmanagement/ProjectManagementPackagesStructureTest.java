package com.smalaca.gtd.architecturetests.projectmanagement;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.projectManagementClasses;
import static com.smalaca.gtd.architecturetests.packages.Dependency.apacheCommonsPackages;
import static com.smalaca.gtd.architecturetests.packages.Dependency.googleCommonPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.projectManagement;
import static com.smalaca.gtd.architecturetests.packages.Java.javaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.jpaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.transactionPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springBeansPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springContextPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springDataPackages;
import static com.smalaca.gtd.architecturetests.packages.SpringFramework.springStereotypesPackages;
import static com.smalaca.gtd.architecturetests.packages.StaticAnalysis.findbugsPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class ProjectManagementPackagesStructureTest {
    private static final String DOMAIN = projectManagement() + ".domain..";
    private static final String APPLICATION = projectManagement() + ".application..";
    private static final String QUERY = projectManagement() + ".query..";

    @Test
    void domainShouldBeIndependent() {
        classes().that()
                .resideInAPackage(DOMAIN)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), jpaPackages(), apacheCommonsPackages(), findbugsPackages(),
                        DOMAIN)

                .check(projectManagementClasses());
    }
    @Test
    void applicationShouldDependOnlyOnCommand() {
        classes().that()
                .resideInAPackage(APPLICATION)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), transactionPackages(), springContextPackages(), springBeansPackages(),
                        APPLICATION, DOMAIN)

                .check(projectManagementClasses());
    }

    @Test
    void queryShouldBeIndependent() {
        classes().that()
                .resideInAPackage(QUERY)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), jpaPackages(), googleCommonPackages(), springDataPackages(), springStereotypesPackages(),
                        findbugsPackages(),
                        QUERY)

                .check(projectManagementClasses());
    }
}
