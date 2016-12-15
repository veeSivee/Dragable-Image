package com.vi.birthdaygift.slide;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.vi.birthdaygift.R;

/**
 * Created by taufiqotulfaidah on 11/15/16.
 */

public class CreateNewSlidePresenter implements CreateNewSlideContract.Presenter{

    private CreateNewSlideContract.View view;

    private Context context;

    public CreateNewSlidePresenter(CreateNewSlideContract.View view, Context context){
        this.view = view;
        this.context = context;
        view.setPresenter(this);
    }

    @Override
    public void newImageClick(int typeOfGallery) {
        view.openGallery(typeOfGallery);
    }

    @Override
    public void newTextClick() {
        view.createNewText();
    }

    @Override
    public void resultChoosePhoto(String pathImage) {
        view.showImage(pathImage);
    }

    @Override
    public void imageOnTouch(View view, MotionEvent motionEvent) {
        this.view.moveObject(view,motionEvent);
    }

    @Override
    public void textOnTouch(View view, MotionEvent motionEvent) {
        this.view.moveText(view,motionEvent);
    }

    @Override
    public void onClickSoundButton(int typeOfGallery) {
        view.openGallery(typeOfGallery);
    }

    @Override
    public void resultChooseVideo(String pathVideo) {
        view.playVideo(pathVideo);
    }

    @Override
    public void imageCreated() {
        view.showNextStep(context.getString(R.string.message_add_text));
    }

    @Override
    public void textCreated() {
        view.showNextStep(context.getString(R.string.message_add_sound));
    }

    @Override
    public void soundChoosen() {

    }

    @Override
    public void fileSaved() {
        view.showNextStep(context.getString(R.string.message_create_new_sheet));
    }

    @Override
    public void startActivity() {
        view.showNextStep(context.getString(R.string.message_add_image));
    }
}
