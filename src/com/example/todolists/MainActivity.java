package com.example.todolists;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private String checkedvalue;
	Button btnnew;
	ListView tasksList, tasksList2;
	ArrayList<Task> taskarray = new ArrayList<Task>();
	private ArrayAdapter<Task> adapter, adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		btnnew = (Button) findViewById(R.id.btnnew);
		btnnew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goadd = new Intent(MainActivity.this,
						AddTaskActivity.class);
				startActivity(goadd);
			}

		});
		tasksList = (ListView) findViewById(R.id.listView1);
		tasksList2 = (ListView) findViewById(R.id.listView2);
		dbhelper = new DBHelper(this);
		db = dbhelper.getReadableDatabase();

		List<Task> tasklists = dbhelper.getallTasks();
		List<Task> tasklists2 = dbhelper.getDoneallTasks();
		adapter = new TaskListAdapter(this.getBaseContext(), this,
				getFragmentManager(), R.layout.list_item, R.id.txtSubject,
				tasklists);
		tasksList.setAdapter(adapter);
		adapter2 = new TaskListAdapter(this.getBaseContext(), this,
				getFragmentManager(), R.layout.list_item, R.id.txtSubject,
				tasklists2);
		tasksList2.setAdapter(adapter2);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class TaskListAdapter extends ArrayAdapter<Task> {

		private Context context;
		private List<Task> tasks;
		private MainActivity cLActivity;
		private FragmentManager fManager;

		public TaskListAdapter(Context context, MainActivity context2,
				FragmentManager fManager, int textViewResourceId,
				int textViewId, List<Task> tasklists) {
			super(context, textViewResourceId, textViewId, tasklists);
			this.context = context;
			this.tasks = tasklists;
			this.cLActivity = context2;
			this.fManager = fManager;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.list_item, parent, false);
			final CheckBox chkDo = (CheckBox) row.findViewById(R.id.chkDo);
			chkDo.setTag(tasks.get(position).getId());
			if (tasks.get(position).getStatus().toString().equals("yes")) {
				chkDo.setChecked(true);
			}
			else {
				chkDo.setChecked(false);
			}
			
			chkDo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (chkDo.isChecked()) {
						checkedvalue = "yes";
					} else {
						checkedvalue = "no";
					}
					db = dbhelper.getWritableDatabase();
					int taskId = Integer.parseInt(v.getTag().toString());
					Task task = dbhelper.getTask(taskId);
					task.setSubject(tasks.get(position).getSubject());
					task.setDuedate(tasks.get(position).getDuedate());
					task.setPriority(tasks.get(position).getPriority());
					task.setStatus(checkedvalue);

					dbhelper.updateTask(task);
					Intent intent=new Intent(MainActivity.this,MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);

				}

			});
			
			TextView subject = (TextView) row.findViewById(R.id.txtSubject);
			// Typeface tf = Typeface.createFromAsset(getAssets(),
			// "fonts/Roboto-Thin.ttf");
			// subject.setTypeface(tf);

			TextView duedate = (TextView) row.findViewById(R.id.txtDueDate);
			// Typeface tfRegular = Typeface.createFromAsset(getAssets(),
			// "fonts/Roboto-Regular.ttf");
			// duedate.setTypeface(tfRegular);
			ImageView deleteButton = (ImageView) row
					.findViewById(R.id.deleteIcon);
			ImageView editButton = (ImageView) row.findViewById(R.id.editIcon);

			subject.setText(tasks.get(position).getSubject());
			duedate.setText(tasks.get(position).getDuedate());

			deleteButton.setTag(tasks.get(position).getId());
			editButton.setTag(tasks.get(position).getId());
			if (tasks.get(position).getPriority().toString()
					.equals("Important")) {

				row.setBackgroundColor(Color.RED);

			} else if (tasks.get(position).getPriority().toString()
					.equals("Normal")) {
				row.setBackgroundColor(Color.GREEN);
			} else if (tasks.get(position).getPriority().toString()
					.equals("Low")) {
				row.setBackgroundColor(Color.YELLOW);
			}

			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int taskId = Integer.parseInt(view.getTag().toString());
					showDeleteConfirmation(taskId);
				}
			});

			editButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					int taskId = Integer.parseInt(view.getTag().toString());
					Task task = dbhelper.getTask(taskId);

					Intent intent = new Intent(MainActivity.this,
							AddTaskActivity.class);
					intent.putExtra("id", task.getId());
					intent.putExtra("subject", task.getSubject());
					intent.putExtra("duedate", task.getDuedate());
					intent.putExtra("priority", task.getPriority());
					intent.putExtra("checked", task.getStatus());
					startActivity(intent);
				}
			});
			// row.setBackgroundColor(Color.YELLOW);
			return row;
		}

		private void showDeleteConfirmation(int taskId) {
			Task c = dbhelper.getTask(taskId);
			DialogFragment deleteDialog = new DialogCreator(c, cLActivity);
			deleteDialog.show(fManager, "Delete");
		}
	}

	public void deleteTask(Task task) {
		// TODO Auto-generated method stub
		dbhelper.deleteTask(task);
		finish();
		startActivity(getIntent());
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
	}
}
