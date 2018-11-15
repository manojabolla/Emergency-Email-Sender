package com.vj.emergencymail;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity {

	SharedPreferences sp;
	Editor spedi;
	String name;
	String pin;
	String[] frnds = new String[4];
	boolean isAddLoc, isAddTime;
	
	AlertDialog loginDialog;
	AlertDialog.Builder loginBuilder;
	EditText pass;
	
	Intent navi;
	LocationManager lm;
	Location curLoc;
	SmsManager smsMan;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        navi = new Intent(this, PrefActivity.class);
        smsMan = SmsManager.getDefault();
        
        // building dialog for getting password to login
        pass = new EditText(this);
        
        pass.setSingleLine();
        pass.setHint("4 digit number");
        pass.requestFocus();
        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pass.setInputType(InputType.TYPE_CLASS_NUMBER);
        pass.setImeOptions(EditorInfo.IME_ACTION_DONE);
        pass.setImeActionLabel("Login", EditorInfo.IME_ACTION_DONE);
        
        loginBuilder = new AlertDialog.Builder(this);
        loginBuilder.setCancelable(false);
        loginBuilder.setTitle("Login");
        loginBuilder.setMessage("Enter PIN for Login: ");
        loginBuilder.setView(pass);
        loginBuilder.setPositiveButton("Login", onLogin);
        loginBuilder.setNegativeButton("Cancel", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
        });
        loginDialog = loginBuilder.create();
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        spedi = sp.edit();
        
        pin = sp.getString("pin", null);
        
        if(pin != null)
        	loginDialog.show();
        else
        {
        	Toast.makeText(this, "Configure your application for the first time", Toast.LENGTH_LONG).show();
        	startActivity(navi);
        }	
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	sp = PreferenceManager.getDefaultSharedPreferences(this);
        spedi = sp.edit();
        name = sp.getString("name", "");
        pin = sp.getString("pin", null);
        for( int i = 1; i <= 3; i++ )
        	frnds[i] = sp.getString("frnd" + i, null);
        isAddLoc = sp.getBoolean("isAddLoc", false);
        isAddTime = sp.getBoolean("isAddTime", false);
		super.onResume();
	}



	DialogInterface.OnClickListener onLogin = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			if(pass.getText().toString().equalsIgnoreCase(pin))
				arg0.dismiss();
			else
			{
				Toast.makeText(HomeActivity.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		startActivity(navi);
		return true;
	}
	
	public void onEmergency(View v)
	{
		// building message..
		String msg = "";
		StringBuilder sms = new StringBuilder(); 
		switch(v.getId())
		{
			case R.id.btnAcc:
				msg = "Hey, I'am " + name + " met an Accident\n";
				break;
				
			case R.id.btnAtt:
				msg = "Hey, I'am " + name + " caused by Heart Attack\n";
				break;
				
			case R.id.btnLost:
				msg = "Hey, I'm " + name + " get Lost somewhere\n";
				break;
				
			case R.id.btnThief:
				msg = "Hey, I'm " + name + " threatening by a Thief\n";
				break;	
		}
		sms.append(msg);
		sms.append("Please Help me\n");
		if(isAddLoc)
		{
			lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			curLoc = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			sms.append("http://maps.google.fr/maps?f=q&source=s_q&hl=fr&geocode=&q="
					+Double.toString(curLoc.getLatitude()) + "," + Double.toString(curLoc.getLongitude())+"\n" );
		}
		if(isAddTime)
			sms.append("Time: " + SimpleDateFormat.getInstance().format(new Date()));
		
		// sending sms....
		for( int i = 1; i <= 3; i++ )
		{
			if(frnds[i] != null)
				smsMan.sendTextMessage(frnds[i], null, sms.toString(), null, null);
		}
		Toast.makeText(this, "Alert passed to your friends\n\n" + sms, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);    	
    	builder.setTitle("Exit")
    		.setMessage("Are you sure do you want to Exit?")
    		.setPositiveButton("Yes", newdialog)
    		.setNegativeButton("No", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}

	DialogInterface.OnClickListener newdialog = new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			finish();
		}		
	};
}
