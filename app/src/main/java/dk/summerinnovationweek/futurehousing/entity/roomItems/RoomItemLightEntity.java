package dk.summerinnovationweek.futurehousing.entity.roomItems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class RoomItemLightEntity extends RoomItemEntity implements Serializable {

	@SerializedName("light_on")
	private boolean mMeasuredLight;
	private transient boolean mUserLight;
	@SerializedName("light_perc")
	private int mMeasuredLightPerc;
	private transient int mUserLightPerc;


	public RoomItemLightEntity(long id, String type, String name)
	{
		super(id, type, name);
	}


	public boolean isMeasuredLight()
	{
		return mMeasuredLight;
	}


	public void setMeasuredLight(boolean measuredLight)
	{
		mMeasuredLight = measuredLight;
	}


	public boolean isUserLight()
	{
		return mUserLight;
	}


	public void setUserLight(boolean userLight)
	{
		mUserLight = userLight;
	}


	public int getMeasuredLightPerc()
	{
		return mMeasuredLightPerc;
	}


	public void setMeasuredLightPerc(int measuredLightPerc)
	{
		mMeasuredLightPerc = measuredLightPerc;
	}


	public int getUserLightPerc()
	{
		return mUserLightPerc;
	}


	public void setUserLightPerc(int userLightPerc)
	{
		mUserLightPerc = userLightPerc;
	}
}
