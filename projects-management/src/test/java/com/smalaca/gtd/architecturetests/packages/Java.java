package com.smalaca.gtd.architecturetests.packages;

public class Java {
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

    public static String transactionPackages() {
        return allPackagesIn("javax.transaction");
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
