package dk.summerinnovationweek.futurehousing.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserEntity implements Serializable
{
	@SerializedName("id")
	private long mId;
	@SerializedName("houseId")
	private long houseId;
	@SerializedName("username")
	private String username;
	@SerializedName("password")
	private String password;
	@SerializedName("isAdmin")
	private boolean isAdmin;


	
	public long getId()
	{
		return mId;
	}
	public void setId(long id)
	{
		this.mId = id;
	}

	public long getHouseId()
	{
		return houseId;
	}

	public void setHouseId(long houseId)
	{
		this.houseId = houseId;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
}
