package com.vi.birthdaygift.launcher;

/**
 * Created by taufiqotulfaidah on 11/14/16.
 */

public class LauncherPresenter implements LauncherContract.Presenter{

    private LauncherContract.View view;

    public LauncherPresenter(LauncherContract.View view){
        this.view = view;
    }

    @Override
    public void start() {
        view.startTimer();
    }

    @Override
    public void finish() {
        view.onFinishTimer();
    }
}
