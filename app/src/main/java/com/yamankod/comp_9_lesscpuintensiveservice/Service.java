package com.yamankod.comp_9_lesscpuintensiveservice;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Service extends android.app.Service {
	private boolean isRunning = true;
	private Thread myThread;
	private Intent myfilterresponse;
	private Notification nt;
	private NotificationManager ntmn;
	private int ntid = 1;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		ntmn = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Toast.makeText(getApplicationContext(),
				"My Service is created at: " + new Date().toLocaleString(),
				Toast.LENGTH_LONG).show();
		create_nt2();
	}
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		myThread = new Thread(new Runnable() {
			long startTime = System.currentTimeMillis();
			long tics = 0;
			@Override
			public void run() {
				for (int i = 0; (i < 60) & isRunning; i++) {
					try {
						tics = System.currentTimeMillis() - startTime;
						String msg = i + " value; " + tics;
						myfilterresponse = new Intent("filter");
						myfilterresponse.putExtra("servicedata", msg);
						sendBroadcast(myfilterresponse);
						Thread.sleep(1000);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), e.getMessage(),	1).show();
					}
				}
			}
		});
		myThread.start();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
		ntmn.cancel(ntid);
		Toast.makeText(getApplicationContext(),
				"My Service is destroyed at: " + new Date().toLocaleString(),Toast.LENGTH_SHORT).show();
	}


	//Api 23 ten itibaren setLatestEventInfo metodu kaldırıldıgı için yerni create_Nt2 metodu kullanıldı
	private void show_nt() {
		int icon = R.drawable.ic_launcher;
		CharSequence text = "Click me to show motificaton";
		long now = System.currentTimeMillis();
		nt = new Notification(icon, text, now);

		Intent ntIntent = new Intent(this, main.class);
		PendingIntent pdIntent = PendingIntent.getActivity(getApplicationContext(), 0, ntIntent, 0);
		Context cn = getApplicationContext();
		CharSequence nttitle = "This is Notification";
		CharSequence nttext = "Notification and Real Service";
		//nt.setLatestEventInfo(cn, nttitle, nttext, pdIntent);
		//ntmn.notify(ntid, nt);
	}



	private void create_nt2(){
		NotificationManager ntmn = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kegm.gov.tr"));
		PendingIntent pd_Intent =PendingIntent.getActivity(this, 0, intent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Notification Başlığı")
				.setContentText("Burası Notification gövde")
				.setTicker("Notificaiton çıktı! ")
				.setWhen(new Date().getTime())
				.setContentIntent(pd_Intent);
		ntmn.notify(ntid, mBuilder.build());
	}

}








