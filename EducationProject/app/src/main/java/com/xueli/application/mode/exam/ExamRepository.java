package com.xueli.application.mode.exam;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xueli.application.mode.user.UserRepository;

/**
 *
 * Created by pingan on 2018/3/25.
 */

public class ExamRepository implements ExamDataSource {

    private static ExamRepository repository;

    private ExamRepository(@NonNull Context context) {

    }

    public static ExamRepository getRepository(Context context) {
        if (repository == null) {
            repository = new ExamRepository(context);
        }
        return repository;
    }
}
