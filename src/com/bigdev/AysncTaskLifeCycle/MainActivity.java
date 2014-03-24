package com.bigdev.AysncTaskLifeCycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdev.AysncTaskLifeCycle.TaskFragment.DummyCallBacks;

public class MainActivity extends Activity implements DummyCallBacks {
	TextView tv;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Submit clicked",
						Toast.LENGTH_SHORT).show();
				FragmentManager fm = getFragmentManager();
				TaskFragment taskFragment = (TaskFragment) fm
						.findFragmentByTag("task");
				if (taskFragment == null) {
					TaskFragment frag = new TaskFragment();
					fm.beginTransaction().add(frag, "task").commit();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgressUpdate(int percent) {
		tv.setText("Loading " + percent + " %");
	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	@Override
	public void onPostExecute(String result) {
		tv.setText(result);
		FragmentManager fm = getFragmentManager();
		fm.popBackStack();

	}

}
