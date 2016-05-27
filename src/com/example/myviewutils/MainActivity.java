package com.example.myviewutils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@ViewInject(R.id.tv_xxxx)
	private TextView tv;
	
	@ViewInject(R.id.tv_yyy)
	private TextView tvy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		
		Log.d("tag", "tv="+tv);
		Log.d("tag", "tvy="+tvy);
		
		
		Toast.makeText(this, "tv="+tv, Toast.LENGTH_LONG).show();
	}
	@OnClick(R.id.btn)
	private void method1(View view){
		Toast.makeText(this, "我被点击了", Toast.LENGTH_SHORT).show();
	}

}
