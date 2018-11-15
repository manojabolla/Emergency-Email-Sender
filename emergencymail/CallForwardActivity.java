package com.vj.emergencymail;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CallForwardActivity extends Activity 
{
	 Button buttonCallForwardOn;
	 Button buttonCallForwardOff;
	 EditText et;
	  
	 @Override
	 protected void onCreate(Bundle savedInstanceState) 
	 {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_call_forward);
	   et=(EditText)findViewById(R.id.editText1);
	   
	  buttonCallForwardOn = (Button) findViewById(R.id.buttonCallForwardOn);
	  buttonCallForwardOn.setOnClickListener(new View.OnClickListener()
	  {
	   public void onClick(View v)
	   {
		   String aa=et.getText().toString();
	    callforward("*21*"+"'"+aa+"'+#"); // 0123456789 is the number you want to forward the calls.
	   }
	  });
	   
	  buttonCallForwardOff = (Button) findViewById(R.id.buttonCallForwardOff);
	  buttonCallForwardOff.setOnClickListener(new View.OnClickListener()
	  {
	   public void onClick(View v)
	   {
	    callforward("#21#");
	   }
	  });
	 }
	  
	 private void callforward(String callForwardString)
	    {
	        PhoneCallListener phoneListener = new PhoneCallListener();
	        TelephonyManager telephonyManager = (TelephonyManager)
	         this.getSystemService(Context.TELEPHONY_SERVICE);
	        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	               
	        Intent intentCallForward = new Intent(Intent.ACTION_CALL);
	        Uri mmiCode = Uri.fromParts("tel", callForwardString, "#");
	        intentCallForward.setData(mmiCode);
	        intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intentCallForward);
	    }
	  
	 private class PhoneCallListener extends PhoneStateListener 
	 {
	        private boolean isPhoneCalling = false;        
	 
	        @Override
	        public void onCallStateChanged(int state, String incomingNumber) 
	        {
	            if (TelephonyManager.CALL_STATE_RINGING == state)
	            {
	                // phone ringing
	            }
	 
	            if (TelephonyManager.CALL_STATE_OFFHOOK == state) 
	            {
	                // active
	                isPhoneCalling = true;
	            }
	 
	            if (TelephonyManager.CALL_STATE_IDLE == state) 
	            {
	                // run when class initial and phone call ended, need detect flag
	                // from CALL_STATE_OFFHOOK
	                if (isPhoneCalling)
	                {
	                    // restart app
	                    Intent i = getBaseContext().getPackageManager()
	                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
	                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                    startActivity(i);
	                    isPhoneCalling = false;
	                }
	            }
	        }
	    }
	}
