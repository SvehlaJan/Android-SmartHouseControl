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

import dk.summerinnovationweek.futurehousing.R;


public class MainActivity extends ActionBarActivity
{
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
		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				// TODO
				Intent intent = dk.summerinnovationweek.futurehousing.activity.MainActivity.newIntent(this);
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
