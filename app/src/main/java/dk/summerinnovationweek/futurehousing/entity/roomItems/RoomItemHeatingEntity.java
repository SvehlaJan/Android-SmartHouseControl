package dk.summerinnovationweek.futurehousing.entity.roomItems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class RoomItemHeatingEntity extends RoomItemEntity implements Serializable {

	@SerializedName("temperature")
	private int mMeasuredTemperature;
	private transient int mUserTemperature;


	public RoomItemHeatingEntity(long id, String type, String name)
	{
		super(id, type, name);
	}


	public int getUserTemperature()
	{
		return mUserTemperature;
	}


	public void setUserTemperature(int userTemperature)
	{
		mUserTemperature = userTemperature;
	}


	public int getMeasuredTemperature()
	{
		return mMeasuredTemperature;
	}


	public void setMeasuredTemperature(int measuredTemperature)
	{
		mMeasuredTemperature = measuredTemperature;
	}
}
