package com.linheimx.app.linlock.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.api.ServiceMMGenerator;
import com.linheimx.app.linlock.api.mm.ApiGroup;
import com.linheimx.app.linlock.api.mm.ContentParser;
import com.linheimx.app.linlock.model.Group;
import com.linheimx.app.linlock.ui.adapter.MMQuickAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MMFragment extends Fragment {


    @Bind(R.id.rv)
    RecyclerView rv;

    MMQuickAdapter _adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pic_thing, container, false);

        ButterKnife.bind(this, rootView);

        initRv();

        ServiceMMGenerator.createService(ApiGroup.class)
                .getGroup("", 1)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<Group>>() {
                    @Override
                    public List<Group> call(String s) {
                        return ContentParser.ParserGroups(s, "");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Group>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Group> groups) {

                        _adapter.setNewData(groups);
                    }
                });


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void initRv() {

        StaggeredGridLayoutManager layoutManager
                = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        _adapter = new MMQuickAdapter(getActivity());
        _adapter.isFirstOnly(false);
        _adapter.openLoadAnimation();
        _adapter.openLoadAnimation(new AlphaInAnimation());
        rv.setAdapter(_adapter);
    }


}
