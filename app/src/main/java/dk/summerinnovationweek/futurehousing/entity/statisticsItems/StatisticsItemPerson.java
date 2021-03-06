package dk.summerinnovationweek.futurehousing.entity.statisticsItems;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class StatisticsItemPerson implements Serializable
{
	@SerializedName("name")
	private String mName;
	@SerializedName("age")
	private String mAge;
	@SerializedName("details")
	private String mDetails;

	@SerializedName("graphTitle")
	private String mGraphTitle;
	@SerializedName("domainLabel")
	private String mDomainLabel;
	@SerializedName("rangeLabel")
	private String mRangeLabel;

	@SerializedName("XVals")
	private ArrayList<Float> XVals;
	@SerializedName("YVals")
	private ArrayList<Integer> YVals;



	public String getName()
	{
		return mName;
	}


	public void setName(String name)
	{
		mName = name;
	}


	public String getAge()
	{
		return mAge;
	}


	public void setAge(String age)
	{
		mAge = age;
	}


	public String getDetails()
	{
		return mDetails;
	}


	public void setDetails(String details)
	{
		mDetails = details;
	}

	public String getGraphTitle()
	{
		return mGraphTitle;
	}


	public void setGraphTitle(String graphTitle)
	{
		mGraphTitle = graphTitle;
	}


	public String getDomainLabel()
	{
		return mDomainLabel;
	}


	public void setDomainLabel(String domainLabel)
	{
		mDomainLabel = domainLabel;
	}


	public String getRangeLabel()
	{
		return mRangeLabel;
	}


	public void setRangeLabel(String rangeLabel)
	{
		mRangeLabel = rangeLabel;
	}


	public ArrayList<Float> getYVals()
	{
		return XVals;
	}


	public void setXVals(ArrayList<Float> XVals)
	{
		this.XVals = XVals;
	}


	public ArrayList<Integer> getXVals()
	{
		return YVals;
	}


	public void setYVals(ArrayList<Integer> YVals)
	{
		this.YVals = YVals;
	}
}
