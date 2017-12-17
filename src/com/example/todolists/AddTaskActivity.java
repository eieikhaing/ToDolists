package com.example.todolists;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTaskActivity extends Activity {
	EditText edtSubject, edtDueDate;
	Spinner spnPriority;
	Button btnsaveTask;
	private String subject, duedate, priority;
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private int taskId = 0;
	private String checkedvalue;
	ArrayList<String> taskProirity = new ArrayList<String>();

	protected void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.activity_main);

		dbhelper = new DBHelper(this);
		db = dbhelper.getWritableDatabase();
		edtSubject = (EditText) findViewById(R.id.edtSubject);
		edtDueDate = (EditText) findViewById(R.id.edtDueDate);
		spnPriority = (Spinner) findViewById(R.id.spinner1);
		btnsaveTask = (Button) findViewById(R.id.btnAddTask);
		taskProirity.add("Choose Plan");
		taskProirity.add("Important");
		taskProirity.add("Normal");
		taskProirity.add("Low");
		ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, android.R.id.text1,
				taskProirity);
		spnPriority.setAdapter(priorityAdapter);
		Intent i = getIntent();
		taskId = i.getIntExtra("id", 0);
		edtSubject.setText(i.getStringExtra("subject"));
		edtDueDate.setText(i.getStringExtra("duedate"));
		priority = i.getStringExtra("priority");
		checkedvalue = i.getStringExtra("checked");

		btnsaveTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subject = edtSubject.getText().toString();
				duedate = edtDueDate.getText().toString();
				priority = spnPriority.getSelectedItem().toString();
				saveTask();

			}

			private void saveTask() {
				// TODO Auto-generated method stub
				Task task = null;
				db = dbhelper.getWritableDatabase();
				ContentValues values = new ContentValues();

				values.put(dbhelper.SUBJECT, subject);
				values.put(dbhelper.DUEDATE, duedate);
				values.put(dbhelper.Priority, priority);
				values.put(dbhelper.Status, "no");
				if (taskId != 0) {
					task = dbhelper.getTask(taskId);
					task.setSubject(subject);
					task.setDuedate(duedate);
					task.setPriority(priority);
					task.setStatus(checkedvalue);

					dbhelper.updateTask(task);
					Toast.makeText(getBaseContext(), "Task updated",
							Toast.LENGTH_SHORT).show();

					Intent i = new Intent(AddTaskActivity.this,
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				} else {
					db.insert(dbhelper.table_name, null, values);

					Intent intent = new Intent(AddTaskActivity.this,
							MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					Toast.makeText(getApplicationContext(),
							"Add task complete", 1).show();
				}
				clearfields();

			}

			private void clearfields() {
				// TODO Auto-generated method stub
				edtSubject.setText("");
				edtDueDate.setText("");
			}

		});
		

	}
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();

	}
}
