package dk.summerinnovationweek.futurehousing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import dk.summerinnovationweek.futurehousing.R;


public class RoomActivity extends ActionBarActivity
{

    Button button;
    ImageView image;

	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, RoomActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setupActionBar();
		setContentView(R.layout.activity_room);

        addListenerOnButton();
    }


    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
        } else {
            // Disable vibrate
        }
    }

    public void addListenerOnButton() {

        final LinearLayout bg = (LinearLayout) findViewById(R.id.fragment_room_content);
        image = (ImageView) findViewById(R.id.ivLightOn);

        button = (ToggleButton) findViewById(R.id.toggleButton);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                boolean on = ((ToggleButton) view).isChecked();
                if (on == true) {
                   image.setImageResource(R.drawable.bulb_on);
                    // bg.setBackgroundColor(0xFFF3F3F3);
                } else {
                    image.setImageResource(R.drawable.bulb_off);
                    //bg.setBackgroundColor(0xFF000000);
                }

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
				Intent intent = RoomActivity.newIntent(this);
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
