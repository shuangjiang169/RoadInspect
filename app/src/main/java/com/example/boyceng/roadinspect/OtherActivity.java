package com.example.boyceng.roadinspect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OtherActivity extends BaseActivity{
//	private TextView myTextView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
       /*
		Intent intent = getIntent();
		String value = intent.getStringExtra("testIntent");
		myTextView = (TextView)findViewById(R.id.myTextView);
		myTextView.setText(value);*/
		
	}

}
