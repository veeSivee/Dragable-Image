package com.vi.birthdaygift.slide;

import android.view.MotionEvent;
import android.view.View;

import com.vi.birthdaygift.launcher.LauncherContract;

/**
 * Created by taufiqotulfaidah on 11/15/16.
 */

public interface CreateNewSlideContract {

    interface Presenter{

        void newImageClick(int typeOfGallery);

        void newTextClick();

        void resultChoosePhoto(String pathImage);

        void imageOnTouch(android.view.View view, MotionEvent motionEvent);

        void textOnTouch(android.view.View view, MotionEvent motionEvent);

        void onClickSoundButton(int typeOfGallery);

        void resultChooseVideo(String pathVideo);

        void imageCreated();

        void textCreated();

        void soundChoosen();

        void fileSaved();

        void startActivity();
    }

    interface View{

        void setPresenter(Presenter presenter);

        void openGallery(int typeOfGallery);

        void showImage(String pathImage);

        void moveObject(android.view.View view, MotionEvent motionEvent);

        void createNewText();

        void moveText(android.view.View view, MotionEvent motionEvent);

        void playVideo(String pathVideo);

        void showNextStep(String nextStep);

        void saveImage();
    }
}
