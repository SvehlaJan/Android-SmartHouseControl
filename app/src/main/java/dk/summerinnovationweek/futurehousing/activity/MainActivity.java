package dk.summerinnovationweek.futurehousing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;


public class MainActivity extends ActionBarActivity
{
	public static final String EXTRA_HOUSE = "extraHouse";
	public static final String EXTRA_ROOM = "extraRoom";

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

		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				Intent intentHome = dk.summerinnovationweek.futurehousing.activity.MainActivity.newIntent(this);
				startActivity(intentHome);
				return true;

			case R.id.ab_button_list_expand:
				Intent intentRoom = RoomActivity.newIntent(this);
				intentRoom.putExtra(EXTRA_ROOM, room);
				startActivity(intentRoom);
				return true;

			case R.id.ab_button_list_collapse:
				Intent intentHouse = HouseActivity.newIntent(this);
				intentHouse.putExtra(EXTRA_HOUSE, house);
				startActivity(intentHouse);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void setupActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false);
	}
}
