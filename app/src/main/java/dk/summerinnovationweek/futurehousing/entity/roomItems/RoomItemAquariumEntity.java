package dk.summerinnovationweek.futurehousing.entity.roomItems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class RoomItemAquariumEntity extends RoomItemEntity implements Serializable {

	@SerializedName("top_light")
	private boolean mTopLight;
	@SerializedName("side_light")
	private boolean mSideLight;
	@SerializedName("temperature")
	private int mMeasuredTemperature;
	private transient int mUserTemperature;


	public RoomItemAquariumEntity(long id, String type, String name)
	{
		super(id, type, name);
	}


	public boolean isTopLight()
	{
		return mTopLight;
	}


	public void setTopLight(boolean topLight)
	{
		mTopLight = topLight;
	}


	public boolean isSideLight()
	{
		return mSideLight;
	}


	public void setSideLight(boolean sideLight)
	{
		mSideLight = sideLight;
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
