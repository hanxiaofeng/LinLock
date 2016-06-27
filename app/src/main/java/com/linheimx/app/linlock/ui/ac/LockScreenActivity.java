package com.linheimx.app.linlock.ui.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.model.Joke;
import com.linheimx.app.linlock.ui.custom.LRelativeLayout;
import com.linheimx.app.linlock.util.Device;
import com.linheimx.app.linlock.util.LockscreenUtil;
import com.linheimx.app.linlock.util.ShareUtils;
import com.linheimx.app.linlock.util.ToastUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import mehdi.sakout.fancybuttons.FancyButton;


public class LockScreenActivity extends AppCompatActivity {


    private SubsamplingScaleImageView _iv_background;
    private LRelativeLayout _rl_container;
    private int _device_width;
    private ShimmerTextView shimmerTextView;
    private Shimmer _shimmer;
    private FancyButton _fb_share;
    private FancyButton _btn_next;
    private TextView _tv_joke;
    private int _offset = 0;


    private View.OnSystemUiVisibilityChangeListener mSystemUiListener;
    private Context _context;
    private SendMassgeHandler _handler;


//    @Override
//    public void onAttachedToWindow() {
//        this.getWindow().setType(
//                WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        this.getWindow().addFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//        );
//
//        super.onAttachedToWindow();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        _context = this;

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        _device_width = metrics.widthPixels;

        _fb_share = (FancyButton) findViewById(R.id.btn_share);
        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        _rl_container = (LRelativeLayout) findViewById(R.id.rl_container);
        _iv_background = (SubsamplingScaleImageView) findViewById(R.id.iv_background);
        _rl_container.setOnTouchListener(touchListener);
        _tv_joke = (TextView) findViewById(R.id.tv_joke);
        _btn_next = (FancyButton) findViewById(R.id.btn_next);


        _tv_joke.setText("我在学校火了 [囧人糗事]\n" +
                "小学时上课爱睡觉，一次语文课老师布置作上课爱睡觉，一次语文课老师布置作上课爱睡觉，一次语文课老师布置作上课爱睡觉，一次语文课老师布置作上课爱睡觉，一次语文课老师布置作业写一篇作文，题目是“假如我是蜘蛛”。\n" +
                "下课了问了同学，晚上在家绞尽脑汁的写了一篇轰动全校的“假如我是只猪”。\n");


        _iv_background.setImage(ImageSource.resource(R.drawable.test1));

        _handler = new SendMassgeHandler();


        // shimmer
        _shimmer = new Shimmer();
        _shimmer.start(shimmerTextView);

        shimmerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("谁让你点我啦！小样儿~");
            }
        });

//        setLockGuard();


        _fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.share(_context, "" + _tv_joke.getText().toString());
            }
        });

        _btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextJoke();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        makeFullScreen();

        if (_offset == 0) {
            // load first joke data from db
            loadNextJoke();
        }
    }

    // Don't finish Activity on Back press
    @Override
    public void onBackPressed() {
        return;
    }

    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (keyCode == KeyEvent.KEYCODE_POWER)
                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {

            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void makeFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            final View decorView = getWindow().getDecorView();
            final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            decorView.setSystemUiVisibility(uiOptions);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    decorView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            decorView.setSystemUiVisibility(uiOptions);
                        }
                    }, 100);
                }
            });
        }
    }


    private void setLockGuard() {
        boolean isLockEnable = false;
        if (!LockscreenUtil.getInstance(_context).isStandardKeyguardState()) {
            isLockEnable = false;
        } else {
            isLockEnable = true;
        }


        boolean isSoftkeyEnable = LockscreenUtil.getInstance(_context).isSoftKeyAvail(this);
//        SharedPreferencesUtil.setBoolean(SwitchLockUtil.ISSOFTKEY, isSoftkeyEnable);
        if (!isSoftkeyEnable) {
            _handler.sendEmptyMessage(0);
        } else if (isSoftkeyEnable) {
            if (isLockEnable) {
                _handler.sendEmptyMessage(0);
            }
        }
    }


    private class SendMassgeHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finishLockscreenAct();
        }
    }

    private void finishLockscreenAct() {
        finish();
    }

    private void loadNextJoke() {
        Joke joke = SQLite.select().from(Joke.class).where().offset(_offset).limit(1).querySingle();
        if (joke != null) {
            _tv_joke.setText(joke.title + "\r\n" + joke.content);
            _offset++;
        } else {
            _offset = 0;
            joke = SQLite.select().from(Joke.class).where().offset(_offset).limit(1).querySingle();
            if (joke != null) {
                _tv_joke.setText(joke.title + "\r\n" + joke.content);
                _offset++;
            }
        }

    }


    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        private float _firstX;
        private float _lastX;
        private float _lastY;
        private float _moveX;

        private boolean flag_open = true;
        private boolean flag_bg_left = false;
        private boolean flag_bg_right = false;


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    _firstX = event.getX();
                    _lastX = event.getRawX();
                    _lastY = event.getRawY();


                    flag_bg_left = false;
                    flag_bg_right = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (flag_open) {
                        float currX = event.getRawX();
                        float currY = event.getRawY();

                        float intervalX = (currX - _lastX);
                        float intervalY = (currY - _lastY);

                        if (intervalX < 0 && _rl_container.getX() < 0) {
                            // 右---》左: 笑话，妹子
                            if (!flag_bg_right) {
                                _iv_background.setVisibility(View.VISIBLE);
                                flag_bg_right = true;

                            }
                        } else {
                            // 左---》右
                            if (!flag_bg_left) {
                                _iv_background.setVisibility(View.GONE);
                                flag_bg_left = true;
                            }
                        }

                        _rl_container.setX(_rl_container.getX() + intervalX);


                        _lastX = currX;
                        _lastY = currY;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    float currX = event.getRawX();

                    if ((currX - _firstX) < 0 && Math.abs(currX - _firstX) > (_device_width / 2)) {

                        ViewCompat.animate(_rl_container)
                                .translationX(-_device_width)
                                .setInterpolator(new DecelerateInterpolator())
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        _context.startActivity(new Intent(_context, MainActivity.class));

                                        _rl_container.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                _rl_container.setX(0);
                                            }
                                        }, 500);

                                    }
                                })
                                .start();
                        return true;
                    }

                    if ((currX - _firstX) > _device_width / 2) {
                        autoOff(_rl_container, true);
                    } else {
                        autoOff(_rl_container, false);
                    }

                    break;
                default:
                    break;
            }

            return true;
        }
    };


    private void autoOff(View v, boolean isRight) {
        if (isRight) {

            ViewCompat.animate(v)
                    .translationX(_device_width)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            LockScreenActivity.this.finish();
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    })
                    .start();
        } else {
            ViewCompat.animate(v)
                    .translationX(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }
}
