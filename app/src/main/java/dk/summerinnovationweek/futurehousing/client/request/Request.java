package dk.summerinnovationweek.futurehousing.client.request;

import android.os.Bundle;

import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;

import dk.summerinnovationweek.futurehousing.FutureHousingConfig;
import dk.summerinnovationweek.futurehousing.client.response.Response;


public abstract class Request
{
	public static final String API_ENDPOINT = FutureHousingConfig.DEV_API ? FutureHousingConfig.API_ENDPOINT_DEVELOPMENT : FutureHousingConfig.API_ENDPOINT_PRODUCTION;
	public static final String CHARSET = "UTF-8";
	public static final String BOUNDARY = "0xKhTmLbOuNdArY";

	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1l;
	public static final double NULL_DOUBLE = -1.0;
	public static final String NULL_STRING = null;

	private Bundle mMetaData = null;

	public abstract String getRequestMethod();
	public abstract String getAddress();
	public abstract Response<?> parseResponse(InputStream stream) throws IOException, JsonParseException;


	public byte[] getContent()
	{
		return null;
	}


	public String getBasicAuthUsername()
	{
		return null;
	}


	public String getBasicAuthToken()
	{
		return null;
	}


	public boolean isMultipart()
	{
		return false;
	}


	public Bundle getMetaData()
	{
		return mMetaData;
	}


	public void setMetaData(Bundle metaData)
	{
		mMetaData = metaData;
	}
}
