package com.smalaca.gtd.architecturetests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import static com.smalaca.gtd.architecturetests.packages.Gtd.gtd;
import static com.smalaca.gtd.architecturetests.packages.Gtd.projectManagement;
import static com.smalaca.gtd.architecturetests.packages.Gtd.sharedConfigurationPackages;
import static com.smalaca.gtd.architecturetests.packages.Gtd.userManagement;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_JARS;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;

public class GtdClasses {
    public static JavaClasses allClasses() {
        return classesFrom(gtd());
    }

    public static JavaClasses projectManagementClasses() {
        return classesFrom(projectManagement());
    }

    public static JavaClasses userManagementClasses() {
        return classesFrom(userManagement());
    }

    public static JavaClasses sharedConfigurationClasses() {
        return classesFrom(sharedConfigurationPackages());
    }

    private static JavaClasses classesFrom(String packageName) {
        return new ClassFileImporter()
                .withImportOption(DO_NOT_INCLUDE_JARS)
                .withImportOption(DO_NOT_INCLUDE_TESTS)
                .importPackages(packageName);
    }
}
