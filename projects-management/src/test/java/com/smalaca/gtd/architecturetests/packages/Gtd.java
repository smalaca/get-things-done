package com.smalaca.gtd.architecturetests.packages;

public final class Gtd {
    private Gtd() {}

    public static String gtd() {
        return "com.smalaca.gtd";
    }

    public static String projectManagement() {
        return gtd() + ".projectmanagement";
    }

    public static String userManagement() {
        return gtd() + ".usermanagement";
    }

    public static String sharedConfigurationPackages() {
        return allPackagesIn(shared() + ".configuration");
    }

    public static String sharedLibrariesValidationPackages() {
        return allPackagesIn(shared() + ".libraries.validation");
    }

    private static String shared() {
        return gtd() + ".shared";
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
