package dk.summerinnovationweek.futurehousing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.fragment.HouseFragment;
import dk.summerinnovationweek.futurehousing.fragment.RoomFragment;


public class MainPagerAdapter extends FragmentPagerAdapter
{
	private HouseEntity mHouse;
	
	
	public MainPagerAdapter(FragmentManager fragmentManager, HouseEntity house)
	{
		super(fragmentManager);
		mHouse = house;
	}


	@Override
	public int getCount()
	{
		if (mHouse == null)
			return 1;
		else
			return mHouse.getRoomList().size() + 1;
	}


	@Override
	public Fragment getItem(int position)
	{
		if (position == 0)
		{
			return HouseFragment.newInstance(mHouse);
		}
		else
		{
			return RoomFragment.newInstance(mHouse.getRoomList().get(position - 1));
		}
	}
	
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		if (position == 0)
			return "House overview";
		else
			return mHouse.getRoomList().get(position - 1).getName();
	}
	
	
	public void refill(HouseEntity house)
	{
		mHouse = house;
		notifyDataSetChanged();
	}
	
	
	public static String getFragmentTag(int viewPagerId, int position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}
}
