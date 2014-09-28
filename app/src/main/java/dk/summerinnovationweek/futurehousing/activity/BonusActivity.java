package dk.summerinnovationweek.futurehousing.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.fragment.BonusFragment;
import dk.summerinnovationweek.futurehousing.utility.DialogUtility;


public class BonusActivity extends ActionBarActivity
{

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_SELECT = 2;
	private String mCurrentPhotoPath;

	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, BonusActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setupActionBar();
		setContentView(R.layout.activity_bonus);
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_future_housing, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				finish();
				return true;

			case R.id.ab_button_list_change_background:
				dispatchChangeBackground();
				return true;

			case R.id.ab_button_refresh:
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_clear);
				setBackground(bitmap);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	private void dispatchChangeBackground()
	{
		DialogUtility.showThreeButtonDialog(this, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dispatchSelectPictureIntent();
			}
		}, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dispatchTakePictureIntent();
			}
		}, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		}, "From galery", "Use camera", "Cancel", "Would you like to take picture or select from galery?", "Select Background");
	}


	private void dispatchTakePictureIntent()
	{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}


	private void dispatchSelectPictureIntent()
	{
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUEST_IMAGE_SELECT);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode)
		{
			case REQUEST_IMAGE_CAPTURE:
				if (resultCode == RESULT_OK)
				{
					Bundle extras = data.getExtras();
					Bitmap imageBitmap = (Bitmap) extras.get("data");
					setBackground(imageBitmap);
				}
				break;
			case REQUEST_IMAGE_SELECT:
				if (resultCode == RESULT_OK)
				{
					Uri selectedImage = data.getData();
					String[] filePathColumn = {MediaStore.Images.Media.DATA};

					Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String filePath = cursor.getString(columnIndex);
					cursor.close();

					Bitmap imageBitmap = BitmapFactory.decodeFile(filePath);
					setBackground(imageBitmap);
				}
				break;
		}
	}

	private void setBackground(Bitmap bitmap)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		BonusFragment fragment = (BonusFragment) fragmentManager.findFragmentById(R.id.fragment_bonus);
		fragment.setBackground(bitmap);
	}
	
	
	private void setupActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setIcon(getResources().getDrawable(R.drawable.back_arrow_image));
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false);
	}
}
