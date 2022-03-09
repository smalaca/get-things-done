package com.smalaca.gtd.architecturetests.usermanagement;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.userManagementClasses;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedConfigurationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedLibrariesValidationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.userManagement;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchitectureTest
class UserManagementPackagesStructureTest {
    private static final String JAVA = "java..";
    private static final String JPA = "javax.persistence..";
    private static final String VALIDATION = "javax.validation..";

    private static final String HIBERNATE_CONSTRAINS = "org.hibernate.validator.constraints..";
    private static final String SPRING_STEREOTYPES = "org.springframework.stereotype..";
    private static final String SPRING_DATA = "org.springframework.data.repository..";
    private static final String SPRING_CONTEXT = "org.springframework.context.annotation..";
    private static final String SPRING_BEANS = "org.springframework.beans.factory.annotation..";
    private static final String SPRING_SECURITY = "org.springframework.security..";
    private static final String SPRING_WEB = "org.springframework.web..";
    private static final String SPRING_HTTP = "org.springframework.http..";

    private static final String FINDBUGS_SUPPRESSION = "edu.umd.cs.findbugs.annotations..";

    private static final String CONTROLLER = userManagement() + ".controller..";
    private static final String DOMAIN = userManagement() + ".domain..";
    private static final String PERSISTENCE = userManagement() + ".persistence..";

    @Test
    void controllersShouldDependOnDomain() {
        classes().that()
                .resideInAPackage(CONTROLLER)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, VALIDATION,
                        SPRING_WEB, SPRING_HTTP, HIBERNATE_CONSTRAINS,
                        sharedConfigurationPackages(), sharedLibrariesValidationPackages(),
                        CONTROLLER, DOMAIN)

                .check(userManagementClasses());
    }

    @Test
    void domainShouldDependOnPersistence() {
        classes().that()
                .resideInAPackage(DOMAIN)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA,
                        SPRING_CONTEXT, SPRING_BEANS, SPRING_SECURITY,
                        DOMAIN, PERSISTENCE)

                .check(userManagementClasses());
    }

    @Test
    void persistenceShouldBeIndependent() {
        classes().that()
                .resideInAPackage(PERSISTENCE)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        JAVA, JPA,
                        SPRING_STEREOTYPES, SPRING_DATA,
                        FINDBUGS_SUPPRESSION,
                        PERSISTENCE)

                .check(userManagementClasses());
    }
}
