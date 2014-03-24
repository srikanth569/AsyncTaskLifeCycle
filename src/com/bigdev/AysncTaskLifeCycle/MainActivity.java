package com.bigdev.AysncTaskLifeCycle;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bigdev.AysncTaskLifeCycle.TaskFragment.DummyCallBacks;

public class MainActivity extends Activity implements DummyCallBacks,
		OnClickListener {
	TextView statusText;
	Button submitButton;
	final static String LOG_TAG = "main_activity";
	final static String CANCEL = "cancel";
	final static String SUBMIT = "submit";
	String currentStatus;
	String buttonText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		statusText = (TextView) findViewById(R.id.textView1);
		submitButton = (Button) findViewById(R.id.button1);
		submitButton.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPreExecute() {
		submitButton.setText(CANCEL);
	}

	@Override
	public void onProgressUpdate(int percent) {
		statusText.setText("Loading " + percent + " %");
	}

	@Override
	public void onCancelled() {
		submitButton.setText(SUBMIT);
	}

	@Override
	public void onPostExecute(String result) {
		statusText.setText(result);
		submitButton.setText(SUBMIT);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			Log.v(LOG_TAG, "Button on click");
			FragmentManager fm = getFragmentManager();
			TaskFragment taskFragment = (TaskFragment) fm
					.findFragmentByTag("task");

			if (taskFragment == null) {
				taskFragment = new TaskFragment();
				fm.beginTransaction().add(taskFragment, "task").commit();
				Log.v(LOG_TAG, "Task fragment added to back stack");
			}
			taskFragment.startDummyTask();
		}
	}

}
