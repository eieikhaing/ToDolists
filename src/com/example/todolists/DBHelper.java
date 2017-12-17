package com.example.todolists;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	Context context;
	private SQLiteDatabase db;
	static final String db_name = "TaskDB";
	static final String table_name = "Tasks";
	static final String ID = "id";
	static final String SUBJECT = "subject";
	static final String DUEDATE = "duedate";
	static final String Status = "status";
	static final String Priority = "priority";
	static final int DB_VERSION = 2;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, db_name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBHelper(Context context) {
		super(context, db_name, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTable = "create table " + table_name + "(" + ID
				+ " integer primary key autoincrement, " + SUBJECT + " text, "
				+ DUEDATE + " text, " + Priority + " text, "+Status + " text);";


		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists" + table_name);
		onCreate(db);
	}

	public List<Task> getallTasks() {
		// TODO Auto-generated method stub
		List<Task> taskslist = new ArrayList<Task>();
		String selectQuery = "select * from Tasks where status='no'";

		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();

				task.setId(Integer.parseInt(cursor.getString(0)));

				task.setSubject(cursor.getString(1));
				task.setDuedate(cursor.getString(2));
				task.setPriority(cursor.getString(3));
				task.setStatus(cursor.getString(4));
				// Adding cloth to list
				taskslist.add(task);
			} while (cursor.moveToNext());
		}

		return taskslist;

	}
	public List<Task> getDoneallTasks() {
		// TODO Auto-generated method stub
		List<Task> donetaskslist = new ArrayList<Task>();
		String selectQuery = "select * from Tasks where status='yes'";

		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();

				task.setId(Integer.parseInt(cursor.getString(0)));

				task.setSubject(cursor.getString(1));
				task.setDuedate(cursor.getString(2));
				task.setPriority(cursor.getString(3));
				task.setStatus(cursor.getString(4));
				// Adding cloth to list
				donetaskslist.add(task);
			} while (cursor.moveToNext());
		}

		return donetaskslist;

	}

	public Task getTask(int id) {

		db = this.getReadableDatabase();
		Cursor cursor = db.query(table_name, new String[] { ID, SUBJECT,
				DUEDATE, Priority,Status }, ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
		String duedate = cursor.getString(cursor.getColumnIndex(DUEDATE));
		String priority = cursor.getString(cursor.getColumnIndex(Priority));
		String status = cursor.getString(cursor.getColumnIndex(Status));

		db.close();
		return new Task(id, subject, duedate, priority,status);
	}

	public void deleteTask(Task task) {
		db = this.getWritableDatabase();
		db.delete(table_name, ID + " =?",
				new String[] { String.valueOf(task.getId()) });
	}

	public int updateTask(Task task) {
		// TODO Auto-generated method stub
		db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SUBJECT, task.getSubject());
		values.put(DUEDATE, task.getDuedate());
		values.put(Priority, task.getPriority());
		values.put(Status, task.getStatus());
		return db.update(table_name, values, ID + " =?",
				new String[] { String.valueOf(task.getId()) });
	}
	

}
