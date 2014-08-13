package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.view.ViewState;

public class RoomFragment extends TaskFragment
{
	private static final String ARGUMENT_ROOM = "room";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;

	private RoomEntity mRoom;
    Button button;
    ImageView image;


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
		if(arguments != null)
		{
			handleArguments(arguments);
		}
	}

	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(ARGUMENT_ROOM))
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
        //ViewPager mRootView = (ViewPager) mRootView.findViewById(R.id.fragment_room);
		mRootView = inflater.inflate(R.layout.fragment_room, container, false);
        image = (ImageView) mRootView.findViewById(R.id.ivLightOn);
        button = (ToggleButton) mRootView.findViewById(R.id.toggleButton);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.OFFLINE)
		{
			showContent();
		}
		else if(mViewState==ViewState.CONTENT)
		{
			if(mRoom !=null) renderView();
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
	
	
	private void renderView()
	{/*
        ImageView kitchenlight = (ImageView) mRootView.findViewById(R.id.);
        RoomEntity re = mRoom;
        if (re.isMeasuredIsLightOn())
        {
            kitchenlight.setImageResource(R.drawable.lightbulb);
        } else {
            kitchenlight.setImageResource(R.drawable.lightbulb_off);
        }

        boolean on = ((ToggleButton) view).isChecked();
        if (on == true) {
            image.setImageResource(R.drawable.bulb_on);
            // bg.setBackgroundColor(0xFFF3F3F3);
        } else {
            image.setImageResource(R.drawable.bulb_off);
            //bg.setBackgroundColor(0xFF000000);
        }
*/

        addListenerOnButton();
	}

    public void addListenerOnButton() {

        final int[][] bgArray = {{0, 0, 255}, {0, 17, 238}, {0, 34, 221}, {0, 51, 204}, {0, 68, 187}, {0, 85, 170}, {0, 102, 153}, {0, 119, 136}, {0, 136, 119}, {0, 153, 102}, {0, 170, 85}, {0, 187, 68}, {0, 204, 51}, {0, 221, 34}, {0, 238, 17}, {0, 255, 0}};
        final int[][] grArray = {{0, 255, 0}, {17, 238, 0}, {34, 221, 0}, {51, 204, 0}, {68, 187, 0}, {85, 170, 0}, {102, 153, 0}, {119, 136, 0}, {136, 119, 0}, {153, 102, 0}, {170, 85, 0}, {187, 68, 0}, {204, 51, 0}, {221, 34, 0}, {238, 17, 0}, {255, 0, 0}};
        final int[][] rbArray = {{255, 0, 0}, {238, 0, 17}, {221, 0, 34}, {204, 0, 51}, {187, 0, 68}, {170, 0, 85}, {153, 0, 102}, {136, 0, 119}, {119, 0, 136}, {102, 0, 153}, {85, 0, 170}, {68, 0, 187}, {51, 0, 204}, {34, 0, 221}, {17, 0, 238}, {0, 0, 255}};

        // final LinearLayout bg = (LinearLayout) findViewById(R.id.fragment_room_content);
        final TextView tvTemp = (TextView) mRootView.findViewById((R.id.tvTemp));
        final SeekBar sb = (SeekBar) mRootView.findViewById(R.id.seekBar);
        sb.setProgress(0);



        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setMax(15);
                int seekValue = sb.getProgress();
                int temp = 15 + seekValue;

                int red = grArray[seekValue][0];
                int green = grArray[seekValue][1];
                int blue = grArray[seekValue][2];

                sb.setBackgroundColor(Color.rgb(red, green, blue));
                sb.invalidate();

                String val = Integer.toString(temp);
                tvTemp.setText("Temperature: " + val);

/*                int seekValue = sb.getProgress();
                int tColor = (int) abs(seekValue * 2.5);  // 1-100
                sb.setBackgroundColor(Color.rgb(tColor, seekValue, seekValue));
                sb.invalidate();

                // przedzial 15 - 30deg, seek: 1-100
                int temp = 15 + abs(seekValue / 8);
                String val = Integer.toString(temp);
                tvTemp.setText("Temperature: " + val);
*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    ;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                boolean on = ((ToggleButton) view).isChecked();
                if (on == true) {
                    image.setImageResource(R.drawable.bulb_on);
                    // bg.setBackgroundColor(0xFFF3F3F3);
                } else {
                    image.setImageResource(R.drawable.bulb_off);
                    //bg.setBackgroundColor(0xFF000000);
                }

            }
        });

    }
}
