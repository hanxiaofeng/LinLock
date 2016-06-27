package com.linheimx.app.linlock.ui.ac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.util.Isetting;
import com.linheimx.app.linlock.util.Prefs;
import com.linheimx.app.linlock.util.SwitchLockUtil;
import com.raizlabs.android.dbflow.sql.language.Condition;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.switch1)
    Switch switch1;

    Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        _context = this;

        switch1.setChecked(Prefs.getBoolean(Isetting.ST_LOCK_ENABLE, false));

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Prefs.putBoolean(Isetting.ST_LOCK_ENABLE, isChecked);
                if (isChecked) {
                    SwitchLockUtil.getInstance(_context).startLockscreenService();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    public static void goThis(Activity activity) {

        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }
}
