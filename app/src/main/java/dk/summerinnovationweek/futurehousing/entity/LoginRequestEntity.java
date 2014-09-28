package dk.summerinnovationweek.futurehousing.entity;


import com.google.gson.annotations.SerializedName;


public class LoginRequestEntity
{
	@SerializedName("userId")
    private String mUserId;
	@SerializedName("password")
	private String mPassword;


	public LoginRequestEntity(String userId, String password)
	{
		mUserId = userId;
		mPassword = password;
	}


	public LoginRequestEntity()
	{

	}


	public String getUserId()
	{
		return mUserId;
	}


	public void setUserId(String userId)
	{
		mUserId = userId;
	}


	public String getPassword()
	{
		return mPassword;
	}


	public void setPassword(String password)
	{
		mPassword = password;
	}
}
