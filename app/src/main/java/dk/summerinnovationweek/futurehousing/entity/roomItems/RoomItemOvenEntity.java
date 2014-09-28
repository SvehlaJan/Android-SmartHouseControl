package dk.summerinnovationweek.futurehousing.entity.roomItems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class RoomItemOvenEntity extends RoomItemEntity implements Serializable {

	@SerializedName("oven_on")
	private boolean mMeasuredTurned;
	private transient boolean mUserTurned;
	@SerializedName("temperature")
	private int mMeasuredTemperature;
	private transient int mUserTemperature;


	public RoomItemOvenEntity(long id, String type, String name)
	{
		super(id, type, name);
	}


	public boolean isMeasuredTurned()
	{
		return mMeasuredTurned;
	}


	public void setMeasuredTurned(boolean measuredTurned)
	{
		mMeasuredTurned = measuredTurned;
	}


	public boolean isUserTurned()
	{
		return mUserTurned;
	}


	public void setUserTurned(boolean userTurned)
	{
		mUserTurned = userTurned;
	}


	public int getMeasuredTemperature()
	{
		return mMeasuredTemperature;
	}


	public void setMeasuredTemperature(int measuredTemperature)
	{
		mMeasuredTemperature = measuredTemperature;
	}


	public int getUserTemperature()
	{
		return mUserTemperature;
	}


	public void setUserTemperature(int userTemperature)
	{
		mUserTemperature = userTemperature;
	}
}
