package com.anb.finalmoviecatalogue.feature.reminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.notification.DailyReceiver;
import com.anb.finalmoviecatalogue.notification.ReleaseReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderActivity extends AppCompatActivity {

    @BindView(R.id.sw_daily_reminder) Switch swDailyReminder;
    @BindView(R.id.sw_release_reminder) Switch swReleaseReminder;
    public DailyReceiver dailyReceiver;
    public ReleaseReceiver releaseReceiver;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public static final String PREF_NAME = "finalmoviecatalogue";
    public static final String TYPE_DAILY = "type_daily";
    public static final String TYPE_RELEASE = "type_release";
    public static final String DAILY_ACTIVE_STATUS = "daily_active_status";
    public static final String RELEASE_ACTIVE_STATUS = "release_active_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        dailyReceiver = new DailyReceiver();
        releaseReceiver = new ReleaseReceiver();

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        boolean daily_status = sharedPreferences.getBoolean(DAILY_ACTIVE_STATUS, false);
        boolean release_status = sharedPreferences.getBoolean(RELEASE_ACTIVE_STATUS, false);
        swDailyReminder.setChecked(daily_status);
        swReleaseReminder.setChecked(release_status);

        swReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sharedPreferences.edit();
                if (isChecked){
                    editor.putBoolean(RELEASE_ACTIVE_STATUS, true);
                    editor.apply();
                    releaseReminderOn();
                } else {
                    editor.putBoolean(RELEASE_ACTIVE_STATUS, false);
                    editor.apply();
                    releaseReminderOff();
                }
            }
        });

        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sharedPreferences.edit();
                if (isChecked){
                    editor.putBoolean(DAILY_ACTIVE_STATUS, true);
                    editor.apply();
                    dailyReminderOn();
                } else {
                    editor.putBoolean(DAILY_ACTIVE_STATUS, false);
                    editor.apply();
                    dailyReminderOff();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void releaseReminderOn() {
        String time = "08:00";
        String message = getResources().getString(R.string.release_movie_message);
        releaseReceiver.setAlarm(this, TYPE_RELEASE, time, message);
    }

    private void releaseReminderOff() {
        releaseReceiver.cancelAlarm(this);
    }

    private void dailyReminderOn() {
        String time = "07:00";
        String message = getResources().getString(R.string.daily_reminder);
        dailyReceiver.setAlarm(this, TYPE_DAILY, time, message);
    }

    private void dailyReminderOff() {
        dailyReceiver.cancelAlarm(this);
    }
}
