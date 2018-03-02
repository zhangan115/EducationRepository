package com.education.application.mode;

/**
 * 提供 repository
 * Created by pingan on 2017/12/2.
 */

public class Injection {

    private static Injection injection;

    private Injection() {

    }

    public static Injection getInjection() {
        if (injection == null) {
            injection = new Injection();
        }
        return injection;
    }

}
