package com.linheimx.app.linlock.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.api.ServiceGenerator;
import com.linheimx.app.linlock.api.xhdq.random_pic.ApiRandomPic;
import com.linheimx.app.linlock.api.xhdq.random_pic.PicResponse;
import com.linheimx.app.linlock.model.FunnyThing;
import com.linheimx.app.linlock.model.LinLockDB;
import com.linheimx.app.linlock.model.Pic;
import com.linheimx.app.linlock.model.Pic_Table;
import com.linheimx.app.linlock.ui.adapter.PicAdapter;
import com.linheimx.app.linlock.util.NetCheck;
import com.linheimx.app.linlock.util.ToastUtils;
import com.linheimx.app.rv.PullLoadMoreRecyclerView;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.yalantis.phoenix.PullToRefreshView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class PicFragment extends Fragment {

    @Bind(R.id.pull_to_refresh)
    PullLoadMoreRecyclerView pullToRefresh;

    PicAdapter _adapter;
    FlowCursorList<Pic> _list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pic_thing, container, false);

        ButterKnife.bind(this, rootView);

        _list = new FlowCursorList(SQLite.select().from(Pic.class).orderBy(Pic_Table.id, true));

        initRv();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void initRv() {

        pullToRefresh.setGridLayout(1);

        _adapter = new PicAdapter(getActivity(), _list);

        pullToRefresh.setAdapter(_adapter);

        pullToRefresh.setOnPullLoadMoreListener(new PullLoadMoreListener());
    }


    int size = 0;
    int start = 0;

    private void getDataFromNet() {

        ServiceGenerator.createService(ApiRandomPic.class)
                .get_OB()
                .subscribeOn(Schedulers.io())
                .map(new Func1<PicResponse, Boolean>() {
                    @Override
                    public Boolean call(final PicResponse picResponse) {

                        start = _list.getCount() - 1;
                        // 插入到数据库
                        DatabaseDefinition db = FlowManager.getDatabase(LinLockDB.class);
                        db.executeTransaction(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                for (Pic pic : picResponse.Result2Pic()) {
                                    pic.save(databaseWrapper);
                                }
                            }
                        });

                        size = picResponse.Result2Pic().size();

                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong("加载出错了，请检查您的网络！" + e == null ? "" : e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            pullToRefresh.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("test", "更多数据已经加载完成！");
                                    _list.refresh();
                                    _adapter.notifyItemRangeInserted(start, size);
                                    pullToRefresh.setPullLoadMoreCompleted();
                                }
                            },1000);

                        }
                    }
                });
    }


    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            // 1. 将old数据删除
            Delete.table(Pic.class);
            _list.clearCache();
            _list.refresh();
            _adapter.notifyDataSetChanged();

            // 2. get data from internet
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            if (NetCheck.isNetConnected()) {
                getDataFromNet();
            }
        }
    }

}
