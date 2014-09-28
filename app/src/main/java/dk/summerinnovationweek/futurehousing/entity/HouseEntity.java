package dk.summerinnovationweek.futurehousing.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HouseEntity implements Serializable
{
	@SerializedName("id")
	private long mId;
	@SerializedName("name")
	private String mName;
	@SerializedName("floorPlanPicture")
	private String mFloorPlan;
	@SerializedName("floorPlanWidth")
	private int mFloorPlanWidth;
	@SerializedName("floorPlanHeight")
	private int mFloorPlanHeight;
	@SerializedName("rooms")
	private ArrayList<RoomEntity> mRoomList;

	
	public long getId()
	{
		return mId;
	}
	public void setId(long id)
	{
		this.mId = id;
	}
	public ArrayList<RoomEntity> getRoomList()
	{
		return mRoomList;
	}
	public void setRoomList(ArrayList<RoomEntity> roomList)
	{
		mRoomList = roomList;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public String getFloorPlan()
	{
		return mFloorPlan;
	}

	public void setFloorPlan(String floorPlan)
	{
		mFloorPlan = floorPlan;
	}


	public int getFloorPlanWidth()
	{
		return mFloorPlanWidth;
	}


	public void setFloorPlanWidth(int floorPlanWidth)
	{
		mFloorPlanWidth = floorPlanWidth;
	}


	public int getFloorPlanHeight()
	{
		return mFloorPlanHeight;
	}


	public void setFloorPlanHeight(int floorPlanHeight)
	{
		mFloorPlanHeight = floorPlanHeight;
	}
}
