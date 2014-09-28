package dk.summerinnovationweek.futurehousing.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import dk.summerinnovationweek.futurehousing.R;


public class SimpleDialogFragment extends DialogFragment
{
	private static final String ARGUMENT_MESSAGE = "message";
	private static final String ARGUMENT_POSITIVE_LABEL = "positive_label";
	private static final String ARGUMENT_NEGATIVE_LABEL = "negative_label";
	
	private String mMessage;
	private SimpleDialogListener mListener;
	private String mPositiveLabel;
	private String mNegativeLabel;


	public interface SimpleDialogListener
	{
		public void onSimpleDialogPositiveClick(DialogFragment dialog);
		public void onSimpleDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static SimpleDialogFragment newInstance(String message)
	{
		SimpleDialogFragment fragment = new SimpleDialogFragment();
		
		// arguments
		Bundle arguments = new Bundle();
		arguments.putString(ARGUMENT_MESSAGE, message);
		fragment.setArguments(arguments);
		
		return fragment;
	}


	public static SimpleDialogFragment newInstance(String message, String positiveLabel, String negativeLabel)
	{
		SimpleDialogFragment fragment = new SimpleDialogFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putString(ARGUMENT_MESSAGE, message);
		arguments.putString(ARGUMENT_POSITIVE_LABEL, positiveLabel);
		arguments.putString(ARGUMENT_NEGATIVE_LABEL, negativeLabel);
		fragment.setArguments(arguments);

		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(true);
		setRetainInstance(true);
		
		// handle fragment arguments
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			handleArguments(arguments);
		}
		
		// set callback listener
		try
		{
			mListener = (SimpleDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + SimpleDialogListener.class.getName());
		}
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// cancelable on touch outside
		if(getDialog()!=null) getDialog().setCanceledOnTouchOutside(true);
	}
	
	
	@Override
	public void onDestroyView()
	{
		// http://code.google.com/p/android/issues/detail?id=17423
		if(getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder
		.setTitle("title")
		.setIcon(R.drawable.ic_launcher)
		.setMessage(mMessage)
		.setPositiveButton(mPositiveLabel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onSimpleDialogPositiveClick(SimpleDialogFragment.this);
			}
		})
		.setNegativeButton(mNegativeLabel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onSimpleDialogNegativeClick(SimpleDialogFragment.this);
			}
		});
		
		return builder.create();
	}


	public void setDialogListener(SimpleDialogListener listener)
	{
		mListener = listener;
	}
	
	
	private int getTheme(boolean light)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			return light ? android.R.style.Theme_DeviceDefault_Light_Dialog : android.R.style.Theme_DeviceDefault_Dialog;
		}
		else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return light ? android.R.style.Theme_Holo_Light_Dialog : android.R.style.Theme_Holo_Dialog;
		}
		else
		{
			return android.R.style.Theme_Dialog;
		}
	}
	
	
	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(ARGUMENT_MESSAGE))
		{
			mMessage = (String) arguments.get(ARGUMENT_MESSAGE);
		}

		if(arguments.containsKey(ARGUMENT_POSITIVE_LABEL))
		{
			mPositiveLabel = (String) arguments.get(ARGUMENT_POSITIVE_LABEL);
		}
		else
		{
			mPositiveLabel = getString(android.R.string.ok);
		}

		if(arguments.containsKey(ARGUMENT_NEGATIVE_LABEL))
		{
			mNegativeLabel = (String) arguments.get(ARGUMENT_POSITIVE_LABEL);
		}
		else
		{
			mNegativeLabel = getString(android.R.string.cancel);
		}
	}
}
