package dk.summerinnovationweek.futurehousing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;


public class HouseActivity extends ActionBarActivity
{
    TextView temperaturekitchen;
    TextView temperaturedinningroom;
    TextView temperaturelivingroom;
    TextView temperatureoffice;
    TextView temperatureguest;
    Button kitchen;
    Button dinningroom;
    Button livingroom;
    Button office;
    Button guest;
    ImageView kitchenlight;
    ImageView dinningroomlight;
    ImageView livingroomlight;
    ImageView officelight;
    ImageView guestlight;
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, dk.summerinnovationweek.futurehousing.activity.HouseActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;



    }


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);




        Bundle extras = getIntent().getExtras();
        HouseEntity house = (HouseEntity) extras.getSerializable(MainActivity.EXTRA_HOUSE);

		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setupActionBar();
		setContentView(R.layout.activity_house);
        addListenerOnButton();
        renderView(house);


    }

    private RoomEntity getRoomByName(HouseEntity house, String roomName)
    {
        for (RoomEntity room : house.getRoomList())
        {
            if (room.getName().equals(roomName))
                return room;
        }

        return null;
    }

    public void renderView(HouseEntity house)
    {

        kitchenlight = (ImageView)findViewById(R.id.kitchenlight);
        RoomEntity kitchenroom = getRoomByName(house, "Kitchen");
        if (kitchenroom.isMeasuredIsLightOn())
        {
            kitchenlight.setImageResource(R.drawable.lightbulb);
        } else {
            kitchenlight.setImageResource(R.drawable.lightbulb_off);
        }
        temperaturekitchen = (TextView)findViewById(R.id.temperaturekitchen);
        temperaturekitchen.setText(Integer.toString(kitchenroom.getMeasuredTemperature()));

        if (kitchenroom.getInputTemperature() == kitchenroom.getMeasuredTemperature())
        {
            temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
        }

        if (kitchenroom.getInputTemperature() > kitchenroom.getMeasuredTemperature())
        {
            temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
        }

        if (kitchenroom.getInputTemperature() < kitchenroom.getMeasuredTemperature())
        {
            temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
        }




        livingroomlight = (ImageView)findViewById(R.id.livingroomlight);
        RoomEntity livingroom = getRoomByName(house, "Living room");
        if (livingroom.isMeasuredIsLightOn())
        {
            livingroomlight.setImageResource(R.drawable.lightbulb);
        } else {
            livingroomlight.setImageResource(R.drawable.lightbulb_off);
        }
        temperaturelivingroom = (TextView)findViewById(R.id.temperaturelivingroom);
        temperaturelivingroom.setText(Integer.toString(livingroom.getMeasuredTemperature()));

        if (livingroom.getInputTemperature() == livingroom.getMeasuredTemperature())
        {
            temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
        }

        if (livingroom.getInputTemperature() > livingroom.getMeasuredTemperature())
        {
            temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
        }

        if (livingroom.getInputTemperature() < livingroom.getMeasuredTemperature())
        {
            temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
        }



        dinningroomlight = (ImageView)findViewById(R.id.dinningroomlight);
        RoomEntity dinningroom = getRoomByName(house, "Dining room");
        if (dinningroom.isMeasuredIsLightOn())
        {
            dinningroomlight.setImageResource(R.drawable.lightbulb);
        } else {
            dinningroomlight.setImageResource(R.drawable.lightbulb_off);
        }

        temperaturedinningroom = (TextView)findViewById(R.id.temperaturedinningroom);
        temperaturedinningroom.setText(Integer.toString(dinningroom.getMeasuredTemperature()));

        if (dinningroom.getInputTemperature() == dinningroom.getMeasuredTemperature())
        {
            temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
        }

        if (dinningroom.getInputTemperature() > dinningroom.getMeasuredTemperature())
        {
            temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
        }

        if (dinningroom.getInputTemperature() < dinningroom.getMeasuredTemperature())
        {
            temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
        }


        officelight = (ImageView)findViewById(R.id.officelight);
        RoomEntity officeroom = getRoomByName(house, "Office");
        if (officeroom.isMeasuredIsLightOn())
        {
            officelight.setImageResource(R.drawable.lightbulb);
        } else {
            officelight.setImageResource(R.drawable.lightbulb_off);
        }

        temperatureoffice = (TextView)findViewById(R.id.temperatureoffice);
        temperatureoffice.setText(Integer.toString(officeroom.getMeasuredTemperature()));

        if (officeroom.getInputTemperature() == officeroom.getMeasuredTemperature())
        {
            temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
        }

        if (officeroom.getInputTemperature() > officeroom.getMeasuredTemperature())
        {
            temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
        }

        if (officeroom.getInputTemperature() < officeroom.getMeasuredTemperature())
        {
            temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
        }



        guestlight = (ImageView)findViewById(R.id.guestlight);
        RoomEntity guestroom = getRoomByName(house, "Guest room");
        if (guestroom.isMeasuredIsLightOn())
        {
            guestlight.setImageResource(R.drawable.lightbulb);
        } else {
            guestlight.setImageResource(R.drawable.lightbulb_off);
        }
        temperatureguest = (TextView)findViewById(R.id.temperatureguest);
        temperatureguest.setText(Integer.toString(guestroom.getMeasuredTemperature()));

        if (guestroom.getInputTemperature() == guestroom.getMeasuredTemperature())
        {
            temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
        }

        if (guestroom.getInputTemperature() > guestroom.getMeasuredTemperature())
        {
            temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
        }

        if (guestroom.getInputTemperature() < guestroom.getMeasuredTemperature())
        {
            temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
        }

    }
    public void addListenerOnButton() {


        kitchen = (Button) findViewById(R.id.kitchen);
        kitchen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "Kitchen" );


            }

        });

        dinningroom = (Button) findViewById(R.id.dinningroom);
        dinningroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "dinningroom" );


            }

        });
        livingroom = (Button) findViewById(R.id.livingroom);
        livingroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "livingroom" );


            }

        });
        office = (Button) findViewById(R.id.office);
        office.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "office" );


            }

        });

        guest = (Button) findViewById(R.id.guest);
        guest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "guest" );


            }

        });

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
		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				// TODO
				Intent intent = dk.summerinnovationweek.futurehousing.activity.HouseActivity.newIntent(this);
				startActivity(intent);
				return true;
				
			case R.id.ab_button_refresh:
				// TODO
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
