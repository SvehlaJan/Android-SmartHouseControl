package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.Logcat;
import dk.summerinnovationweek.futurehousing.view.ViewState;

public class RoomFragment extends TaskFragment
{
	private static final String ARGUMENT_ROOM = "room";
	private static final String ARGUMENT_BACKGROUND = "background";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;

	private RoomEntity mRoom;
	private String mBackgroundPath;
    Button button;
    ImageView image;

	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;
	private ImageView mBackgroundImageView;

	public static RoomFragment newInstance(RoomEntity room, String backgroundPath)
	{
        RoomFragment fragment = new RoomFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putSerializable(ARGUMENT_ROOM, room);
		arguments.putString(ARGUMENT_BACKGROUND, backgroundPath);
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

		// image caching options
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(Color.TRANSPARENT)
				.showImageForEmptyUri(Color.TRANSPARENT)
				.showImageOnFail(Color.TRANSPARENT)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();
		mImageLoadingListener = new SimpleImageLoadingListener();
	}

    private void handleArguments(Bundle arguments) {
        if (arguments.containsKey(ARGUMENT_ROOM)) {
            mRoom = (RoomEntity) arguments.getSerializable(ARGUMENT_ROOM);
			mBackgroundPath = arguments.getString(ARGUMENT_BACKGROUND);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewPager mRootView = (ViewPager) mRootView.findViewById(R.id.fragment_room);
        mRootView = inflater.inflate(R.layout.fragment_room, container, false);
        image = (ImageView) mRootView.findViewById(R.id.ivLightOn);
        button = (ToggleButton) mRootView.findViewById(R.id.toggleButton);
		mBackgroundImageView = (ImageView) mRootView.findViewById(R.id.fragment_room_background);

        return mRootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // load and show data
        if (mViewState == null || mViewState == ViewState.OFFLINE) {
			if (mRoom != null) renderView();
            showContent();
        } else if (mViewState == ViewState.CONTENT) {
            if (mRoom != null) renderView();
            showContent();
        } else if (mViewState == ViewState.PROGRESS) {
            showProgress();
        } else if (mViewState == ViewState.EMPTY) {
            showEmpty();
        }

        // progress in action bar
        showActionBarProgress(mActionBarProgress);
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save current instance state
        super.onSaveInstanceState(outState);
//		setUserVisibleHint(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // action bar menu
        super.onCreateOptionsMenu(menu, inflater);

        // TODO
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar menu behaviour
        return super.onOptionsItemSelected(item);

        // TODO
    }

    private void showActionBarProgress(boolean visible) {
        // show action bar progress
        ((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
        mActionBarProgress = visible;
    }


    private void showContent() {
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


    private void showProgress() {
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


    private void showOffline() {
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


    private void showEmpty() {
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
		{
			mBackgroundImageView.setImageBitmap(bitmap);
		}
	}


    private void renderView() {
		mImageLoader.displayImage(mBackgroundPath, mBackgroundImageView, mDisplayImageOptions, mImageLoadingListener);

        // http://smarthouses.summerinnovationweek.dk/Api/getHouse.php?houseID=1
        // http://blog.csdn.net/sun_star1chen/article/details/16330459

        final int[][] bgArray = {{0, 0, 255}, {0, 17, 238}, {0, 34, 221}, {0, 51, 204}, {0, 68, 187}, {0, 85, 170}, {0, 102, 153}, {0, 119, 136},
                {0, 136, 119}, {0, 153, 102}, {0, 170, 85}, {0, 187, 68}, {0, 204, 51}, {0, 221, 34}, {0, 238, 17}, {0, 255, 0}};
        final int[][] grArray = {{0, 255, 0}, {17, 238, 0}, {34, 221, 0}, {51, 204, 0}, {68, 187, 0}, {85, 170, 0}, {102, 153, 0}, {119, 136, 0},
                {136, 119, 0}, {153, 102, 0}, {170, 85, 0}, {187, 68, 0}, {204, 51, 0}, {221, 34, 0}, {238, 17, 0}, {255, 0, 0}};


		LinearLayout linearLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_room_linear_layout);

		if (mRoom.getName().equalsIgnoreCase("Living room"))
		{
			RelativeLayout fireplaceLayout = (RelativeLayout) View.inflate(getActivity(), R.layout.fireplace_layout, null);
			linearLayout.addView(fireplaceLayout, linearLayout.getChildCount(), ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		else if (mRoom.getName().equalsIgnoreCase("Kitchen"))
		{

		}

/*
       final SeekBar sb = (SeekBar) mRootView.findViewById(R.id.seekBar);
       sb.setProgress(0);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int red = 0, green = 0, blue = 0;
                seekBar.setMax(15);
                int seekValue = sb.getProgress();
                int temp = 15 + seekValue;

                if (temp <= 20) {
                    red = bgArray[seekValue][0];
                    green = bgArray[seekValue][1];
                    blue = bgArray[seekValue][2];
                } else {
                    red = grArray[seekValue][0];
                    green = grArray[seekValue][1];
                    blue = grArray[seekValue][2];
                }

                sb.setBackgroundColor(Color.rgb(red, green, blue));
                sb.invalidate();

                String val = Integer.toString(temp);
                //   tvTemp.setText("Temperature: " + val);
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
*/

        final TextView tvDesTemp = (TextView) mRootView.findViewById((R.id.tvDesTemp));
        final TextView tvACTemp = (TextView) mRootView.findViewById((R.id.tvACTemp));

        final ProgressBar pb = (ProgressBar) mRootView.findViewById(R.id.progressBar);
        pb.setProgress(mRoom.getMeasuredTemperature());

        final NumberPicker np = (NumberPicker) mRootView.findViewById(R.id.numberPicker);

		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setMaxValue(30);
        np.setMinValue(15);
        np.setFadingEdgeLength(5);
		np.setValue(mRoom.getInputTemperature());

		tvACTemp.setText("Actual temperature: " + mRoom.getMeasuredTemperature());
		tvDesTemp.setText(Integer.toString(mRoom.getInputTemperature()));
        
// http://www.i-programmer.info/programming/android/6418-android-adventures-pickers.html?start=2
// http://stackoverflow.com/questions/11069236/can-anyone-recommend-an-open-source-number-picker-for-ics

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {

                int acTemp = mRoom.getMeasuredTemperature();
                String acTempStr = Integer.toString(acTemp);
                pb.setProgress(acTemp);

                if (acTemp > 15 || acTemp < 46) {
                    pb.setBackgroundColor(grArray[acTemp - 15][1]);
                    tvACTemp.setText("Actual temperature: " + acTempStr);
                }

                String tvDesTempStr = Integer.toString(newVal);
                tvDesTemp.setText(tvDesTempStr);
				mRoom.setInputTemperature(newVal);

            }
        });

        np.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                int oldValue ;
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    //We get the different between oldValue and the new value

                    //  int valueDiff = numberPicker.getValue() - oldValue;
                    Toast.makeText(getActivity(), "Value is now: " + Integer.toString(numberPicker.getValue()), Toast.LENGTH_SHORT).show();

                    //Update oldValue to the new value for the next scroll
                    oldValue = numberPicker.getValue();

                    //Do action with valueDiff
                }

            }
        });

		if (mRoom.isMeasuredIsLightOn())
			image.setImageResource(R.drawable.bulb_on);
		else
			image.setImageResource(R.drawable.bulb_off);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean on = ((ToggleButton) view).isChecked();
                if (on == true) {
                    image.setImageResource(R.drawable.bulb_on);
					mRoom.setInputIsLightOn(true);
					mRoom.setMeasuredIsLightOn(true);
                    // bg.setBackgroundColor(0xFFF3F3F3);
                } else {
                    image.setImageResource(R.drawable.bulb_off);
					mRoom.setInputIsLightOn(false);
					mRoom.setMeasuredIsLightOn(false);
                    //bg.setBackgroundColor(0xFF000000);
                }
            }
        });
    }
}
