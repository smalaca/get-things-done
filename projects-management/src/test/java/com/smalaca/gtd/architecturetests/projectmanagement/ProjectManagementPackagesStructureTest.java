package com.smalaca.gtd.architecturetests.projectmanagement;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.projectManagementClasses;
import static com.smalaca.gtd.architecturetests.packages.Dependency.apacheCommons;
import static com.smalaca.gtd.architecturetests.packages.Dependency.googleCommon;
import static com.smalaca.gtd.architecturetests.packages.Gtd.projectManagement;
import static com.smalaca.gtd.architecturetests.packages.Java.javaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.jpaPackages;
import static com.smalaca.gtd.architecturetests.packages.Java.transactionPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class ProjectManagementPackagesStructureTest {
    private static final String SPRING_CONTEXT = "org.springframework.context.annotation..";
    private static final String SPRING_BEANS = "org.springframework.beans.factory.annotation..";
    private static final String SPRING_STEREOTYPES = "org.springframework.stereotype..";
    private static final String SPRING_DATA = "org.springframework.data.repository..";

    private static final String FINDBUGS_SUPPRESSION = "edu.umd.cs.findbugs.annotations..";

    private static final String DOMAIN = projectManagement() + ".domain..";
    private static final String APPLICATION = projectManagement() + ".application..";
    private static final String QUERY = projectManagement() + ".query..";

    @Test
    void domainShouldBeIndependent() {
        classes().that()
                .resideInAPackage(DOMAIN)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), jpaPackages(), apacheCommons(),
                        FINDBUGS_SUPPRESSION,
                        DOMAIN)

                .check(projectManagementClasses());
    }
    @Test
    void applicationShouldDependOnlyOnCommand() {
        classes().that()
                .resideInAPackage(APPLICATION)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), transactionPackages(), SPRING_CONTEXT, SPRING_BEANS,
                        APPLICATION, DOMAIN)

                .check(projectManagementClasses());
    }

    @Test
    void queryShouldBeIndependent() {
        classes().that()
                .resideInAPackage(QUERY)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        javaPackages(), jpaPackages(), googleCommon(), SPRING_DATA, SPRING_STEREOTYPES,
                        FINDBUGS_SUPPRESSION,
                        QUERY)

                .check(projectManagementClasses());
    }
}
