package com.vj.emergencymail;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.text.InputType;
import android.widget.Toast;

public class PrefActivity extends PreferenceActivity{

	EditTextPreference pin;
	EditTextPreference frnd1;
	EditTextPreference frnd2;
	EditTextPreference frnd3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.prefs);
		
		pin = (EditTextPreference) this.findPreference("pin");
		frnd1 = (EditTextPreference) this.findPreference("frnd1");
		frnd2 = (EditTextPreference) this.findPreference("frnd2");
		frnd3 = (EditTextPreference) this.findPreference("frnd3");
		
		pin.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		frnd1.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
		frnd2.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
		frnd3.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
		
		pin.getEditText().setHint("4 - 8 digit number");
		frnd1.getEditText().setHint("10 digit mob no");
		frnd2.getEditText().setHint("10 digit mob no");
		frnd3.getEditText().setHint("10 digit mob no");
		
		frnd1.setOnPreferenceChangeListener(checkIsPhone);
		frnd2.setOnPreferenceChangeListener(checkIsPhone);
		frnd3.setOnPreferenceChangeListener(checkIsPhone);
	}
	
	Preference.OnPreferenceChangeListener checkIsPhone = new Preference.OnPreferenceChangeListener(){
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			if(newValue.toString().length() < 10)
			{
				Toast.makeText(PrefActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
				return false;
			}
			return true;
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(pin.getText() != null)
			super.onBackPressed();
		else
			Toast.makeText(this, "PIN can not be null", Toast.LENGTH_SHORT).show();
	}
	
}