package com.automation.amazon;

import org.testng.Assert;

import java.util.Date;

public class LoggingTool {




        private static String suffix = "- - - - - - - - - - ";
        private static String banner = "* * * * * * * * * * ";
        private static String step = "==========================================================";
        private static String warning = "#######################";
        private static String error = "*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*";



        public static void beginTest(final String testName) {

            Date theTimeTheTestBegan = new Date();
            System.out.println(banner);
            System.out.println("Beginning test " + testName + " at " + theTimeTheTestBegan);
            System.out.println();
            System.out.println();
        }

        public static void endTest(final String testName) {
            Date theTimeTheTestEnded = new Date();
            System.out.println();
            System.out.println();
            System.out.println("Finishing test " + testName + " at " + theTimeTheTestEnded);
            System.out.println(banner);
        }

        public static void logAction(final String msg) {

            System.out.println("  " + msg);
            System.out.println("  " + suffix);
        }

        public static void logComment(final String msg) {

            System.out.println("        -> " + msg);
            System.out.println("  " + suffix);
        }

        public static void logStep(final String stepMsg) {
            System.out.println(step);
            System.out.println(stepMsg);
            System.out.println(step);

        }

        public static void logWarning(final String msg) {
            System.out.println(warning);
            System.out.println("### WARNING: " + msg);
            System.out.println(warning);
        }


        public static void logError(final String msg) {
            System.out.println(error);
            System.out.println("### Error: " + msg);
            System.out.println(error);
            Assert.fail();
        }
}
