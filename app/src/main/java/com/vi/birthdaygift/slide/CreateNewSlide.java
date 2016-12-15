package com.vi.birthdaygift.slide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vi.birthdaygift.R;
import com.vi.birthdaygift.utils.ConstantVar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class CreateNewSlide extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener,
        CreateNewSlideContract.View {

    private ImageView img;
    private ImageView ivDone;
    private ViewGroup mRrootLayout;
    private int _xDelta;
    private int _yDelta;
    float dX, dY;

    private TextView tvInfo;
    private ImageButton ivNewImage;
    private ImageButton ivNewText;
    private ImageButton ivHideKeyboard;
    private ImageButton ivVideo;
    private String selectedImagePath;

    private TextView tvNew;

    private MediaPlayer mediaPlayer;

    private CreateNewSlideContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_slide);

        init();

        presenter.startActivity();
    }

    private void init(){

        new CreateNewSlidePresenter(this, this);

        mRrootLayout = (ViewGroup) findViewById(R.id.rlBGift);

        tvInfo = (TextView)findViewById(R.id.tvInfo);

        ivNewImage = (ImageButton)findViewById(R.id.iv_new_image);
        ivNewText = (ImageButton)findViewById(R.id.imageButton3);
        ivHideKeyboard = (ImageButton)findViewById(R.id.hideKeyboard);
        ivVideo = (ImageButton)findViewById(R.id.imageButton4);

        ivNewImage.setOnClickListener(this);
        ivNewText.setOnClickListener(this);
        ivHideKeyboard.setOnClickListener(this);
        ivVideo.setOnClickListener(this);

        img = (ImageView)mRrootLayout.findViewById(R.id.imageView4);
        ivDone = (ImageView)findViewById(R.id.imageView2);
        ivDone.setOnClickListener(this);

        //img.setOnTouchListener(this);
        img.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        presenter.textOnTouch(view,motionEvent);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_new_image:
                presenter.newImageClick(ConstantVar.GALLERY_IMAGE);
                break;

            case R.id.imageButton3:
                presenter.newTextClick();
                break;

            case R.id.hideKeyboard:
                hideKeyboard();
                break;

            case R.id.imageButton4:
                //sound
                presenter.onClickSoundButton(ConstantVar.GALLERY_VIDEO);
                break;

            case R.id.imageView2:
                //blend image
                saveImage();
                break;

            default:
                Log.e("Image", "onCLick ");
                break;
        }
    }

    /*private void newImage(){
         ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ActionBar.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.drawable.lampion);
        imageView.bringToFront();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Image", "onCLick inside ");
                Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 1);
            }
        });
        imageView.setClickable(true);
        //imageView.setOnTouchListener(this);
        mRrootLayout.addView(imageView);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            switch (requestCode){
                case ConstantVar.REQUEST_IMAGE :
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    Log.e("Image", "Image loaded " + selectedImagePath);

                    presenter.resultChoosePhoto(selectedImagePath);
                    presenter.imageCreated();
                    //setImage(selectedImagePath);
                    break;

                case ConstantVar.REQUEST_VIDEO:
                    Uri selectedVideo = data.getData();
                    String pathVideo = getPath(selectedVideo);
                    presenter.resultChooseVideo(pathVideo);
                    break;
            }
        }
    }

    public String getPath(Uri uri) {

        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }

    @Override
    public void setPresenter(CreateNewSlideContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void openGallery(int typeOfGallery) {

        switch (typeOfGallery){
            case ConstantVar.GALLERY_IMAGE:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), ConstantVar.REQUEST_IMAGE);
                break;

            case ConstantVar.GALLERY_VIDEO:
                Intent intentVid = new Intent();
                intentVid.setType("video/*");
                intentVid.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentVid,
                        "Select Video"), ConstantVar.REQUEST_VIDEO);
                break;
        }
    }

    @Override
    public void showImage(String pathImage) {

        File imgFile = new  File(pathImage);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        img.setImageBitmap(myBitmap);
        img.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveObject(View view, MotionEvent motionEvent) {
        final int X = (int) motionEvent.getRawX();
        final int Y = (int) motionEvent.getRawY();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - layoutParams.leftMargin;
                _yDelta = Y - layoutParams.rightMargin;
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                break;

            case MotionEvent.ACTION_POINTER_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParam.leftMargin = X - _xDelta;
                layoutParam.topMargin = Y - _yDelta;
                //layoutParam.rightMargin = -250;
                //layoutParam.bottomMargin = Y -250;
                view.setLayoutParams(layoutParam);
                break;
        }
        mRrootLayout.invalidate();
    }

    @Override
    public void createNewText() {

        final TextView textView = new TextView(this);
        textView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setX(150);
        textView.setY(150);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLUE);
        textView.setText("Click and Edit Me");
        textView.bringToFront();

        tvNew = textView;

        if(Build.VERSION.SDK_INT < 23){
            textView.setTextAppearance(this, R.style.MtTextViewStyle_BoldRed_Big);
        } else {
            textView.setTextAppearance(R.style.MtTextViewStyle_BoldRed_Big);
        }
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                hideKeyboard();

                textView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        presenter.textOnTouch(view,motionEvent);
                        return false;
                    }
                });

                return false;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setCursorVisible(true);
                textView.setFocusableInTouchMode(true);
                textView.setInputType(InputType.TYPE_CLASS_TEXT);
                textView.requestFocus();
            }
        });

        mRrootLayout.addView(textView);

        presenter.textCreated();
    }

    @Override
    public void moveText(View view, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                break;
        }

        hideKeyboard();
    }

    private void hideKeyboard(){
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(this.getCurrentFocus() != null){
            inputManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void playVideo(String pathVideo) {
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(pathVideo)));
        mediaPlayer.start();
    }

    @Override
    public void showNextStep(String nextStep) {
        tvInfo.setText(nextStep);
    }

    @Override
    public void saveImage() {
        try {
            File imgSave = new File("/sdcard/bigift");
            if(!imgSave.exists()){
                imgSave.mkdirs();
            }

            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String filename = "Image-" + n + ".jpg";
            File myFile = new File(imgSave, filename);
            if(myFile.exists()) myFile.delete();

            FileOutputStream fileOutputStream = new FileOutputStream(myFile);

            Bitmap originalBitmap = getBitmap(selectedImagePath);

            Canvas canvas = new Canvas(originalBitmap);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(24);
            //paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

            String txt = tvNew.getText().toString();

            if(TextUtils.isEmpty(txt)){
                txt = "Yo Yo Yo ..... ";
            }
            Log.e("Image","Image Created ````````````" + txt + " :: " + _xDelta + "," + _yDelta);

            canvas.drawBitmap(originalBitmap,0,0,paint);
            canvas.drawText(txt, 100, 100, paint);

            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            presenter.fileSaved();

        }catch (Exception e){
            Log.e("Image", "Save Image Error . . " + e.toString());
        }
    }

    private Bitmap getBitmap(String photoPath){
        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return bitmap;*/

        Bitmap loadedBitmap = BitmapFactory.decodeFile(photoPath);
        Bitmap drawableBitmap = loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        return drawableBitmap;
    }
}
