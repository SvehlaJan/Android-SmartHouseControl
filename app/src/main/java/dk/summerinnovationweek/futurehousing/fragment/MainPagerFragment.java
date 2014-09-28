package dk.summerinnovationweek.futurehousing.fragment;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.adapter.MainPagerAdapter;
import dk.summerinnovationweek.futurehousing.adapter.RoomItemEntityAdapter;
import dk.summerinnovationweek.futurehousing.client.APICallListener;
import dk.summerinnovationweek.futurehousing.client.APICallManager;
import dk.summerinnovationweek.futurehousing.client.APICallTask;
import dk.summerinnovationweek.futurehousing.client.ResponseStatus;
import dk.summerinnovationweek.futurehousing.client.request.HouseRequest;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemAquariumEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemHeatingEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemLightEntity;
import dk.summerinnovationweek.futurehousing.geolocation.Geolocation;
import dk.summerinnovationweek.futurehousing.geolocation.GeolocationListener;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.Logcat;
import dk.summerinnovationweek.futurehousing.utility.NetworkManager;
import dk.summerinnovationweek.futurehousing.utility.Preferences;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class MainPagerFragment extends TaskFragment implements APICallListener, GeolocationListener
{
	private static final String META_REFRESH = "refresh";

	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private MainPagerAdapter mAdapter;
	private APICallManager mAPICallManager = new APICallManager();

	private Geolocation mGeolocation = null;
	private Location mLocation = null;

	private ViewPager mViewPager;
	private HouseEntity mHouseEntity;
	private int mHouseId;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_pager, container, false);

		mViewPager = (ViewPager) mRootView.findViewById(R.id.fragment_pager_view_pager);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);


		// load and show data
		if (mViewState == null || mViewState == ViewState.OFFLINE)
		{
			mHouseEntity = getHouseDummyData();

			renderView();
			showContent();
//			loadData();
		} else if (mViewState == ViewState.CONTENT)
		{
			if (mHouseEntity != null)
				renderView();
			showContent();
		} else if (mViewState == ViewState.PROGRESS)
		{
			showProgress();
		} else if (mViewState == ViewState.NO_LOCATION)
		{
			showNoLocation();
		}


		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}


	@Override
	public void onPause()
	{
		super.onPause();

		// stop adapter
//		if(mAdapter!=null) mAdapter.stop();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// cancel async tasks
		mAPICallManager.cancelAllTasks();
	}


	@Override
	public void onAPICallRespond(final APICallTask task, final ResponseStatus status, final Response<?> response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if (mRootView == null) return; // view was destroyed

				if (task.getRequest().getClass().equals(HouseRequest.class))
				{
					Response<HouseEntity> exampleResponse = (Response<HouseEntity>) response;

					// error
					if (exampleResponse.isError())
					{
						Logcat.d("Fragment.onAPICallRespond(HouseRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
								" / error / " + exampleResponse.getErrorType() + " / " + exampleResponse.getErrorMessage());

						// hide progress
						showContent();

						// handle error
						handleError(exampleResponse.getErrorType(), exampleResponse.getErrorMessage());
					}

					// response
					else
					{
						Logcat.d("Fragment.onAPICallRespond(HouseRequest): " + status.getStatusCode() + " " + status.getStatusMessage());

//						mSmartHouseEntity = exampleResponse.getResponseObject();
						mHouseEntity = exampleResponse.getResponseObject();

						if (mHouseEntity != null)
							renderView();

						// hide progress
						showContent();
					}
				}

				// finish request
				mAPICallManager.finishTask(task);

				// hide progress in action bar
				if (mAPICallManager.getTasksCount() == 0) showActionBarProgress(false);
			}
		});
	}


	@Override
	public void onAPICallFail(final APICallTask task, final ResponseStatus status, final Exception exception)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if (mRootView == null) return; // view was destroyed

				if (task.getRequest().getClass().equals(HouseRequest.class))
				{
					Logcat.d("Fragment.onAPICallFail(HouseRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
							" / " + exception.getClass().getSimpleName() + " / " + exception.getMessage());

					// hide progress
					showContent();

					// handle fail
					handleFail(exception);
				}

				// finish request
				mAPICallManager.finishTask(task);

				// hide progress in action bar
				if (mAPICallManager.getTasksCount() == 0) showActionBarProgress(false);
			}
		});
	}


	private void handleError(String errorType, String errorMessage)
	{
		// TODO: show dialog
	}


	private void handleFail(Exception exception)
	{
		int messageId;
		if (exception != null && exception.getClass().equals(UnknownHostException.class))
			messageId = R.string.global_apicall_unknown_host_toast;
		else if (exception != null && exception.getClass().equals(FileNotFoundException.class))
			messageId = R.string.global_apicall_not_found_toast;
		else if (exception != null && exception.getClass().equals(SocketTimeoutException.class))
			messageId = R.string.global_apicall_timeout_toast;
		else if (exception != null && exception.getClass().equals(JsonParseException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if (exception != null && exception.getClass().equals(ParseException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if (exception != null && exception.getClass().equals(NumberFormatException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if (exception != null && exception.getClass().equals(ClassCastException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else messageId = R.string.global_apicall_fail_toast;
		Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG).show();
	}


	private void loadData()
	{
		if (NetworkManager.isOnline(getActivity()))
		{
			if (!mAPICallManager.hasRunningTask(HouseRequest.class))
			{
				// show progress
				showProgress();

				// show progress in action bar
				showActionBarProgress(true);

				Preferences preferences = new Preferences(getActivity());

				// execute request
				HouseRequest request = new HouseRequest(mHouseId, preferences.getUserEmail(), preferences.getAuthToken());
				mAPICallManager.executeTask(request, this);
			}
		} else
		{
			showOffline();
		}
	}


	public void refreshData()
	{
		if (NetworkManager.isOnline(getActivity()))
		{
			if (!mAPICallManager.hasRunningTask(HouseRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);

				// meta data
				Bundle bundle = new Bundle();
				bundle.putBoolean(META_REFRESH, true);

				Preferences preferences = new Preferences(getActivity());

				// execute request
				HouseRequest request = new HouseRequest(0, preferences.getUserEmail(), preferences.getAuthToken());
				request.setMetaData(bundle);
				mAPICallManager.executeTask(request, this);
			}
		} else
		{
			Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public void onGeolocationRespond(Geolocation geolocation, final Location location)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed

				Logcat.d("Fragment.onGeolocationRespond(): " + location.getProvider() + " / " + location.getLatitude() + " / " + location.getLongitude() + " / " + new Date(location.getTime()).toString());
				mLocation = new Location(location);

				// TODO
			}
		});
	}


	@Override
	public void onGeolocationFail(Geolocation geolocation)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed

				Logcat.d("Fragment.onGeolocationFail()");

				// TODO
			}
		});
	}


	private void showActionBarProgress(boolean visible)
	{
		// show progress in action bar
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}


	private void changeActionBarTitle(String title)
	{
		getActivity().setTitle(title);
	}


	private void showContent()
	{
		// show list container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerNoLocation = (ViewGroup) mRootView.findViewById(R.id.container_no_location);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerNoLocation.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}


	private void showProgress()
	{
		// show progress container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerNoLocation = (ViewGroup) mRootView.findViewById(R.id.container_no_location);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		containerNoLocation.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}


	private void showOffline()
	{
		// show offline container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerNoLocation = (ViewGroup) mRootView.findViewById(R.id.container_no_location);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		containerNoLocation.setVisibility(View.GONE);
		mViewState = ViewState.OFFLINE;
	}


	private void showNoLocation()
	{
		// show no location container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		ViewGroup containerNoLocation = (ViewGroup) mRootView.findViewById(R.id.container_no_location);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerNoLocation.setVisibility(View.VISIBLE);
		mViewState = ViewState.NO_LOCATION;
	}


	private void renderView()
	{
		// pager content
		if (mAdapter == null)
		{
			// create adapter
			mAdapter = new MainPagerAdapter(getChildFragmentManager(), mHouseEntity);
		} else
		{
			// refill adapter
			mAdapter.refill(mHouseEntity);
		}

		// set adapter
		mViewPager.setAdapter(mAdapter);
//		mViewPager.setOffscreenPageLimit((mHouseEntity.getRoomList().size() + 3) / 2);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int i, float v, int i2)
			{

			}

			@Override
			public void onPageSelected(int i)
			{
				if (i == 0)
				{
					changeActionBarTitle("House overview");
					getActivity().getActionBar().setIcon(R.drawable.future_housing);
					String tag = MainPagerAdapter.getFragmentTag(mViewPager.getId(), mAdapter.getItemId(0));
					HouseFragment fragment = (HouseFragment) getChildFragmentManager().findFragmentByTag(tag);
					fragment.updateHouse();
				} else if (i < mHouseEntity.getRoomList().size() + 1)
				{
					changeActionBarTitle(mHouseEntity.getRoomList().get(i - 1).getName());
					getActivity().getActionBar().setIcon(R.drawable.back_arrow_image);
				} else if (i == mHouseEntity.getRoomList().size() + 1)
				{
					changeActionBarTitle("House statistics");
					getActivity().getActionBar().setIcon(R.drawable.back_arrow_image);
				} else
				{
					changeActionBarTitle("About us");
					getActivity().getActionBar().setIcon(R.drawable.back_arrow_image);
				}

			}


			@Override
			public void onPageScrollStateChanged(int i)
			{

			}
		});
	}


	public void showRoomWithId(int id)
	{
		if (mViewPager == null)
			return;

		for (int i = 0; i < mHouseEntity.getRoomList().size(); i++)
		{
			RoomEntity room = mHouseEntity.getRoomList().get(i);
			if (room.getId() == id)
			{
				mViewPager.setCurrentItem(i + 1);
			}
		}

	}


	public void showHouse()
	{
		if (mViewPager != null)
			mViewPager.setCurrentItem(0);
	}


	public void showStatistics()
	{
		if (mViewPager != null)
			mViewPager.setCurrentItem(mViewPager.getChildCount() - 2);
	}


	public void showAbout()
	{
		if (mViewPager != null)
			mViewPager.setCurrentItem(mViewPager.getChildCount() - 1);
	}


	public void setBackground(Bitmap background)
	{
		if (mViewPager != null && mAdapter != null)
		{
			String tag = MainPagerAdapter.getFragmentTag(mViewPager.getId(), mAdapter.getItemId(mViewPager.getCurrentItem()));
			RoomFragment fragment = (RoomFragment) getChildFragmentManager().findFragmentByTag(tag);
			fragment.setBackground(background);
		}

	}


	public boolean isOverviewShown()
	{
		if (mViewPager == null)
			return true;

		if (mViewPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public HouseEntity getHouseDummyData()
	{
		HouseEntity house = new HouseEntity();
		house.setId(0);
		house.setFloorPlan("drawable://" + R.drawable.house_floorplan);
		house.setFloorPlanWidth(738);
		house.setFloorPlanHeight(984);

		ArrayList<RoomEntity> rooms = new ArrayList<RoomEntity>();
		ArrayList<RoomItemEntity> roomItems;
		RoomItemLightEntity roomItemLight;
		RoomItemHeatingEntity roomItemHeating;
		RoomItemAquariumEntity roomItemAquarium;

		RoomEntity room = new RoomEntity(0, "Kitchen");
		room.setBackgroundPhotoUrl("drawable://" + R.drawable.background_kitchen);
		room.setFloorPlanMarginTop(90);
		room.setFloorPlanMarginLeft(165);
		room.setFloorPlanMarginRight(680);
		room.setFloorPlanMarginBottom(250);

		roomItems = new ArrayList<RoomItemEntity>();

		roomItemLight = new RoomItemLightEntity(0, RoomItemEntity.TYPE_LIGHT, "Main light");
		roomItemLight.setMeasuredLight(true);
		roomItemLight.setMeasuredLightPerc(80);
		roomItemLight.setUserLight(true);
		roomItemLight.setUserLightPerc(80);
		room.setItemLightEntity(roomItemLight);
		roomItems.add(roomItemLight);

		roomItemHeating = new RoomItemHeatingEntity(0, RoomItemEntity.TYPE_HEATING, "Central heating");
		roomItemHeating.setMeasuredTemperature(20);
		roomItemHeating.setUserTemperature(22);
		room.setItemHeatingEntity(roomItemHeating);
		roomItems.add(roomItemHeating);

		room.setRoomItemEntities(roomItems);
		rooms.add(room);


		room = new RoomEntity(1, "Living room");
		room.setBackgroundPhotoUrl("drawable://" + R.drawable.background_livingroom);
		room.setFloorPlanMarginTop(250);
		room.setFloorPlanMarginLeft(165);
		room.setFloorPlanMarginRight(420);
		room.setFloorPlanMarginBottom(680);

		roomItems = new ArrayList<RoomItemEntity>();

		roomItemLight = new RoomItemLightEntity(0, RoomItemEntity.TYPE_LIGHT, "Main light");
		roomItemLight.setMeasuredLight(true);
		roomItemLight.setMeasuredLightPerc(80);
		roomItemLight.setUserLight(true);
		roomItemLight.setUserLightPerc(80);
		room.setItemLightEntity(roomItemLight);
		roomItems.add(roomItemLight);

		roomItemHeating = new RoomItemHeatingEntity(0, RoomItemEntity.TYPE_HEATING, "Central heating");
		roomItemHeating.setMeasuredTemperature(22);
		roomItemHeating.setUserTemperature(20);
		roomItems.add(roomItemHeating);
		room.setItemHeatingEntity(roomItemHeating);

		roomItemAquarium = new RoomItemAquariumEntity(0, RoomItemEntity.TYPE_AQUARIUM, "Aquarium");
		roomItemAquarium.setSideLight(false);
		roomItemAquarium.setTopLight(true);
		roomItemAquarium.setUserTemperature(17);
		roomItemAquarium.setMeasuredTemperature(16);
		roomItems.add(roomItemAquarium);

		room.setRoomItemEntities(roomItems);
		rooms.add(room);

		room = new RoomEntity(2, "Dining room");
		room.setBackgroundPhotoUrl("drawable://" + R.drawable.background_diningroom);
		room.setFloorPlanMarginTop(280);
		room.setFloorPlanMarginLeft(480);
		room.setFloorPlanMarginRight(680);
		room.setFloorPlanMarginBottom(545);
		roomItems = new ArrayList<RoomItemEntity>();
		room.setRoomItemEntities(roomItems);
		rooms.add(room);


		room = new RoomEntity(3, "Bed room");
		room.setBackgroundPhotoUrl("drawable://" + R.drawable.background_bedroom);
		room.setFloorPlanMarginTop(680);
		room.setFloorPlanMarginLeft(165);
		room.setFloorPlanMarginRight(420);
		room.setFloorPlanMarginBottom(970);
		roomItems = new ArrayList<RoomItemEntity>();
		room.setRoomItemEntities(roomItems);
		rooms.add(room);

		room = new RoomEntity(4, "Study room");
		room.setBackgroundPhotoUrl("drawable://" + R.drawable.background_studyroom);
		room.setFloorPlanMarginTop(730);
		room.setFloorPlanMarginLeft(485);
		room.setFloorPlanMarginRight(680);
		room.setFloorPlanMarginBottom(970);
		roomItems = new ArrayList<RoomItemEntity>();
		room.setRoomItemEntities(roomItems);
		rooms.add(room);

		house.setRoomList(rooms);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoomItemEntity.class, new RoomItemEntityAdapter());
		Gson gson = gsonBuilder.create();

		String output = gson.toJson(house);

		Logcat.e(output);

		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File (sdCard.getAbsolutePath() + "/FutureHousing");
		directory.mkdirs();

		File file = new File(directory, "houseJson.txt");
		Logcat.e(file.getAbsolutePath());
		FileOutputStream fOut = null;
		try
		{
			fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(output);
			osw.flush();
			osw.close();
			Logcat.e("Done writing file.");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}


		return house;
	}
}
