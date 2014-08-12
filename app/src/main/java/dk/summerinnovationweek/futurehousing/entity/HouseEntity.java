package dk.summerinnovationweek.futurehousing.entity;


import java.io.Serializable;
import java.util.ArrayList;

public class HouseEntity implements Serializable
{
	private long mId;
	private ArrayList<RoomEntity> mRoomList;


	// empty constructor
	public HouseEntity()
	{
	
	}

	public HouseEntity(long id, ArrayList<RoomEntity> roomList)
	{
		mId = id;
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
}
