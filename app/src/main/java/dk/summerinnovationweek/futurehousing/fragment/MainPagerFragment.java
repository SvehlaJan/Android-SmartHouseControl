package dk.summerinnovationweek.futurehousing.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.adapter.MainPagerAdapter;
import dk.summerinnovationweek.futurehousing.client.APICallListener;
import dk.summerinnovationweek.futurehousing.client.APICallManager;
import dk.summerinnovationweek.futurehousing.client.APICallTask;
import dk.summerinnovationweek.futurehousing.client.ResponseStatus;
import dk.summerinnovationweek.futurehousing.client.request.HouseRequest;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.SmartHouseEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.Logcat;
import dk.summerinnovationweek.futurehousing.utility.NetworkManager;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class MainPagerFragment extends TaskFragment implements APICallListener
{
	private static final String META_REFRESH = "refresh";

	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private MainPagerAdapter mAdapter;
	private APICallManager mAPICallManager = new APICallManager();

	private ViewPager mViewPager;
	private HouseEntity mHouseEntity;
	private BitmapDrawable mBackgroundImage;


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
		if(mViewState==null || mViewState==ViewState.OFFLINE)
		{
            HouseEntity house = new HouseEntity();
            house.setId(1);

            ArrayList<RoomEntity> rooms = new ArrayList<RoomEntity>();

            RoomEntity room = new RoomEntity(1, "Kitchen", true, 20);
            room.setInputIsLightOn(true);
            room.setInputTemperature(22);
            rooms.add(room);

            room = new RoomEntity(2, "Guest room", false, 17);
            room.setInputIsLightOn(false);
            room.setInputTemperature(17);
            rooms.add(room);

            room = new RoomEntity(3, "Living room", true, 23);
            room.setInputIsLightOn(true);
            room.setInputTemperature(22);
            rooms.add(room);

            room = new RoomEntity(4, "Office", false, 22);
            room.setInputIsLightOn(true);
            room.setInputTemperature(22);
            rooms.add(room);

            room = new RoomEntity(5, "Dining room", false, 20);
            room.setInputIsLightOn(false);
            room.setInputTemperature(20);
            rooms.add(room);

            house.setRoomList(rooms);

            mHouseEntity = house;

			/*
			Gson gson = new Gson();

			String gsonString = "{\"smarthouse\":{\"house\":{\"id\":\"1\",\"name\":\"Innovation House\",\"floorPlanPicture\":\"http:\\/\\/is.gd\\/qsAKnw\",\"rooms\":{\"room\":[{\"id\":\"1\",\"name\":\"Kitchen\",\"temperature\":\"22\",\"light\":\"false\"},{\"id\":\"2\",\"name\":\"Living room\",\"temperature\":\"22\",\"light\":\"true\"}]}},\"users\":{\"user\":[{\"id\":\"1\",\"houseId\":\"1\",\"username\":\"admin\",\"password\":\"password\",\"isAdmin\":\"true\",\"analytics\":{\"data\":[{\"id\":\"1\",\"hoursOfSleep\":\"6\",\"weight\":\"70\",\"date\":\"2014-08-12T07:30:00\"},{\"id\":\"2\",\"hoursOfSleep\":\"6.5\",\"weight\":\"72\",\"date\":\"2014-08-13T07:32:00\"}]}},{\"id\":\"2\",\"houseId\":\"1\",\"username\":\"user\",\"password\":\"password\",\"isAdmin\":\"false\",\"analytics\":{\"data\":[{\"id\":\"1\",\"hoursOfSleep\":\"8\",\"weight\":\"50\",\"date\":\"2014-08-12T07:33:00\"},{\"id\":\"2\",\"hoursOfSleep\":\"7.7\",\"weight\":\"49.5\",\"date\":\"2014-08-13T07:35:00\"}]}}]}}}";
			JSONObject jsonObject;
			JSONArray data = null;
			try
			{
				jsonObject = new JSONObject(gsonString);
				data = jsonObject.getJSONArray("houseEntity");
			} catch (JSONException e)
			{
				e.printStackTrace();
			}

			SmartHouseEntity smartHouseEntity = gson.fromJson(data, );

			Logcat.e(smartHouseEntity.getHouseEntity().getName());
			*/


            renderView();
            showContent();
//			loadData();
		}
		else if(mViewState==ViewState.CONTENT)
		{
			if(mHouseEntity!=null)
				renderView();
			showContent();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
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
				if(mRootView==null) return; // view was destroyed
				
				if(task.getRequest().getClass().equals(HouseRequest.class))
				{
					Response<HouseEntity> exampleResponse = (Response<HouseEntity>) response;
					
					// error
					if(exampleResponse.isError())
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
				if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
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
				if(mRootView==null) return; // view was destroyed
				
				if(task.getRequest().getClass().equals(HouseRequest.class))
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
				if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
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
		if(exception!=null && exception.getClass().equals(UnknownHostException.class)) messageId = R.string.global_apicall_unknown_host_toast;
		else if(exception!=null && exception.getClass().equals(FileNotFoundException.class)) messageId = R.string.global_apicall_not_found_toast;
		else if(exception!=null && exception.getClass().equals(SocketTimeoutException.class)) messageId = R.string.global_apicall_timeout_toast;
		else if(exception!=null && exception.getClass().equals(JsonParseException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(ParseException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(NumberFormatException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(ClassCastException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else messageId = R.string.global_apicall_fail_toast;
		Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG).show();
	}
	
	
	private void loadData()
	{
		/*
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mAPICallManager.hasRunningTask(HouseRequest.class))
			{
				// show progress
				showProgress();

				// show progress in action bar
				showActionBarProgress(true);
				
				// execute request
				HouseRequest request = new HouseRequest(0);
				mAPICallManager.executeTask(request, this);
			}
		}
		else
		{
			showOffline();
		}
		*/
	}


	public void refreshData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mAPICallManager.hasRunningTask(HouseRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);
				
				// meta data
				Bundle bundle = new Bundle();
				bundle.putBoolean(META_REFRESH, true);
				
				// execute request
				HouseRequest request = new HouseRequest(0);
				request.setMetaData(bundle);
				mAPICallManager.executeTask(request, this);
			}
		}
		else
		{
			Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void showActionBarProgress(boolean visible)
	{
		// show progress in action bar
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}

	private void changeActionBarTitle(String title)
	{
		((ActionBarActivity) getActivity()).setTitle(title);
	}

	
	
	private void showContent()
	{
		// show list container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.OFFLINE;
	}

	
	private void renderView()
	{
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new MainPagerAdapter(getChildFragmentManager(), mHouseEntity);
		}
		else
		{
			// refill adapter
			mAdapter.refill(mHouseEntity);
		}

		// set adapter
		mViewPager.setAdapter(mAdapter);
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
				}
				else if (i < mHouseEntity.getRoomList().size() + 1)
				{
					changeActionBarTitle(mHouseEntity.getRoomList().get(i - 1).getName());
				}
				else
				{
					changeActionBarTitle("House statistics");
				}

			}
			@Override
			public void onPageScrollStateChanged(int i)
			{

			}
		});
	}


	public void showRoom(int id)
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

	public void setBackground(Bitmap background)
	{
		if (mViewPager != null)
		{
			mBackgroundImage = new BitmapDrawable(background);
			mViewPager.getChildAt(mViewPager.getCurrentItem()).setBackgroundDrawable(mBackgroundImage);
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
}
