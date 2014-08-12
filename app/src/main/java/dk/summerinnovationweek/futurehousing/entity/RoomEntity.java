package dk.summerinnovationweek.futurehousing.entity;

import java.io.Serializable;

public class RoomEntity implements Serializable
{
	private int mId;
	private String mName;
	private boolean mMeasuredIsLightOn;
	private boolean mInputIsLightOn;
	private int mMeasuredTemperature;
	private int mInputTemperature;
	

	public RoomEntity()
	{

	}

	public RoomEntity(int id, String name, boolean inputIsLightOn, int measuredTemperature)
	{
		mId = id;
		mName = name;
		mInputIsLightOn = inputIsLightOn;
		mMeasuredTemperature = measuredTemperature;
	}

	public RoomEntity(RoomEntity origin)
	{
		mId = origin.mId;
		mInputIsLightOn = origin.mInputIsLightOn;
		mMeasuredTemperature = origin.mMeasuredTemperature;
		mInputTemperature = origin.mInputTemperature;
	}

	public int getId()
	{
		return mId;
	}

	public void setId(int id)
	{
		mId = id;
	}

	public boolean isInputIsLightOn()
	{
		return mInputIsLightOn;
	}

	public void setInputIsLightOn(boolean isLightOn)
	{
		mInputIsLightOn = isLightOn;
	}

	public int getMeasuredTemperature()
	{
		return mMeasuredTemperature;
	}

	public void setMeasuredTemperature(int measuredTemperature)
	{
		mMeasuredTemperature = measuredTemperature;
	}

	public int getInputTemperature()
	{
		return mInputTemperature;
	}

	public void setInputTemperature(int inputTemperature)
	{
		mInputTemperature = inputTemperature;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public boolean isMeasuredIsLightOn()
	{
		return mMeasuredIsLightOn;
	}

	public void setMeasuredIsLightOn(boolean measuredIsLightOn)
	{
		mMeasuredIsLightOn = measuredIsLightOn;
	}
}
