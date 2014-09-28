package dk.summerinnovationweek.futurehousing.entity;


public class LoginResponseEntity
{
	private String mAuthToken;
	private String mEmail;
	private String mActualVersion;
	private int[] mHouseIds;
	private int[] mUserIds;


	public LoginResponseEntity()
	{

	}


	public String getAuthToken()
	{
		return mAuthToken;
	}


	public void setAuthToken(String authToken)
	{
		mAuthToken = authToken;
	}


	public String getEmail()
	{
		return mEmail;
	}


	public void setEmail(String email)
	{
		mEmail = email;
	}


	public int[] getHouseIds()
	{
		return mHouseIds;
	}


	public void setHouseIds(int[] houseIds)
	{
		mHouseIds = houseIds;
	}


	public int[] getUserIds()
	{
		return mUserIds;
	}


	public void setUserIds(int[] userIds)
	{
		mUserIds = userIds;
	}


	public String getActualVersion()
	{
		return mActualVersion;
	}


	public void setActualVersion(String actualVersion)
	{
		mActualVersion = actualVersion;
	}
}
