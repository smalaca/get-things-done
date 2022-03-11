package com.smalaca.gtd.architecturetests.packages;

public class StaticAnalysis {
    private StaticAnalysis() {}

    public static String findbugsSuppression() {
        return allPackagesIn("edu.umd.cs.findbugs.annotations");
    }

    private static String allPackagesIn(String packageName) {
        return packageName + "..";
    }
}
