package dk.summerinnovationweek.futurehousing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.fragment.AboutFragment;
import dk.summerinnovationweek.futurehousing.fragment.HouseFragment;
import dk.summerinnovationweek.futurehousing.fragment.RoomFragment;
import dk.summerinnovationweek.futurehousing.fragment.StatisticsFragment;


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
			return mHouse.getRoomList().size() + 3;
	}


	@Override
	public Fragment getItem(int position)
	{
		if (position == 0)
		{
			return HouseFragment.newInstance(mHouse);
		}
		else if (position <  mHouse.getRoomList().size() + 1)
		{
			return RoomFragment.newInstance(mHouse.getRoomList().get(position - 1));
		}
		else if (position == mHouse.getRoomList().size() + 1)
		{
			return StatisticsFragment.newInstance(null);
		}
		else if (position == mHouse.getRoomList().size() + 2)
		{
			String backgroundPath = "drawable://" + R.drawable.stairs;
			return AboutFragment.newInstance(backgroundPath);
		}
		else
		{
			return HouseFragment.newInstance(mHouse);
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
	
	
	public static String getFragmentTag(int viewPagerId, long position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}
}
