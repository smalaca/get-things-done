package com.smalaca.gtd.architecturetests.projectmanagement;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.projectmanagement.ProjectManagementClasses.projectClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class PackageStructureTest {
    private static final String JAVA = "java..";
    private static final String JPA = "javax.persistence..";
    private static final String TRANSACTIONS = "javax.transaction..";
    private static final String APACHE_COMMONS = "org.apache.commons.lang3..";
    private static final String GOGGLE_COMMON = "com.google.common..";

    private static final String SPRING_CONTEXT = "org.springframework.context.annotation..";
    private static final String SPRING_BEANS = "org.springframework.beans.factory.annotation..";
    private static final String SPRING_STEREOTYPES = "org.springframework.stereotype..";
    private static final String SPRING_DATA = "org.springframework.data.repository..";

    private static final String FINDBUGS_SUPPRESSION = "edu.umd.cs.findbugs.annotations..";

    private static final String DOMAIN = "..domain..";
    private static final String APPLICATION = "..application..";
    private static final String QUERY = "..query..";

    @Test
    void domainShouldBeIndependent() {
        classes().that()
                .resideInAPackage(DOMAIN)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, JPA, APACHE_COMMONS,
                        FINDBUGS_SUPPRESSION,
                        DOMAIN)
                .check(projectClasses());
    }
    @Test
    void applicationShouldDependOnlyOnCommand() {
        classes().that()
                .resideInAPackage(APPLICATION)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, SPRING_CONTEXT, TRANSACTIONS, SPRING_BEANS,
                        APPLICATION, DOMAIN)
                .check(projectClasses());
    }

    @Test
    void queryShouldBeIndependent() {
        classes().that()
                .resideInAPackage(QUERY)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, JPA, GOGGLE_COMMON, SPRING_DATA, SPRING_STEREOTYPES,
                        FINDBUGS_SUPPRESSION,
                        QUERY)
                .check(projectClasses());
    }
}
