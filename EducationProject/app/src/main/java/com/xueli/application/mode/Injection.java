package com.xueli.application.mode;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.xueli.application.mode.user.UserRepository;

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

    private UserRepository userRepository;

    /**
     * @param context ApplicationModule
     * @return UserRepository
     */
    public UserRepository provideUserRepository(@NonNull Context context) {
        return UserRepository.getRepository(context);
    }

}
