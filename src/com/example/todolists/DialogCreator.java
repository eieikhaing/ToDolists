package com.example.todolists;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "ValidFragment", "NewApi" })
public class DialogCreator extends DialogFragment {

	private MainActivity cLActivity;
	
	private Task task;

	public DialogCreator(Task task, MainActivity activity) {
		cLActivity = activity;
		this.task = task;
	}

	

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (cLActivity != null) {
			String subject = task.getSubject();
			return new AlertDialog.Builder(getActivity())
					.setTitle(getString(R.string.delete_confirm_title))
					.setMessage("Do you want to delete " + subject + "?")
					.setPositiveButton(getString(R.string.delete_yes),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									cLActivity.deleteTask(task);

								}
							})
					.setNegativeButton(getString(R.string.delete_no),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();
		}

		

		return null;

	}

}
