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

import dk.summerinnovationweek.futurehousing.R;


public class HouseActivity extends ActionBarActivity
{

    Button kitchen;
    Button dinningroom;
    Button livingroom;
    Button office;
    Button guest;
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
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setupActionBar();
		setContentView(R.layout.activity_house);
        addListenerOnButton();


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
