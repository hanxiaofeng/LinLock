package com.linheimx.app.linlock.ui.ac;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.ui.fragment.PicFragment;
import com.linheimx.app.linlock.ui.fragment.MMFragment;
import com.linheimx.app.linlock.util.ToastUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tab)
    SmartTabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("老司机锁屏");

        // fragment
        FragmentPagerItems pages = new FragmentPagerItems(this);
//        pages.add(FragmentPagerItem.of("妹子", MMFragment.class));
        pages.add(FragmentPagerItem.of("趣图", PicFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        viewpager.setAdapter(adapter);


        tab.setViewPager(viewpager);

        requestPermission();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                ToastUtils.showLong("todo...");
                return true;
            case R.id.menu_lock:
                SettingActivity.goThis(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 请求权限！
     */
    private void requestPermission() {
        RxPermissions.getInstance(this)
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            // 未授予

                        }
                    }
                });
    }

}
