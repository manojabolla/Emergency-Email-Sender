package com.vj.emergencymail;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Button b = (Button) findViewById(R.id.button1);
		Button b1 = (Button) findViewById(R.id.button2);
		b.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(MenuActivity.this, Emailsetting.class);

				startActivity(it);
			}
		});
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(MenuActivity.this,
						HomeActivity.class);

				startActivity(it);
			}
		});
	}

	
}
