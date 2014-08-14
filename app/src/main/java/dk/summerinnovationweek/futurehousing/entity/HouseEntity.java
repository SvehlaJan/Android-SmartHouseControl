package dk.summerinnovationweek.futurehousing.entity;


import android.graphics.Bitmap;

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
	@SerializedName("rooms")
	private ArrayList<RoomEntity> mRoomList;


	// empty constructor
	public HouseEntity()
	{
	
	}

	public HouseEntity(long id, String name, String floorPlan, ArrayList<RoomEntity> roomList)
	{
		mId = id;
		mName = name;
		mFloorPlan = floorPlan;
		mRoomList = roomList;
	}

	// copy constructor
	public HouseEntity(HouseEntity origin)
	{
		mId = origin.mId;
		if (origin.getRoomList() != null)
			mRoomList = new ArrayList<RoomEntity>(origin.getRoomList());
	}
	
	
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
}
