package dk.summerinnovationweek.futurehousing.utility;

import android.content.Context;


public class Utility
{
	public static final String TAG = "utility";

	public static final long DEFAULT_LONG = -1L;
	public static final int DEFAULT_INT = -1;
	public static final double DEFAULT_DOUBLE = Double.MIN_VALUE;


	public static int dpToPx(int dp, Context context)
	{
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

}
