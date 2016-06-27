package com.linheimx.app.linlock.ui.ac;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.util.RxUtils;
import com.linheimx.app.linlock.util.ShareUtils;
import com.linheimx.app.linlock.util.ToastUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PicActivity extends AppCompatActivity {

    public static final String PIC_IV = "iv";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imageView)
    ImageView imageView;

    PhotoViewAttacher _photoViewAttacher;
    String url;
    Activity _activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);

        _activity = this;



        /********************************* toobar ****************************/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // set white arrow
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        toolbar.setAlpha(0.7f);
        toolbar.setTitleTextColor(0xffffffff);

        ViewCompat.setTransitionName(imageView, PIC_IV);


        Intent intent = getIntent();
        url = intent.getStringExtra("url");


        Glide
                .with(this)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        _photoViewAttacher = new PhotoViewAttacher(imageView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        _photoViewAttacher.cleanup();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                RxUtils.saveImageAndGetPathObservable(this, url, url + "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.showLong(e.getMessage());
                            }

                            @Override
                            public void onNext(Object o) {
                                Uri uri = (Uri) o;
                                ShareUtils.shareImage(_activity, uri, "分享妹纸到...");
                            }
                        });
                return true;
            case R.id.action_save:
                RxUtils.saveImageAndGetPathObservable(this, url, url + "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.showLong(e.getMessage());
                            }

                            @Override
                            public void onNext(Object o) {
                                File appDir = new File(Environment.getExternalStorageDirectory(), "老司机");
                                String msg = "图片以及保存到老司机文件夹:" + appDir.getAbsolutePath();
                                ToastUtils.showShort(msg);
                            }
                        });

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
