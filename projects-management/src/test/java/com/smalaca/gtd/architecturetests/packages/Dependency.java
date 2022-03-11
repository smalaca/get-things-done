package com.smalaca.gtd.architecturetests.packages;

public final class Dependency {
    private Dependency() {}

    public static String hibernateConstrainsPackages() {
        return allPackagesIn("org.hibernate.validator.constraints");
    }

    public static String apacheCommonsPackages() {
        return allPackagesIn("org.apache.commons.lang3");
    }

    public static String googleCommonPackages() {
        return allPackagesIn("com.google.common");
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
