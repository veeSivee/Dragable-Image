package com.vi.birthdaygift.launcher;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vi.birthdaygift.R;
import com.vi.birthdaygift.slide.CreateNewSlide;

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View{

    private int TIMER_TIME = 3000;
    private int TIMER_INTERVAL = 1000;
    private LauncherContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        presenter = new LauncherPresenter(this);
        presenter.start();
    }

    @Override
    public void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(TIMER_TIME, TIMER_INTERVAL) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                presenter.finish();
            }
        }.start();
    }

    @Override
    public void onFinishTimer() {
        gotoCreatePage();
    }

    @Override
    public void setPresenter(LauncherContract.Presenter presenter) {
        //this.presenter = presenter;
    }

    private void gotoCreatePage() {
        Intent intent = new Intent(this, CreateNewSlide.class);
        startActivity(intent);
        finish();
    }
}
