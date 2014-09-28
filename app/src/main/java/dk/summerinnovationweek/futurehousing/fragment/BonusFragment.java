package dk.summerinnovationweek.futurehousing.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptGroup;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.ScriptIntrinsicColorMatrix;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.task.TaskFragment;
import dk.summerinnovationweek.futurehousing.utility.Logcat;
import dk.summerinnovationweek.futurehousing.view.ViewState;


public class BonusFragment extends TaskFragment
{
	private static final String ARGUMENT_BACKGROUND = "background";
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;

	private String mBackgroundPath;

	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;
	private ImageView mBackgroundImageView;
	private View mBonusView;
	private LinearLayout mLinearLayout;


	public static BonusFragment newInstance(String backgroundPath)
	{
		BonusFragment fragment = new BonusFragment();

		// arguments
		Bundle arguments = new Bundle();
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
				.cacheOnDisc(true)
				.displayer(new SimpleBitmapDisplayer())
				.build();
		mImageLoadingListener = new SimpleImageLoadingListener();
	}


	private void handleArguments(Bundle arguments)
	{
		mBackgroundPath = arguments.getString(ARGUMENT_BACKGROUND);
	}


	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_bonus, container, false);
		mBackgroundImageView = (ImageView) mRootView.findViewById(R.id.fragment_bonus_background);
		mBonusView = mRootView.findViewById(R.id.bonus_view);
		mLinearLayout = (LinearLayout) mRootView.findViewById(R.id.bonus_fragment_linear_layout);

		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// load and show data
		if (mViewState == null || mViewState == ViewState.OFFLINE)
		{
			renderView();
			showContent();
		} else if (mViewState == ViewState.CONTENT)
		{
			renderView();
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


	public void setBackground(Bitmap inBitmap)
	{
		int width = mRootView.getMeasuredWidth();
		int height = mRootView.getMeasuredHeight();
		Bitmap preparedBitmap = prepareBitmap(inBitmap, width, height);

//		setBlurredBackground(preparedBitmap);

		mBackgroundImageView.setImageBitmap(preparedBitmap);
//		setBlurredView(preparedBitmap, mLinearLayout);
//		setGrayscaleView(preparedBitmap, mBonusView);
		setGrayscaleBlurView(preparedBitmap, mLinearLayout);
	}


	private void setBlurredBackground(Bitmap inBitmap)
	{
		long startMs = System.currentTimeMillis();
		int width = mRootView.getMeasuredWidth();
		int height = mRootView.getMeasuredHeight();

		Bitmap blurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		final RenderScript rs = RenderScript.create(getActivity());
		final Allocation input = Allocation.createFromBitmap(rs, inBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
		final Allocation output = Allocation.createTyped(rs, input.getType());

		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		script.setRadius(15f); // 0 < radius <= 25
		script.setInput(input);
		script.forEach(output);
		output.copyTo(blurBitmap);

		rs.destroy();

		if (mBackgroundImageView != null)
			mBackgroundImageView.setImageBitmap(blurBitmap);
		Toast.makeText(getActivity(), System.currentTimeMillis() - startMs + " ms", Toast.LENGTH_SHORT).show();
	}


	private void setBlurredView(Bitmap inBitmap, View view)
	{
		long startMs = System.currentTimeMillis();

		Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);

		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(inBitmap, 0, 0, null);

		final RenderScript rs = RenderScript.create(getActivity());
		final Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);

		final ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
		blur.setInput(overlayAlloc);
		blur.setRadius(20f);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(overlay);

		view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));

		rs.destroy();
		Toast.makeText(getActivity(), System.currentTimeMillis() - startMs + " ms", Toast.LENGTH_SHORT).show();
	}


	private void setGrayscaleView(Bitmap inBitmap, View view)
	{
		long startMs = System.currentTimeMillis();

		Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Bitmap grayBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);

		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(inBitmap, 0, 0, null);

		final RenderScript rs = RenderScript.create(getActivity());
		final Allocation input = Allocation.createFromBitmap(rs, overlay);
		final Allocation output = Allocation.createTyped(rs, input.getType());
		final ScriptIntrinsicColorMatrix scriptColor = ScriptIntrinsicColorMatrix.create(rs, Element.U8_4(rs));
		scriptColor.setGreyscale();
		scriptColor.forEach(input, output);
		output.copyTo(grayBitmap);

		view.setBackgroundDrawable(new BitmapDrawable(getResources(), grayBitmap));

		rs.destroy();
		Toast.makeText(getActivity(), System.currentTimeMillis() - startMs + " ms", Toast.LENGTH_SHORT).show();
	}


	private void setGrayscaleBlurView(Bitmap inBitmap, View view)
	{
		long startMs = System.currentTimeMillis();

		Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Bitmap outBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);

		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(inBitmap, 0, 0, null);

		final RenderScript rs = RenderScript.create(getActivity());
		final Allocation input = Allocation.createFromBitmap(rs, overlay);
		final Allocation output = Allocation.createTyped(rs, input.getType());

		final ScriptIntrinsicBlur scriptBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		scriptBlur.setRadius(20f);

		final ScriptIntrinsicColorMatrix scriptColor = ScriptIntrinsicColorMatrix.create(rs, Element.U8_4(rs));
		scriptColor.setGreyscale();


		ScriptGroup.Builder builder = new ScriptGroup.Builder(rs);

		builder.addKernel(scriptBlur.getKernelID());
		builder.addKernel(scriptColor.getKernelID());
		builder.addConnection(input.getType(), scriptBlur.getKernelID(), scriptColor.getKernelID());

		ScriptGroup group = builder.create();

		scriptBlur.setInput(input);
		group.setOutput(scriptColor.getKernelID(), output);
		group.execute();

		output.copyTo(outBitmap);

		view.setBackgroundDrawable(new BitmapDrawable(getResources(), outBitmap));

		rs.destroy();
		Toast.makeText(getActivity(), System.currentTimeMillis() - startMs + " ms", Toast.LENGTH_SHORT).show();
	}


	private Bitmap prepareBitmap(Bitmap inBitmap, int width, int height)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(90);

		Bitmap scaledBitmap;
		Bitmap rotatedBitmap;

		Logcat.e("w : " + width + " h : " + height);
		Logcat.e("mw: " + inBitmap.getWidth() + " mh: " + inBitmap.getHeight());

		if (inBitmap.getWidth() > inBitmap.getHeight())
		{
			rotatedBitmap = Bitmap.createScaledBitmap(inBitmap, height, width, true);
			scaledBitmap = Bitmap.createBitmap(rotatedBitmap, 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);
		} else
		{
			scaledBitmap = Bitmap.createScaledBitmap(inBitmap, width, height, true);
		}
		Logcat.e("rw: " + scaledBitmap.getWidth() + " rh: " + scaledBitmap.getHeight());

		return scaledBitmap;
	}


	private void renderView()
	{
//		mImageLoader.displayImage(mBackgroundPath, mBackgroundImageView, mDisplayImageOptions, mImageLoadingListener);
	}
}
