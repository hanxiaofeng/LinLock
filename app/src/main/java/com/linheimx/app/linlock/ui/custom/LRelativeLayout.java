package com.linheimx.app.linlock.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by LJIAN on 2016/5/6.
 */
public class LRelativeLayout extends RelativeLayout {


    public LRelativeLayout(Context context) {
        super(context);
    }

    public LRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float _lastX;
    private float _lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                _lastX = ev.getRawX();
//                _lastY = ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float currX = ev.getRawX();
//                float currY = ev.getRawY();
//
//                float dx = Math.abs(currX - _lastX);
//                float dy = Math.abs(currY - _lastY);
//
//                if (dx >= dy && dx > 0) {
//                    return true;
//                }
//
//                _lastX = currX;
//                _lastY = currY;
//                break;
//        }


        return super.onInterceptTouchEvent(ev);

//        return true;
    }
}
