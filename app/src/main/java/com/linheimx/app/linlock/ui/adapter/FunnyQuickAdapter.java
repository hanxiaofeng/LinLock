package com.linheimx.app.linlock.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.model.FunnyThing;
import com.linheimx.app.linlock.ui.ac.WebActivity;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class FunnyQuickAdapter extends BaseQuickAdapter<FunnyThing> {

    public FunnyQuickAdapter(Context context) {
        super(context, R.layout.item_pic, new ArrayList<FunnyThing>());
    }

    @Override
    protected void convert(BaseViewHolder helper, final FunnyThing item) {

        helper.setText(R.id.tv_title,item.getTitle());
        helper.setImageUrl(R.id.iv_pic,item.getSourceurl());
        helper.setOnClickListener(R.id.ll_funy, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= WebActivity.newIntent(mContext,item.getUrl(),item.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

}
