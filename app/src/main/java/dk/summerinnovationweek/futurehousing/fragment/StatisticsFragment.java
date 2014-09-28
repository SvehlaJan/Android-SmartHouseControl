package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Arrays;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.StatisticsEntity;
import dk.summerinnovationweek.futurehousing.entity.statisticsItems.StatisticsItemConsumption;
import dk.summerinnovationweek.futurehousing.entity.statisticsItems.StatisticsItemPerson;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class StatisticsFragment extends TaskFragment
{
	private static final String ARGUMENT_DATA = "data";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;

	private StatisticsEntity mStatistics;

	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;
	private ImageView mBackgroundImageView;


	public static StatisticsFragment newInstance(StatisticsEntity statistics)
	{
		StatisticsFragment fragment = new StatisticsFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putSerializable(ARGUMENT_DATA, statistics);
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

		mStatistics = new StatisticsEntity();
		StatisticsItemConsumption consumption;
		StatisticsItemPerson person;
		mStatistics.setBackgroundPhotoUrl("drawable://" + R.drawable.background_statistics);
		mStatistics.setConsumptions(new ArrayList<StatisticsItemConsumption>());
		mStatistics.setPeopleList(new ArrayList<StatisticsItemPerson>());

		consumption = new StatisticsItemConsumption();
		consumption.setTitle("Power consumption this week: 13m3");
		consumption.setDetails("Last week: 21m3\nSmartly saved: 12%");
		consumption.setGraphTitle("Power consumption");
		consumption.setDomainLabel("Days");
		consumption.setRangeLabel("Consumption");
		consumption.setXVals(new ArrayList<Float>(Arrays.asList(new Float[]{3f, 5f, 4f, 5f, 5f, 3f, 4f})));
		consumption.setYVals(new ArrayList<Integer>(Arrays.asList(new Integer[]{21, 22, 23, 24, 25, 26, 27})));
		mStatistics.getConsumptions().add(consumption);

		consumption = new StatisticsItemConsumption();
		consumption.setTitle("Water consumption this week: 13m3");
		consumption.setDetails("Last week: 21m3\nSmartly saved: 12%");
		consumption.setGraphTitle("Water consumption");
		consumption.setDomainLabel("Days");
		consumption.setRangeLabel("Consumption");
		consumption.setXVals(new ArrayList<Float>(Arrays.asList(new Float[]{3f, 5f, 4f, 5f, 5f, 3f, 4f})));
		consumption.setYVals(new ArrayList<Integer>(Arrays.asList(new Integer[]{21, 22, 23, 24, 25, 26, 27})));
		mStatistics.getConsumptions().add(consumption);

		person = new StatisticsItemPerson();
		person.setName("Alice");
		person.setAge("28");
		person.setDetails("Hours of sleep today: 7\nYesterday: 5");
		person.setGraphTitle("Hours of sleep");
		person.setDomainLabel("Days");
		person.setRangeLabel("Consumption");
		person.setXVals(new ArrayList<Float>(Arrays.asList(new Float[]{7f, 7f, 6f, 6f, 8f, 8f, 6f})));
		person.setYVals(new ArrayList<Integer>(Arrays.asList(new Integer[]{21, 22, 23, 24, 25, 26, 27})));
		mStatistics.getPeopleList().add(person);


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


	private void handleArguments(Bundle arguments)
	{
		if (arguments.containsKey(ARGUMENT_DATA))
		{
			mStatistics = (StatisticsEntity) arguments.getSerializable(ARGUMENT_DATA);
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
		mRootView = inflater.inflate(R.layout.fragment_statistics, container, false);
		mBackgroundImageView = (ImageView) mRootView.findViewById(R.id.fragment_statistics_background);

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
			if (mStatistics != null) renderView();
			showContent();
		} else if (mViewState == ViewState.CONTENT)
		{
			if (mStatistics != null) renderView();
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
		mImageLoader.displayImage(mStatistics.getBackgroundPhotoUrl(), mBackgroundImageView, mDisplayImageOptions, mImageLoadingListener);

		final LinearLayout statisticsLinearLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_statistics_linear_layout);
//		LayoutTransition transition = statisticsLinearLayout.getLayoutTransition();
//		statisticsLinearLayout.setLayoutAnimation();
		LayoutInflater li = LayoutInflater.from(getActivity());

		int viewCounter = 0;
		for (StatisticsItemConsumption consumption : mStatistics.getConsumptions())
		{
			final View reportChild = li.inflate(R.layout.statistics_item_report, null);
			final View graphChild = li.inflate(R.layout.statistics_item_graph, null);

			final TextView titleTextView = (TextView) reportChild.findViewById(R.id.statistics_item_consumption_report_title_text_view);
			final TextView detailsTextView = (TextView) reportChild.findViewById(R.id.statistics_item_consumption_report_details_text_view);
			final ImageButton imageButton = (ImageButton) reportChild.findViewById(R.id.statistics_item_consumption_report_image_button);

			reportChild.setId(viewCounter);
			titleTextView.setText(consumption.getTitle());
			detailsTextView.setText(consumption.getDetails());
			imageButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int position;
					for (position = 0; position < statisticsLinearLayout.getChildCount(); position++)
					{
						if (statisticsLinearLayout.getChildAt(position).getId() == reportChild.getId())
							break;
					}

					if (graphChild.isShown())
					{
						statisticsLinearLayout.removeView(graphChild);
						imageButton.setImageResource(R.drawable.icn_expand_arrow);
					} else
					{
						statisticsLinearLayout.addView(graphChild, position + 1);
						imageButton.setImageResource(R.drawable.icn_collapse_arrow);
					}
				}
			});

			final XYPlot graph = (XYPlot) graphChild.findViewById(R.id.statistics_item_consumption_graph);


			XYSeries series1 = new SimpleXYSeries(consumption.getXVals(), consumption.getYVals(), "");

			LineAndPointFormatter series1Format = new LineAndPointFormatter();
			series1Format.setPointLabelFormatter(new PointLabelFormatter());
			series1Format.configure(getActivity(), R.xml.line_point_formatter_with_plf1);

			graph.addSeries(series1, series1Format);

			graph.setDomainLabel(consumption.getDomainLabel());
			graph.setRangeLabel(consumption.getRangeLabel());
			graph.setTitle(consumption.getGraphTitle());

			graph.setTicksPerRangeLabel(3);
			graph.getGraphWidget().setDomainLabelOrientation(-45);

			statisticsLinearLayout.addView(reportChild);

			viewCounter++;
		}

		for (StatisticsItemPerson person : mStatistics.getPeopleList())
		{
			final View reportChild = li.inflate(R.layout.statistics_item_report, null);
			final View graphChild = li.inflate(R.layout.statistics_item_graph, null);

			final TextView titleTextView = (TextView) reportChild.findViewById(R.id.statistics_item_consumption_report_title_text_view);
			final TextView detailsTextView = (TextView) reportChild.findViewById(R.id.statistics_item_consumption_report_details_text_view);
			final ImageButton imageButton = (ImageButton) reportChild.findViewById(R.id.statistics_item_consumption_report_image_button);

			reportChild.setId(viewCounter);
			titleTextView.setText(person.getName() + ", " + person.getAge());
			detailsTextView.setText(person.getDetails());
			imageButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int position;
					for (position = 0; position < statisticsLinearLayout.getChildCount(); position++)
					{
						if (statisticsLinearLayout.getChildAt(position).getId() == reportChild.getId())
							break;
					}

					if (graphChild.isShown())
					{
						statisticsLinearLayout.removeView(graphChild);
						imageButton.setImageResource(R.drawable.icn_expand_arrow);
					} else
					{
						statisticsLinearLayout.addView(graphChild, position + 1);
						imageButton.setImageResource(R.drawable.icn_collapse_arrow);
					}
				}
			});

			final XYPlot graph = (XYPlot) graphChild.findViewById(R.id.statistics_item_consumption_graph);


			XYSeries series1 = new SimpleXYSeries(person.getXVals(), person.getYVals(), "");

			LineAndPointFormatter series1Format = new LineAndPointFormatter();
			series1Format.setPointLabelFormatter(new PointLabelFormatter());
			series1Format.configure(getActivity(), R.xml.line_point_formatter_with_plf1);

			graph.addSeries(series1, series1Format);

			graph.setDomainLabel(person.getDomainLabel());
			graph.setRangeLabel(person.getRangeLabel());
			graph.setTitle(person.getGraphTitle());

			graph.setTicksPerRangeLabel(3);
			graph.getGraphWidget().setDomainLabelOrientation(-45);

			statisticsLinearLayout.addView(reportChild);

			viewCounter++;
		}
	}
}
