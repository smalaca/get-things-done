package com.smalaca.gtd.architecturetests.packages;

public class Dependency {

    public static String hibernateConstrains() {
        return allPackagesIn("org.hibernate.validator.constraints");
    }

    public static String apacheCommons() {
        return allPackagesIn("org.apache.commons.lang3");
    }

    public static String googleCommon() {
        return allPackagesIn("com.google.common");
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
