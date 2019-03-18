package com.anb.finalmoviecatalogue.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.api.RetroServer;
import com.anb.finalmoviecatalogue.data.model.Movie;
import com.anb.finalmoviecatalogue.data.model.MovieResponse;
import com.anb.finalmoviecatalogue.feature.main.MainActivity;
import com.anb.finalmoviecatalogue.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReleaseReceiver extends BroadcastReceiver {
    public ArrayList<MovieResponse> listMovie = new ArrayList<>();

    public static final int NOTIFICATION_ID = 101;
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TYPE = "extra_type";

    public ReleaseReceiver() {}

    @SuppressLint("CheckResult")
    @Override
    public void onReceive(Context context, Intent intent) {
        RetroServer.getRequestService()
                .getMovie(Constant.API_KEY, Constant.LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    ArrayList<Movie> movies = movieResponse.getResults();
                    for (Movie movie: movies) {
                        if (matchDate(movie.getRelease_date())){
                            int notifId = new Random().nextInt(300);
                            String title = movie.getTitle();
                            String message = movie.getOverview();
                            sendNotification(context, title, message, notifId);
                        }
                    }
                }, e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    boolean matchDate(String date){
        boolean match = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date releaseDate = sdf.parse(date);
            Date currentDate = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
            if (releaseDate.getTime() == currentDate.getTime()){
                match = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return match;
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "chanel_id";
            CharSequence name = "Channel Final Movie Catalogue";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, chanel_id);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uriTone);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, R.string.active_release_reminder, Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.deactive_release_reminder, Toast.LENGTH_SHORT).show();
    }
}
