package dk.summerinnovationweek.futurehousing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemHeatingEntity;
import dk.summerinnovationweek.futurehousing.entity.roomItems.RoomItemLightEntity;


public class RoomEntity implements Serializable
{
	@SerializedName("id")
	private int mId;
	@SerializedName("name")
	private String mName;

	@SerializedName("backgroundPhoto")
	private String mBackgroundPhotoUrl;

	@SerializedName("floorPlanMarginTop")
	private int mFloorPlanMarginTop;
	@SerializedName("floorPlanMarginLeft")
	private int mFloorPlanMarginLeft;
	@SerializedName("floorPlanMarginRight")
	private int mFloorPlanMarginRight;
	@SerializedName("floorPlanMarginBottom")
	private int mFloorPlanMarginBottom;
	@SerializedName("roomItems")
	private ArrayList<RoomItemEntity> mRoomItemEntities;

	private transient RoomItemHeatingEntity mItemHeatingEntity;
	private transient RoomItemLightEntity mItemLightEntity;


	public RoomEntity(int id, String name)
	{
		mId = id;
		mName = name;
	}


	public int getId()
	{
		return mId;
	}


	public void setId(int id)
	{
		mId = id;
	}


	public String getName()
	{
		return mName;
	}


	public void setName(String name)
	{
		mName = name;
	}


	public ArrayList<RoomItemEntity> getRoomItemEntities()
	{
		return mRoomItemEntities;
	}


	public void setRoomItemEntities(ArrayList<RoomItemEntity> roomItemEntities)
	{
		mRoomItemEntities = roomItemEntities;
	}


	public RoomItemHeatingEntity getItemHeatingEntity()
	{
		return mItemHeatingEntity;
	}


	public void setItemHeatingEntity(RoomItemHeatingEntity itemHeatingEntity)
	{
		mItemHeatingEntity = itemHeatingEntity;
	}


	public RoomItemLightEntity getItemLightEntity()
	{
		return mItemLightEntity;
	}


	public void setItemLightEntity(RoomItemLightEntity itemLightEntity)
	{
		mItemLightEntity = itemLightEntity;
	}


	public int getFloorPlanMarginLeft()
	{
		return mFloorPlanMarginLeft;
	}


	public void setFloorPlanMarginLeft(int floorPlanMarginLeft)
	{
		mFloorPlanMarginLeft = floorPlanMarginLeft;
	}


	public int getFloorPlanMarginTop()
	{
		return mFloorPlanMarginTop;
	}


	public void setFloorPlanMarginTop(int floorPlanMarginTop)
	{
		mFloorPlanMarginTop = floorPlanMarginTop;
	}


	public String getBackgroundPhotoUrl()
	{
		return mBackgroundPhotoUrl;
	}


	public void setBackgroundPhotoUrl(String backgroundPhotoUrl)
	{
		mBackgroundPhotoUrl = backgroundPhotoUrl;
	}


	public int getFloorPlanMarginBottom()
	{
		return mFloorPlanMarginBottom;
	}


	public void setFloorPlanMarginBottom(int floorPlanMarginBottom)
	{
		mFloorPlanMarginBottom = floorPlanMarginBottom;
	}


	public int getFloorPlanMarginRight()
	{
		return mFloorPlanMarginRight;
	}


	public void setFloorPlanMarginRight(int floorPlanMarginRight)
	{
		mFloorPlanMarginRight = floorPlanMarginRight;
	}
}
