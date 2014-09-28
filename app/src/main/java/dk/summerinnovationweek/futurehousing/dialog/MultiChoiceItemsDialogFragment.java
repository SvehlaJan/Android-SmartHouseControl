package dk.summerinnovationweek.futurehousing.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import dk.summerinnovationweek.futurehousing.R;


public class MultiChoiceItemsDialogFragment extends DialogFragment
{
	private static final String ARGUMENT_CHECKED_ITEMS = "checked_items";
	
	private boolean mCheckedItems[] = null;
	private MultiChoiceItemsDialogListener mListener;
	
	
	public interface MultiChoiceItemsDialogListener
	{
		public void onMultiChoiceItemsDialogPositiveClick(DialogFragment dialog, boolean checkedItems[]);
		public void onMultiChoiceItemsDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static MultiChoiceItemsDialogFragment newInstance(boolean checkedItems[])
	{
		MultiChoiceItemsDialogFragment fragment = new MultiChoiceItemsDialogFragment();
		
		// arguments
		Bundle arguments = new Bundle();
		arguments.putBooleanArray(ARGUMENT_CHECKED_ITEMS, checkedItems);
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
			mListener = (MultiChoiceItemsDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + MultiChoiceItemsDialogListener.class.getName());
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
		final CharSequence items[] = {
				"item1",
				"item2",
				"item3",
				"item4" };
		
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder
		.setTitle("title")
		.setIcon(R.drawable.ic_launcher)
		.setMultiChoiceItems(items, mCheckedItems, new OnMultiChoiceClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked)
			{
				mCheckedItems[which] = isChecked;
			}
		})
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onMultiChoiceItemsDialogPositiveClick(MultiChoiceItemsDialogFragment.this, mCheckedItems);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onMultiChoiceItemsDialogNegativeClick(MultiChoiceItemsDialogFragment.this);
			}
		});
		
		return builder.create();
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
		if(arguments.containsKey(ARGUMENT_CHECKED_ITEMS))
		{
			mCheckedItems = (boolean[]) arguments.get(ARGUMENT_CHECKED_ITEMS);
		}
	}
}
