package dk.summerinnovationweek.futurehousing.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SmartHouseEntity implements Serializable
{
	@SerializedName("house")
	private HouseEntity mHouseEntity;
	@SerializedName("users")
	private List<UserEntity> mUserEntities;

	public List<UserEntity> getUserEntities()
	{
		return mUserEntities;
	}

	public void setUserEntities(List<UserEntity> userEntities)
	{
		mUserEntities = userEntities;
	}

	public HouseEntity getHouseEntity()
	{
		return mHouseEntity;
	}

	public void setHouseEntity(HouseEntity houseEntity)
	{
		mHouseEntity = houseEntity;
	}
}
