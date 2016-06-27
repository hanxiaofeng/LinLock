package com.linheimx.app.linlock.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.model.Pic;
import com.linheimx.app.linlock.ui.ac.PicActivity;
import com.raizlabs.android.dbflow.list.FlowCursorList;

/**
 * Created by LJIAN on 2016/6/4.
 */
public class PicAdapter extends RecyclerView.Adapter<PicAdapter.MyHolder> {

    Activity _activity;
    FlowCursorList<Pic> _list;

    public PicAdapter(Activity _activity, FlowCursorList<Pic> _list) {
        this._activity = _activity;
        this._list = _list;
    }

    @Override
    public int getItemCount() {
        return _list.getCount();
    }

    @Override
    public void onBindViewHolder(final PicAdapter.MyHolder holder, int position) {

        final Pic pic = _list.getItem(position);

        holder.tv.setText(pic.content);

        Glide.with(_activity)
                .load(pic.url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_card)
                .into(holder.iv);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pic.url.contains("gif")) {
                    // gif 不点击
                    return;
                }
                // 跳转至图片activity
                // 1.intent
                Intent intent = new Intent(_activity, PicActivity.class);
                intent.putExtra("url", pic.url);

                //??????????????????  why dont work !!!!!!!!!!!!!!!
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        _activity, holder.iv, PicActivity.PIC_IV);
//                ActivityCompat.startActivity(_activity, intent, optionsCompat.toBundle());
                _activity.startActivity(intent);
                _activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public PicAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyHolder holder = new MyHolder(LayoutInflater.from(_activity).inflate(R.layout.item_pic, null));

        return holder;
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_title);
            iv = (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}
