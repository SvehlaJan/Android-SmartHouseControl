package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.FragmentUtils;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class HouseFragment extends TaskFragment
{
	private static final String ARGUMENT_HOUSE = "house";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;

    TextView temperaturekitchen;
    TextView temperaturedinningroom;
    TextView temperaturelivingroom;
    TextView temperatureoffice;
    TextView temperatureguest;
    Button kitchen;
    Button dinningroom;
    Button livingroom;
    Button office;
    Button guest;
    ImageView kitchenlight;
    ImageView dinningroomlight;
    ImageView livingroomlight;
    ImageView officelight;
    ImageView guestlight;

	private HouseEntity mHouse;

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
		if(arguments != null)
		{
			handleArguments(arguments);
		}
	}

	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(ARGUMENT_HOUSE))
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
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.OFFLINE)
		{
            renderView();
			showContent();
		}
		else if(mViewState==ViewState.CONTENT)
		{
			if(mHouse !=null) renderView();
			showContent();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}
		else if(mViewState==ViewState.EMPTY)
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
		renderView();
	}
	
	
	private void renderView()
	{
        RoomEntity kitchenroom = getRoomByName(mHouse, "Kitchen");
        if (kitchenroom != null ) {
            kitchenlight = (ImageView) mRootView.findViewById(R.id.kitchenlight);
            if (kitchenroom.isMeasuredLight()) {
                kitchenlight.setImageResource(R.drawable.lightbulb_off);
            } else {
                kitchenlight.setImageResource(R.drawable.lightbulb);
            }
            temperaturekitchen = (TextView) mRootView.findViewById(R.id.temperaturekitchen);
            temperaturekitchen.setText(Integer.toString(kitchenroom.getMeasuredTemperature()));

            if (kitchenroom.getInputTemperature() == kitchenroom.getMeasuredTemperature()) {
                temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
            }

            if (kitchenroom.getInputTemperature() > kitchenroom.getMeasuredTemperature()) {
                temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
            }

            if (kitchenroom.getInputTemperature() < kitchenroom.getMeasuredTemperature()) {
                temperaturekitchen.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
            }
        }


        RoomEntity livingroom = getRoomByName(mHouse, "Living room");
        if (livingroom != null ) {
            livingroomlight = (ImageView) mRootView.findViewById(R.id.livingroomlight);
            if (livingroom.isMeasuredLight()) {
                livingroomlight.setImageResource(R.drawable.lightbulb_off);
            } else {
                livingroomlight.setImageResource(R.drawable.lightbulb);
            }
            temperaturelivingroom = (TextView) mRootView.findViewById(R.id.temperaturelivingroom);
            temperaturelivingroom.setText(Integer.toString(livingroom.getMeasuredTemperature()));

            if (livingroom.getInputTemperature() == livingroom.getMeasuredTemperature()) {
                temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
            }

            if (livingroom.getInputTemperature() > livingroom.getMeasuredTemperature()) {
                temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
            }

            if (livingroom.getInputTemperature() < livingroom.getMeasuredTemperature()) {
                temperaturelivingroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
            }

        }

        RoomEntity dinningroom = getRoomByName(mHouse, "Dining room");
        if (dinningroom != null ) {
            dinningroomlight = (ImageView) mRootView.findViewById(R.id.dinningroomlight);
            if (dinningroom.isMeasuredLight()) {
                dinningroomlight.setImageResource(R.drawable.lightbulb);
            } else {
                dinningroomlight.setImageResource(R.drawable.lightbulb_off);
            }

            temperaturedinningroom = (TextView) mRootView.findViewById(R.id.temperaturedinningroom);
            temperaturedinningroom.setText(Integer.toString(dinningroom.getMeasuredTemperature()));

            if (dinningroom.getInputTemperature() == dinningroom.getMeasuredTemperature()) {
                temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
            }

            if (dinningroom.getInputTemperature() > dinningroom.getMeasuredTemperature()) {
                temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
            }

            if (dinningroom.getInputTemperature() < dinningroom.getMeasuredTemperature()) {
                temperaturedinningroom.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
            }
        }

        RoomEntity officeroom = getRoomByName(mHouse, "Office");

        if (officeroom != null ) {

            officelight = (ImageView) mRootView.findViewById(R.id.officelight);
            if (officeroom.isMeasuredLight()) {
                officelight.setImageResource(R.drawable.lightbulb);
            } else {
                officelight.setImageResource(R.drawable.lightbulb_off);
            }

            temperatureoffice = (TextView) mRootView.findViewById(R.id.temperatureoffice);
            temperatureoffice.setText(Integer.toString(officeroom.getMeasuredTemperature()));

            if (officeroom.getInputTemperature() == officeroom.getMeasuredTemperature()) {
                temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
            }

            if (officeroom.getInputTemperature() > officeroom.getMeasuredTemperature()) {
                temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
            }

            if (officeroom.getInputTemperature() < officeroom.getMeasuredTemperature()) {
                temperatureoffice.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
            }

        }

        RoomEntity guestroom = getRoomByName(mHouse, "Guest room");
        if (guestroom != null ) {


            guestlight = (ImageView) mRootView.findViewById(R.id.guestlight);

            if (guestroom.isMeasuredLight()) {
                guestlight.setImageResource(R.drawable.lightbulb);
            } else {
                guestlight.setImageResource(R.drawable.lightbulb_off);
            }
            temperatureguest = (TextView) mRootView.findViewById(R.id.temperatureguest);
            temperatureguest.setText(Integer.toString(guestroom.getMeasuredTemperature()));

            if (guestroom.getInputTemperature() == guestroom.getMeasuredTemperature()) {
                temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_green));
            }

            if (guestroom.getInputTemperature() > guestroom.getMeasuredTemperature()) {
                temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_blue));
            }

            if (guestroom.getInputTemperature() < guestroom.getMeasuredTemperature()) {
                temperatureguest.setBackgroundDrawable(getResources().getDrawable(R.drawable.temperature_shape_red));
            }
        }

        addListenerOnButton();
	}

    public void addListenerOnButton() {

        final MainPagerFragment fragment = FragmentUtils.getParent(this, MainPagerFragment.class);

        kitchen = (Button) mRootView.findViewById(R.id.kitchen);
        kitchen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                fragment.showRoom(1);

                //TODO
                Log.e("TAG", "Kitchen" );


            }

        });

        dinningroom = (Button) mRootView.findViewById(R.id.dinningroom);
        dinningroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "dinningroom" );
                fragment.showRoom(5);


            }

        });
        livingroom = (Button) mRootView.findViewById(R.id.livingroom);
        livingroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "livingroom" );
                fragment.showRoom(3);


            }

        });
        office = (Button) mRootView.findViewById(R.id.office);
        office.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "office" );
                fragment.showRoom(4);


            }

        });

        guest = (Button) mRootView.findViewById(R.id.guest);
        guest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TODO
                Log.e("TAG", "guest" );
                fragment.showRoom(2);



            }

        });

    }
}
