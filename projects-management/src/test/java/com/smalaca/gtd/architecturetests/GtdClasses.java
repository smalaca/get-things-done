package com.smalaca.gtd.architecturetests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_JARS;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;

public class GtdClasses {
    public static JavaClasses allClasses() {
        return classesFrom("com.smalaca.gtd");
    }

    public static JavaClasses projectManagementClasses() {
        return classesFrom("com.smalaca.gtd.projectmanagement");
    }

    private static JavaClasses classesFrom(String packageName) {
        return new ClassFileImporter()
                .withImportOption(DO_NOT_INCLUDE_JARS)
                .withImportOption(DO_NOT_INCLUDE_TESTS)
                .importPackages(packageName);
    }
}
