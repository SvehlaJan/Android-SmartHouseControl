package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemAquariumEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemHeatingEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemLightEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemOvenEntity;
import dk.summerinnovationweek.futurehousing.fragment.roomItems.RoomItemFragment;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class RoomFragment extends TaskFragment
{
	private static final String ARGUMENT_ROOM = "room";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private RoomEntity mRoom;
	private LinearLayout mLinearLayout;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;
	private ImageView mBackgroundImageView;


	public static RoomFragment newInstance(RoomEntity room)
	{
		RoomFragment fragment = new RoomFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putSerializable(ARGUMENT_ROOM, room);
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
		if (arguments.containsKey(ARGUMENT_ROOM))
		{
			mRoom = (RoomEntity) arguments.getSerializable(ARGUMENT_ROOM);
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
		mRootView = inflater.inflate(R.layout.fragment_room, container, false);
		mBackgroundImageView = (ImageView) mRootView.findViewById(R.id.fragment_room_background);
		mLinearLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_room_linear_layout);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// load and show data
		if (mViewState == null || mViewState == ViewState.OFFLINE)
		{
			showProgress();
			if (mRoom != null) renderView();
			showContent();
		} else if (mViewState == ViewState.CONTENT)
		{
			if (mRoom != null) renderView();
			showContent();
		} else if (mViewState == ViewState.PROGRESS)
		{
			showProgress();
		} else if (mViewState == ViewState.EMPTY)
		{
			showEmpty();
		}

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


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);

		// TODO
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behaviour
		return super.onOptionsItemSelected(item);

		// TODO
	}


	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}


	private void showContent()
	{
		// show content container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerEmpty = (ViewGroup) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}


	private void showProgress()
	{
		// show progress container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerEmpty = (ViewGroup) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}


	private void showOffline()
	{
		// show offline container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerEmpty = (ViewGroup) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.OFFLINE;
	}


	private void showEmpty()
	{
		// show empty container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerEmpty = (ViewGroup) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.VISIBLE);
		mViewState = ViewState.EMPTY;
	}


	public void setBackground(Bitmap bitmap)
	{
		if (mBackgroundImageView != null)
			mBackgroundImageView.setImageBitmap(bitmap);
	}


	private void renderView()
	{
		mImageLoader.displayImage(mRoom.getBackgroundPhotoUrl(), mBackgroundImageView, mDisplayImageOptions, mImageLoadingListener);

		for (RoomItemEntity roomItem : mRoom.getRoomItemEntities())
		{
			LayoutInflater li = LayoutInflater.from(getActivity());
			View child = null;

			if (roomItem.getType().equals(RoomItemEntity.TYPE_LIGHT))
			{
				final RoomItemLightEntity item = (RoomItemLightEntity) roomItem;

				child = li.inflate(R.layout.room_item_light_layout, null);

				final ImageView lightImageView = (ImageView) child.findViewById(R.id.room_item_light_light_image_view);
				final TextView lightNameTextView = (TextView) child.findViewById(R.id.room_item_light_name_text_view);
				final Switch lightSwitch = (Switch) child.findViewById(R.id.room_item_light_switch);
				final SeekBar lightSeekBar = (SeekBar) child.findViewById(R.id.room_item_light_seek_bar);

				lightNameTextView.setText(item.getName());

				if (item.isMeasuredLight())
					lightImageView.setImageResource(R.drawable.bulb_on);
				else
					lightImageView.setImageResource(R.drawable.bulb_off);


				lightSwitch.setChecked(item.isUserLight());
				lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
					{
						if (isChecked)
						{
							lightImageView.setImageResource(R.drawable.bulb_on);
							item.setMeasuredLight(true);
							item.setUserLight(true);
							lightSeekBar.setProgress(item.getUserLightPerc());
						} else
						{
							lightImageView.setImageResource(R.drawable.bulb_off);
							item.setMeasuredLight(false);
							item.setUserLight(false);
							lightSeekBar.setProgress(0);
						}
					}
				});

				lightSeekBar.setProgress(item.getUserLightPerc());
				lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
				{
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
					{
						if (fromUser)
						{
							item.setUserLightPerc(progress);
							item.setMeasuredLightPerc(progress);
							if (progress == 0)
								lightSwitch.setChecked(false);
							else if (!lightSwitch.isChecked())
								lightSwitch.setChecked(true);
						}
					}


					@Override
					public void onStartTrackingTouch(SeekBar seekBar)
					{
					}


					@Override
					public void onStopTrackingTouch(SeekBar seekBar)
					{
					}
				});
			}
			else if (roomItem.getType().equals(RoomItemEntity.TYPE_HEATING))
			{
				final RoomItemHeatingEntity item = (RoomItemHeatingEntity) roomItem;
				child = li.inflate(R.layout.room_item_heating_layout, null);

				final TextView heatingTempTextView = (TextView) child.findViewById(R.id.room_item_heating_actual_temp_text_view);
				final TextView heatingStateTextView = (TextView) child.findViewById(R.id.room_item_heating_actual_state_text_view);
				final NumberPicker numberPicker = (NumberPicker) child.findViewById(R.id.room_item_heating_number_picker);

				heatingTempTextView.setText(item.getMeasuredTemperature() + "\u00B0C");
				RoomItemFragment.setHeatingState(item.getUserTemperature(), item.getMeasuredTemperature(), heatingStateTextView);

				numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				numberPicker.setMaxValue(30);
				numberPicker.setMinValue(15);
				numberPicker.setValue(item.getUserTemperature());
				numberPicker.setFadingEdgeLength(5);
				numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
				{
					@Override
					public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal)
					{
						item.setUserTemperature(newVal);
						RoomItemFragment.setHeatingState(item.getUserTemperature(), item.getMeasuredTemperature(), heatingStateTextView);
					}
				});
			}
			else if (roomItem.getType().equals(RoomItemEntity.TYPE_AQUARIUM))
			{
				final RoomItemAquariumEntity item = (RoomItemAquariumEntity) roomItem;
				child = li.inflate(R.layout.room_item_aquarium_layout, null);

				final TextView temperatureTextView = (TextView) child.findViewById(R.id.room_item_aquarium_temperature_text_view);
				final Switch topLightSwitch = (Switch) child.findViewById(R.id.room_item_aquarium_top_light_switch);
				final Switch sideLightSwitch = (Switch) child.findViewById(R.id.room_item_aquarium_side_light_switch);
				final NumberPicker numberPicker = (NumberPicker) child.findViewById(R.id.room_item_aquarium_number_picker);

				temperatureTextView.setText(item.getMeasuredTemperature() + "\u00B0C");
				topLightSwitch.setChecked(item.isTopLight());
				topLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
					{
						item.setTopLight(isChecked);
					}
				});
				sideLightSwitch.setChecked(item.isSideLight());
				sideLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
					{
						item.setSideLight(isChecked);
					}
				});

				numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				numberPicker.setMaxValue(30);
				numberPicker.setMinValue(10);
				numberPicker.setValue(item.getUserTemperature());
				numberPicker.setFadingEdgeLength(5);

				numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
				{
					@Override
					public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal)
					{
						item.setUserTemperature(newVal);
					}
				});
			}
			else if (roomItem.getType().equals(RoomItemEntity.TYPE_OVEN))
			{
				final RoomItemOvenEntity item = (RoomItemOvenEntity) roomItem;
				child = li.inflate(R.layout.room_item_aquarium_layout, null);

				final TextView temperatureTextView = (TextView) child.findViewById(R.id.room_item_oven_temperature_text_view);
				final TextView ovenStateTextView = (TextView) child.findViewById(R.id.room_item_oven_actual_state_text_view);
				final Switch isTurnedSwitch = (Switch) child.findViewById(R.id.room_item_oven_is_turned_switch);
				final NumberPicker numberPicker = (NumberPicker) child.findViewById(R.id.room_item_oven_number_picker);

				temperatureTextView.setText(item.getMeasuredTemperature() + "\u00B0C");
				RoomItemFragment.setHeatingState(item.getUserTemperature(), item.getMeasuredTemperature(), ovenStateTextView);

				isTurnedSwitch.setChecked(item.isUserTurned());
				isTurnedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
					{
						item.setUserTurned(isChecked);
					}
				});

				numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				numberPicker.setFadingEdgeLength(5);

				String[] ovenTempValues = new String[10];
				for (int i = 150; i <= 250; i += 10)
				{
					ovenTempValues[i] = Integer.toString(i);
				}
				numberPicker.setDisplayedValues(ovenTempValues);
				numberPicker.setValue((item.getUserTemperature() - 150) / 10);

				numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
				{
					@Override
					public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal)
					{
						item.setUserTemperature((newVal * 10) + 150);
						RoomItemFragment.setHeatingState(item.getUserTemperature(), item.getMeasuredTemperature(), ovenStateTextView);
					}
				});
			}

			if (child != null)
			{
				mLinearLayout.addView(child);
			}
		}

	}

}
