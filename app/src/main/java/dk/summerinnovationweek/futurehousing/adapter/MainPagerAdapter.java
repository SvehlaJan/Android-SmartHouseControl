package dk.summerinnovationweek.futurehousing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainPagerAdapter extends FragmentPagerAdapter // TODO: use FragmentPagerAdapter or FragmentStatePagerAdapter
{
	private static final int FRAGMENT_COUNT = 2;
	
	
	public MainPagerAdapter(FragmentManager fragmentManager)
	{
		super(fragmentManager);
	}


	@Override
	public int getCount()
	{
		return FRAGMENT_COUNT;
	}


	@Override
	public Fragment getItem(int position)
	{
//		return ExampleFragment.newInstance(Integer.toString(position));
		return null;
	}
	
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		String title = "Fragment " + position;
		return title;
	}
	
	
	public void refill()
	{
		notifyDataSetChanged();
	}
	
	
	public static String getFragmentTag(int viewPagerId, int position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}
}
