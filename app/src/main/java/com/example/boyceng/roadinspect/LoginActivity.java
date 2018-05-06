package com.example.boyceng.roadinspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private EditText user;
	private EditText pwd;
	private Button loginBtn;
	private Button btn_register;
	private String username = "";
	private String pass = "";
	private Context mContext;
	private DatabaseHelper d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		mContext = this;
		d = new DatabaseHelper(getApplicationContext());

		user = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(loginClick);
   
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(mIntent);
			}
		});
		user.setText("");
		pwd.setText("");
	}
	
	OnClickListener loginClick = new OnClickListener() {
		public void onClick(final View v) {
			if(checkEmpty()){
				if(d.check(username, pass)){
					Intent i = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(),
							getString(R.string.remain_error),Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	private boolean checkEmpty(){
		username = user.getText().toString().trim();
		pass = pwd.getText().toString().trim();
        boolean ret = false;
		if(username.equals("")||pass.equals("")){
			Toast.makeText(getApplicationContext(),
					getString(R.string.remain_empty),Toast.LENGTH_SHORT).show();
			ret = false;
		}else{
			ret = true;
		}
        return ret;
	}
}
