package com.example.boyceng.roadinspect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	private EditText user;
	private EditText pwd;
	private Button login_save;
	private String username = "";
	private String pass = "";
	private Context mContext;
	private DatabaseHelper d;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		mContext = this;
		d = new DatabaseHelper(getApplicationContext());
		user = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		login_save = (Button) findViewById(R.id.login_save);
		login_save.setOnClickListener(saveClick);
		user.setText("");
		pwd.setText("");
	}

	OnClickListener saveClick = new OnClickListener() {
		public void onClick(final View v) {
			if(checkEmpty()){
				    d.insertAdmin(username, pass);
					Toast.makeText(getApplicationContext(),
							getString(R.string.remain_register_succ),Toast.LENGTH_SHORT).show();
					finish();
			}
		}

	};

	private boolean checkEmpty() {
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
