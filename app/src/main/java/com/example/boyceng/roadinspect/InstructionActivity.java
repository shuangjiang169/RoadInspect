package com.example.boyceng.roadinspect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructionActivity extends BaseActivity {

	public static final String FRUIT_NAME = "fruit_name";

	//public static final String FRUIT_IMAGE_ID = "fruit_image_id";
			//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instruction);
		Intent intent = getIntent();
		//这两行修改了 fruitName里修改标题
		String fruitName = "说明书&常见问题";
		//int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
		TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		collapsingToolbar.setTitle(fruitName);
	//	Glide.with(this).load(fruitImageId).into(fruitImageView);
		//String fruitContent = generateFruitContent(fruitName);
		//fruitContentText.setText(fruitContent);
	}

	/*private String generateFruitContent(String fruitName) {
		StringBuilder fruitContent = new StringBuilder();
		for (int i = 0; i < 500; i++) {
			fruitContent.append(fruitName);
		}
		return fruitContent.toString();
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
