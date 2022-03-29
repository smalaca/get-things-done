package com.smalaca.gtd.architecturetests.packages;

public final class Java {
    private Java() {}

    public static String javaPackages() {
        return allPackagesIn("java");
    }

    public static String jpaPackages() {
        return allPackagesIn("javax.persistence");
    }

    public static String validationPackages() {
        return allPackagesIn("javax.validation");
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
