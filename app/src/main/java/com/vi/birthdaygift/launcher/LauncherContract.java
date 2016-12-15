package com.vi.birthdaygift.launcher;

import com.vi.birthdaygift.base.BaseView;

/**
 * Created by taufiqotulfaidah on 11/14/16.
 */

public interface LauncherContract {

    interface Presenter{

        void start();

        void finish();
    }

    interface View extends BaseView<Presenter>{

        void startTimer();

        void onFinishTimer();
    }
}
