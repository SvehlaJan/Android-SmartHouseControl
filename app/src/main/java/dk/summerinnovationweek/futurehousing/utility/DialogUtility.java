package dk.summerinnovationweek.futurehousing.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import dk.summerinnovationweek.futurehousing.R;


public class DialogUtility
{
	private static final String TAG = "dialog_utility";


	/**
	 * show normal dialog
	 */
	public static void showNormalDialog(Context ctx, DialogInterface.OnClickListener onPositive, DialogInterface.OnClickListener onNegative, String okButton, String cancelButton, String message, String title)
	{
		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

			builder.setMessage(message)
					.setTitle(title)
					.setCancelable(false)
					.setPositiveButton(okButton, onPositive)
					.setNegativeButton(cancelButton, onNegative);
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception ex)
		{
			Log.e(TAG, "error: " + ex.toString());
		}
	}

	/**
	 * show normal dialog
	 */
	public static void showThreeButtonDialog(Context ctx, DialogInterface.OnClickListener onFirstOption, DialogInterface.OnClickListener onSecondOption, DialogInterface.OnClickListener onThirdOption, String firstButton, String secondButton, String thirdButton, String message, String title)
	{
		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

			builder.setMessage(message)
					.setTitle(title)
					.setCancelable(false)
					.setPositiveButton(firstButton, onFirstOption)
					.setNeutralButton(thirdButton, onThirdOption)
					.setNegativeButton(secondButton, onSecondOption);
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception ex)
		{
			Log.e(TAG, "error: " + ex.toString());
		}
	}


	/**
	 * show error dialog
	 */
	public static void showErrorDialog(Context ctx, DialogInterface.OnClickListener onPositive, String okButton, String message, String title)
	{
		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

			builder.setMessage(message)
					.setTitle(title)
					.setCancelable(false)
					.setPositiveButton(okButton, onPositive);
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception ex)
		{
			Log.e(TAG, "error: " + ex.toString());
		}
	}


	/**
	 * show single choice dialog
	 */
	public static void showSingleChoiseDialog(Context context, String[] items, String title, DialogInterface.OnClickListener listener)
	{
		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(title)
					.setItems(items, listener);
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception ex)
		{
			Log.e(TAG, "error: " + ex.toString());
		}
	}
}
