package dk.summerinnovationweek.futurehousing.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.fragment.MainPagerFragment;
import dk.summerinnovationweek.futurehousing.utility.DialogUtility;


public class MainActivity extends ActionBarActivity
{
	public static final String EXTRA_HOUSE = "extraHouse";
	public static final String EXTRA_ROOM = "extraRoom";

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_SELECT = 2;
	private String mCurrentPhotoPath;

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
		menuInflater.inflate(R.menu.menu_simple, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		HouseEntity house = new HouseEntity();
		house.setId(1);

		ArrayList<RoomEntity> rooms = new ArrayList<RoomEntity>();

		RoomEntity room = new RoomEntity(1, "Kitchen", true, 20);
		room.setInputIsLightOn(true);
		room.setInputTemperature(22);
        rooms.add(room);

		room = new RoomEntity(2, "Guest room", false, 17);
		room.setInputIsLightOn(false);
		room.setInputTemperature(17);
		rooms.add(room);

		room = new RoomEntity(3, "Living room", true, 23);
		room.setInputIsLightOn(true);
		room.setInputTemperature(22);
		rooms.add(room);

		room = new RoomEntity(4, "Office", false, 22);
		room.setInputIsLightOn(true);
		room.setInputTemperature(22);
		rooms.add(room);

		room = new RoomEntity(5, "Dining room", false, 20);
		room.setInputIsLightOn(false);
		room.setInputTemperature(20);
		rooms.add(room);

		house.setRoomList(rooms);

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

			case R.id.ab_button_list_house_consumption:
				fragment.showStatistics();
				return true;

			case R.id.ab_button_list_change_background:
				dispatchChangeBackground();
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

	@Override
	public void onBackPressed()
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		MainPagerFragment fragment = (MainPagerFragment) fragmentManager.findFragmentById(R.id.fragment_main_pager);
		if (fragment.isOverviewShown())
			finish();
		else fragment.showHouse();
	}

	private void setBackground(Bitmap background)
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

		if (background.getWidth() > background.getHeight())
		{
			scaledBitmap = Bitmap.createScaledBitmap(background, height/2, width/2, true);
			rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
		}
		else
		{
			rotatedBitmap = Bitmap.createScaledBitmap(background, width/2, height/2, true);
		}

		final RenderScript rs = RenderScript.create(getBaseContext());
		final Allocation input = Allocation.createFromBitmap(rs, rotatedBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
		final Allocation output = Allocation.createTyped(rs, input.getType());
		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		script.setRadius(15f);
		script.setInput(input);
		script.forEach(output);
		output.copyTo(rotatedBitmap);

		FragmentManager fragmentManager = getSupportFragmentManager();
		MainPagerFragment fragment = (MainPagerFragment) fragmentManager.findFragmentById(R.id.fragment_main_pager);
		fragment.setBackground(rotatedBitmap);
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
