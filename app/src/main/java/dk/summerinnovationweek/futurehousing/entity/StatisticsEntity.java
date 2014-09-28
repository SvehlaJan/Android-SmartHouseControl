package dk.summerinnovationweek.futurehousing.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import dk.summerinnovationweek.futurehousing.entity.statisticsItems.StatisticsItemConsumption;
import dk.summerinnovationweek.futurehousing.entity.statisticsItems.StatisticsItemPerson;


public class StatisticsEntity implements Serializable
{
	@SerializedName("backgroundPhoto")
	private String mBackgroundPhotoUrl;

	@SerializedName("consumptions")
	private ArrayList<StatisticsItemConsumption> mConsumptions;
	@SerializedName("people")
	private ArrayList<StatisticsItemPerson> mPeopleList;


	public String getBackgroundPhotoUrl()
	{
		return mBackgroundPhotoUrl;
	}


	public void setBackgroundPhotoUrl(String backgroundPhotoUrl)
	{
		mBackgroundPhotoUrl = backgroundPhotoUrl;
	}


	public ArrayList<StatisticsItemConsumption> getConsumptions()
	{
		return mConsumptions;
	}


	public void setConsumptions(ArrayList<StatisticsItemConsumption> consumptions)
	{
		mConsumptions = consumptions;
	}


	public ArrayList<StatisticsItemPerson> getPeopleList()
	{
		return mPeopleList;
	}


	public void setPeopleList(ArrayList<StatisticsItemPerson> peopleList)
	{
		mPeopleList = peopleList;
	}
}
