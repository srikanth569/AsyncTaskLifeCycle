package com.bigdev.AysncTaskLifeCycle;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class TaskFragment extends Fragment {
	private DummyTask mTask;
	private final static String LOG_TAG = "taskFragment";

	public interface DummyCallBacks {
		void onPreExecute();

		void onProgressUpdate(int percent);

		void onCancelled();

		void onPostExecute(String result);
	}

	private DummyCallBacks callbacks;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callbacks = (DummyCallBacks) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	protected void startDummyTask() {
		mTask = new DummyTask();
		mTask.execute();
	}

	private class DummyTask extends AsyncTask<Void, Integer, String> {

		@Override
		protected void onPreExecute() {
			if (callbacks == null) {
				Log.v(LOG_TAG, "call back reference is null");
			} else
				callbacks.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			for (int i = 0; !isCancelled() && i < 100; i++) {
				SystemClock.sleep(100);
				publishProgress(i);
			}
			return "Completed....";
		}

		@Override
		protected void onPostExecute(String result) {
			callbacks.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			callbacks.onProgressUpdate(values[0]);
		}
	}
}
