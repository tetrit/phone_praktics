package ru.mirea.aleev.mireaproject;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public	static	final	String	CHANNEL_ID	=	"ForegroundServiceChannel";
    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public	int	onStartCommand(Intent	intent,	int	flags,	int	startId)	{
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new	MediaPlayer.OnCompletionListener()	{
            public	void	onCompletion(MediaPlayer	mp)	{
                stopForeground(true);
            }
        });
        return	super.onStartCommand(intent,	flags,	startId);
    }
    @SuppressLint("ForegroundServiceType")
    @Override
    public	void	onCreate()	{
        super.onCreate();
        NotificationCompat.Builder	builder	=	new	NotificationCompat.Builder(this,	CHANNEL_ID)
                .setContentText("Playing....")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new	NotificationCompat.BigTextStyle()
                        .bigText("daydream in blue"))
                .setContentTitle("Сейчас играет:");
        int	importance	=	NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel	channel	= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,	"Student	FIO	Notification",	importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription("MIREA	Channel");
        }
        NotificationManagerCompat notificationManager	=	NotificationManagerCompat.from(this);
        assert channel != null;
        notificationManager.createNotificationChannel(channel);
        startForeground(1,	builder.build());
        mediaPlayer=	MediaPlayer.create(this,	R.raw.daydreaminblue);
        mediaPlayer.setLooping(false);
    }
    @Override
    public	void	onDestroy()	{
        stopForeground(true);
        mediaPlayer.stop();
    }

}