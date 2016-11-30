package com.yamankod.comp_9_lesscpuintensiveservice;

import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends Activity {
	private Button start,stop;
	private EditText etext;
	Intent myIntent; 
	IntentFilter myFilter;
	BroadcastReceiver myReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        etext = (EditText)findViewById(R.id.etext);
        myIntent = new Intent(this, Service.class);
        myFilter = new IntentFilter("filter");
        myReceiver = new receiver();
        registerReceiver(myReceiver, myFilter);        
        start.setOnClickListener(startlisten);
        stop.setOnClickListener(stoplisten);                
    };
   
   @Override 
    public void onDestroy() {
	   super.onDestroy();
    	try {
			stopService(myIntent);
			unregisterReceiver(myReceiver);
			etext.setText("My Service destroy!!!");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), 1).show();
		}
    };
    OnClickListener startlisten = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
	    		startService(myIntent);
	    		etext.setText("My Service Started!!");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), 1).show();
			}
		}
	};
   OnClickListener stoplisten = new OnClickListener() {
	   @Override
	   public void onClick(View v) {
			try {
	    		stopService(myIntent);
	    		etext.setText("My Service Stoppped!!!");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), 1).show();
			}
		}
   }; 		
   public class receiver extends BroadcastReceiver{
	   @Override
	   public void onReceive(Context context, Intent intent) {
		   String data = intent.getStringExtra("servicedata");
		   String now = "\n"+ data + " ---- " + new Date().toLocaleString(); 
		   etext.append(now);
		 
	   }
   }
}






