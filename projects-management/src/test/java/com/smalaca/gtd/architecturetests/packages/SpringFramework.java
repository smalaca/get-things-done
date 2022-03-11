package com.smalaca.gtd.architecturetests.packages;

public final class SpringFramework {
    private SpringFramework() {}

    public static String springWebPackages() {
        return allPackagesIn(springFramework("web"));
    }

    public static String springHttpPackages() {
        return allPackagesIn(springFramework("http"));
    }

    public static String springValidationPackages() {
        return allPackagesIn(springFramework("validation"));
    }

    public static String springContextPackages() {
        return allPackagesIn(springFramework("context.annotation"));
    }

    public static String springBeansPackages() {
        return allPackagesIn(springFramework("beans.factory.annotation"));
    }

    public static String springStereotypesPackages() {
        return allPackagesIn(springFramework("stereotype"));
    }

    public static String springDataPackages() {
        return allPackagesIn(springFramework("data.repository"));
    }

    public static String springSecurityPackages() {
        return allPackagesIn(springFramework("security"));
    }

    private static String springFramework(String subPackage) {
        return "org.springframework." + subPackage;
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
