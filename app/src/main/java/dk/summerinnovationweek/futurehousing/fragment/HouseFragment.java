package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.FragmentUtils;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class HouseFragment extends TaskFragment
{
	private static final String ARGUMENT_HOUSE = "house";

	//image loader
	private ImageLoader mImageLoader = ImageLoader.getInstance();

	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;

	private ImageView mFloorPlanImageView;

	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private HouseEntity mHouse;
	private int mFloorPlanViewWidth;
	private int mFloorPlanViewHeight;
	private float mFloorPlanScaleRatio;


	public static HouseFragment newInstance(HouseEntity house)
	{
		HouseFragment fragment = new HouseFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putSerializable(ARGUMENT_HOUSE, house);
		fragment.setArguments(arguments);

		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if (arguments != null)
		{
			handleArguments(arguments);
		}

		// image caching options
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(Color.TRANSPARENT)
				.showImageForEmptyUri(Color.TRANSPARENT)
				.showImageOnFail(Color.TRANSPARENT)
				.cacheInMemory(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();
		mImageLoadingListener = new SimpleImageLoadingListener();
	}


	private void handleArguments(Bundle arguments)
	{
		if (arguments.containsKey(ARGUMENT_HOUSE))
		{
			mHouse = (HouseEntity) arguments.getSerializable(ARGUMENT_HOUSE);
		}
	}


	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_house, container, false);

		mFloorPlanImageView = (ImageView) mRootView.findViewById(R.id.fragment_house_floorplan_image_view);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		renderView();

		// progress in action bar
		showActionBarProgress(mActionBarProgress);
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
	public void onDestroyView()
	{
		super.onDestroyView();
		mRootView = null;
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}


	@Override
	public void onDetach()
	{
		super.onDetach();
	}


	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);
//		setUserVisibleHint(true);
	}


	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
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


	public void updateHouse()
	{
		drawRooms();
	}


	private void renderView()
	{
		mImageLoader.displayImage(mHouse.getFloorPlan(), mFloorPlanImageView, mDisplayImageOptions, mImageLoadingListener);
		ViewTreeObserver viewTreeObserver = mFloorPlanImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
				{

					@Override
					public void onGlobalLayout()
					{
						mFloorPlanViewWidth = mFloorPlanImageView.getWidth();
						mFloorPlanViewHeight = mFloorPlanImageView.getHeight();

						mFloorPlanImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

						drawRooms();
					}
				});
	}

	public void drawRooms()
	{
		RelativeLayout roomsRootRelativeLayout = (RelativeLayout) mRootView.findViewById(R.id.fragment_house_floorplan_overlay);

		if (mFloorPlanViewWidth == 0)
		{
			return;
		}

		mFloorPlanScaleRatio = (float)mFloorPlanViewWidth / (float)mHouse.getFloorPlanWidth();

		roomsRootRelativeLayout.removeAllViews();

		for (final RoomEntity room : mHouse.getRoomList())
		{
			if (room.getFloorPlanMarginBottom() == 0 || room.getFloorPlanMarginRight() == 0)
				continue;

			LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View child = li.inflate(R.layout.fragment_house_room_item, null);

			if (room.getItemLightEntity() != null)
			{
				ImageView lightImageView = (ImageView) child.findViewById(R.id.fragment_house_room_item_light_image_view);
				if (room.getItemLightEntity().isMeasuredLight())
				{
					lightImageView.setImageResource(R.drawable.lightbulb);
				} else
				{
					lightImageView.setImageResource(R.drawable.lightbulb_off);
				}
			}
			if (room.getItemHeatingEntity() != null)
			{
				TextView temperatureTextView = (TextView) child.findViewById(R.id.fragment_house_room_item_temperature_text_view);
				temperatureTextView.setText(Integer.toString(room.getItemHeatingEntity().getMeasuredTemperature()));

				if (room.getItemHeatingEntity().getUserTemperature() < room.getItemHeatingEntity().getMeasuredTemperature())
				{
					temperatureTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
				} else if (room.getItemHeatingEntity().getUserTemperature() > room.getItemHeatingEntity().getMeasuredTemperature())
				{
					temperatureTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
				} else
				{
					temperatureTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
				}
			}

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			params.topMargin = (int)(room.getFloorPlanMarginTop() * mFloorPlanScaleRatio);
			params.leftMargin = (int)(room.getFloorPlanMarginLeft() * mFloorPlanScaleRatio);
			params.rightMargin = (int)((mHouse.getFloorPlanWidth() - room.getFloorPlanMarginRight()) * mFloorPlanScaleRatio);
			params.bottomMargin = (int)((mHouse.getFloorPlanHeight() - room.getFloorPlanMarginBottom()) * mFloorPlanScaleRatio);

			final MainPagerFragment fragment = FragmentUtils.getParent(this, MainPagerFragment.class);
			child.setClickable(true);
			child.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					fragment.showRoomWithId(room.getId());
				}
			});

			roomsRootRelativeLayout.addView(child, params);
		}
	}
}
