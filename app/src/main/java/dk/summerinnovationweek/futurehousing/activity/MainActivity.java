package dk.summerinnovationweek.futurehousing.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.ScriptIntrinsicColorMatrix;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import dk.summerinnovationweek.futurehousing.FutureHousingConfig;
import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.fragment.MainPagerFragment;
import dk.summerinnovationweek.futurehousing.gcm.GcmUtility;
import dk.summerinnovationweek.futurehousing.utility.DialogUtility;
import dk.summerinnovationweek.futurehousing.utility.Logcat;


public class MainActivity extends ActionBarActivity
{
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_SELECT = 2;

	private GoogleCloudMessaging mGcm;
	private String mGcmRegistrationId;
	private AsyncTask<Void, Void, Void> mGcmRegisterAsyncTask;

	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, dk.summerinnovationweek.futurehousing.activity.MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setupActionBar();
		setContentView(R.layout.activity_main);

		// handle GCM registration
		handleGcmRegistration();
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

		// check Google Play Services
		GcmUtility.checkPlayServices(this);
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
		// cancel async tasks
		if(mGcmRegisterAsyncTask!=null) mGcmRegisterAsyncTask.cancel(true);

		super.onDestroy();
	}


	private void setupActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setIcon(getResources().getDrawable(R.drawable.future_housing));
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(true);

		setSupportProgressBarIndeterminateVisibility(false);
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
		FragmentManager fragmentManager = getSupportFragmentManager();
		MainPagerFragment fragment = (MainPagerFragment) fragmentManager.findFragmentById(R.id.fragment_main_pager);

		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				fragment.showHouse();
				return true;

			case R.id.ab_button_refresh:
				fragment.refreshData();
				return true;

			case R.id.ab_button_list_house_statistics:
				fragment.showStatistics();
				return true;

			case R.id.ab_button_list_change_background:
				dispatchChangeBackground();
				return true;

			case R.id.ab_button_list_about:
				fragment.showAbout();
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
		}, getString(R.string.dialog_change_background_gallery), getString(R.string.dialog_change_background_camera),
				getString(android.R.string.cancel), getString(R.string.dialog_change_background_message),
				getString(R.string.dialog_change_background_title));
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

	@Override
	public void onBackPressed()
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		MainPagerFragment fragment = (MainPagerFragment) fragmentManager.findFragmentById(R.id.fragment_main_pager);
		if (fragment.isOverviewShown())
			finish();
		else fragment.showHouse();
	}

	private void setBackground(Bitmap inBitmap)
	{
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		Matrix matrix = new Matrix();
		matrix.postRotate(90);

		Bitmap scaledBitmap;
		Bitmap rotatedBitmap;
		Bitmap blurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Bitmap grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		if (inBitmap.getWidth() > inBitmap.getHeight())
		{
			rotatedBitmap = Bitmap.createScaledBitmap(inBitmap, height, width, true);
			scaledBitmap = Bitmap.createBitmap(rotatedBitmap, 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);
		}
		else
		{
			scaledBitmap = Bitmap.createScaledBitmap(inBitmap, width, height, true);
		}


		final RenderScript rs = RenderScript.create(getBaseContext());
		final Allocation input = Allocation.createFromBitmap(rs, scaledBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
		final Allocation output = Allocation.createTyped(rs, input.getType());

		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		script.setRadius(15f); // 0 < radius <= 25
		script.setInput(input);
		script.forEach(output);
		output.copyTo(blurBitmap);

		final ScriptIntrinsicColorMatrix scriptColor = ScriptIntrinsicColorMatrix.create(rs, Element.U8_4(rs));
		scriptColor.setGreyscale();
		scriptColor.forEach(input, output);
		output.copyTo(grayBitmap);

		rs.destroy();

		FragmentManager fragmentManager = getSupportFragmentManager();
		MainPagerFragment fragment = (MainPagerFragment) fragmentManager.findFragmentById(R.id.fragment_main_pager);

		fragment.setBackground(blurBitmap);
	}





	private void handleGcmRegistration()
	{
		final Context context = getApplicationContext();

		// check device for Play Services APK
		if(GcmUtility.checkPlayServices(this))
		{
			// registration id
			mGcm = GoogleCloudMessaging.getInstance(this);
			mGcmRegistrationId = GcmUtility.getRegistrationId(context);

			// register device
			if(mGcmRegistrationId.isEmpty())
			{
				mGcmRegisterAsyncTask = new AsyncTask<Void, Void, Void>()
				{
					@Override
					protected Void doInBackground(Void... params)
					{
						try
						{
							// register on GCM server
							if(mGcm==null) mGcm = GoogleCloudMessaging.getInstance(context);
							mGcmRegistrationId = mGcm.register(FutureHousingConfig.GCM_SENDER_ID);

							// GcmUtility.register() must be called after successfull GoogleCloudMessaging.register(),
							// because it sets registration id in shared preferences
							GcmUtility.register(context, mGcmRegistrationId);
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						return null;
					}


					@Override
					protected void onPostExecute(Void result)
					{
						mGcmRegisterAsyncTask = null;
					}
				};
				mGcmRegisterAsyncTask.execute(null, null, null);
			}
			else
			{
				Logcat.d("Activity.handleGcmRegistration(): device is already registered on server / " + mGcmRegistrationId);
			}
		}
		else
		{
			Logcat.d("Activity.handleGcmRegistration(): no valid Google Play Services APK found");
		}
	}
}
