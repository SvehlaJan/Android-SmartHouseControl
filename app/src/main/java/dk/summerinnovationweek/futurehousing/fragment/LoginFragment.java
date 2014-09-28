package dk.summerinnovationweek.futurehousing.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.activity.MainActivity;
import dk.summerinnovationweek.futurehousing.client.APICallListener;
import dk.summerinnovationweek.futurehousing.client.APICallManager;
import dk.summerinnovationweek.futurehousing.client.APICallTask;
import dk.summerinnovationweek.futurehousing.client.ResponseStatus;
import dk.summerinnovationweek.futurehousing.client.request.HouseRequest;
import dk.summerinnovationweek.futurehousing.client.request.LoginRequest;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.dialog.SimpleDialogFragment;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.LoginRequestEntity;
import dk.summerinnovationweek.futurehousing.entity.LoginResponseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemAquariumEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemHeatingEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemLightEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.Logcat;
import dk.summerinnovationweek.futurehousing.utility.Preferences;
import dk.summerinnovationweek.futurehousing.utility.Version;


public class LoginFragment extends TaskFragment implements APICallListener
{
	private static final String DIALOG_SIMPLE = "dialog_simple";

	private boolean mActionBarProgress = false;
	private View mRootView;
	private APICallManager mAPICallManager = new APICallManager();

	private LoginResponseEntity mLoginResponse;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_login, container, false);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		checkNewVersion();
		checkLaunch();

		checkAuthToken();
		renderView();
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


	private void checkAuthToken()
	{
		Preferences preferences = new Preferences(getActivity());
		if (preferences.getAuthToken() != Preferences.NULL_STRING)
		{
			Logcat.e(preferences.getAuthToken());
			Intent intent = new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
	}


	private void saveAuth(LoginResponseEntity loginEntity)
	{
		Preferences preferences = new Preferences(getActivity());
		preferences.setAuthToken(loginEntity.getAuthToken());
		preferences.setUserEmail(loginEntity.getEmail());
		preferences.setVersion(loginEntity.getActualVersion());
	}


	@Override
	public void onAPICallRespond(final APICallTask task, final ResponseStatus status, final Response<?> response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if (mRootView == null) return; // view was destroyed

				if (task.getRequest().getClass().equals(LoginRequest.class))
				{
					Response<LoginResponseEntity> loginResponse = (Response<LoginResponseEntity>) response;

					// error
					if (loginResponse.isError())
					{
						Logcat.d("Fragment.onAPICallRespond(HouseRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
								" / error / " + loginResponse.getErrorType() + " / " + loginResponse.getErrorMessage());

						// handle error
						handleError(loginResponse.getErrorType(), loginResponse.getErrorMessage());
					}

					// response
					else
					{
						Logcat.d("Fragment.onAPICallRespond(HouseRequest): " + status.getStatusCode() + " " + status.getStatusMessage());

						mLoginResponse = loginResponse.getResponseObject();
						saveAuth(mLoginResponse);

						Intent intent = new Intent(getActivity(), MainActivity.class);
						startActivity(intent);
						getActivity().finish();
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
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
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


	private void showActionBarProgress(boolean visible)
	{
		// show progress in action bar
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}


	private void renderView()
	{
		LinearLayout loginLinearLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_login_login_linear_layout);
		LinearLayout forgotPasswordLinearLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_login_forgot_password_linear_layout);

		loginLinearLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				login();
			}
		});

		forgotPasswordLinearLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				forgotPassword();
			}
		});

		EditText passwordEditText = (EditText) mRootView.findViewById(R.id.fragment_login_password_edit_text);
		passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_GO)
				{
					login();
				}
				return handled;
			}

		});
	}


	private void forgotPassword()
	{
//		Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
//		startActivity(intent);
	}


	private void login()
	{
		if (!mAPICallManager.hasRunningTask(LoginRequest.class))
		{
			EditText userIdEditText = (EditText) mRootView.findViewById(R.id.fragment_login_user_id_edit_text);
			EditText passwordEditText = (EditText) mRootView.findViewById(R.id.fragment_login_password_edit_text);

			String userId = userIdEditText.getText().toString();
			String password = passwordEditText.getText().toString();

			if (userId.length() > 0 && password.length() > 0)
			{
				showActionBarProgress(true);

				LoginRequest request = new LoginRequest(new LoginRequestEntity(userId, password));
				mAPICallManager.executeTask(request, this);
			} else
			{
				Toast.makeText(getActivity(), getString(R.string.login_invalid_credentials), Toast.LENGTH_LONG).show();
			}
		}
	}


	private void checkNewVersion()
	{
		Preferences preferences = new Preferences(getActivity());

		String currentVersion = Version.getVersionName(getActivity());
		String lastVersion = preferences.getVersion();

		// new version is available
		if (!currentVersion.equals(lastVersion))
		{
			if (Version.compareVersions(currentVersion, lastVersion) == 1)
			{
				// create and show the dialog
				SimpleDialogFragment newFragment = SimpleDialogFragment.newInstance(getActivity().getString(R.string.dialog_new_version_message), getActivity().getString(R.string.dialog_new_version_positive_label), getString(android.R.string.cancel));
				newFragment.setDialogListener(new SimpleDialogFragment.SimpleDialogListener()
				{
					@Override
					public void onSimpleDialogPositiveClick(DialogFragment dialog)
					{
						String appPackageName = getActivity().getPackageName();
						try
						{
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
						} catch (android.content.ActivityNotFoundException e)
						{
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
						}
					}


					@Override
					public void onSimpleDialogNegativeClick(DialogFragment dialog)
					{

					}
				});
				newFragment.show(getFragmentManager(), DIALOG_SIMPLE);
			}
		}
	}


	private void checkLaunch()
	{
		// get current launch
		final Preferences preferences = new Preferences(getActivity());
		final int launch = preferences.getLaunch();

		// check launch number
		boolean showDialog = false;
		if (!preferences.isRated())
		{
			if (launch == 3) showDialog = true;
			else if (launch >= 10 && launch % 10 == 0) showDialog = true;
		}

		// show rating dialog
		if (showDialog)
		{
			SimpleDialogFragment newFragment = SimpleDialogFragment.newInstance(getActivity().getString(R.string.dialog_rate_app_message),
					getActivity().getString(R.string.dialog_rate_app_positive_label), getString(android.R.string.cancel));
			newFragment.setDialogListener(new SimpleDialogFragment.SimpleDialogListener()
			{
				@Override
				public void onSimpleDialogPositiveClick(DialogFragment dialog)
				{
					String appPackageName = getActivity().getPackageName();
					try
					{
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
					} catch (android.content.ActivityNotFoundException e)
					{
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
					} finally
					{
						preferences.setRated(true);
					}
				}


				@Override
				public void onSimpleDialogNegativeClick(DialogFragment dialog)
				{

				}
			});
		}

		// increment launch
		preferences.setLaunch(launch + 1);
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

		return house;
	}
}
