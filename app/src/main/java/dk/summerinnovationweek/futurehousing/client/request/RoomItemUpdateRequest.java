package dk.summerinnovationweek.futurehousing.client.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import dk.summerinnovationweek.futurehousing.client.parser.RoomParser;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;
import dk.summerinnovationweek.futurehousing.utility.Logcat;


public class RoomItemUpdateRequest extends Request
{
	private static final String REQUEST_METHOD = "POST";
	private static final String REQUEST_PATH = "/api/futurehousing/roomitem";

	private RoomItemEntity mRoomItemEntity;

	private double mLatitude = NULL_DOUBLE;
	private double mLongitude = NULL_DOUBLE;
	private String mAccountEmail;
	private String mAccountToken;


	public RoomItemUpdateRequest(RoomItemEntity roomItemEntity, String accountEmail, String accountToken)
	{
		mRoomItemEntity = roomItemEntity;
		mAccountEmail = accountEmail;
		mAccountToken = accountToken;
	}


	public RoomItemUpdateRequest(RoomItemEntity roomItemEntity, double latitude, double longitude, String accountEmail, String accountToken)
	{
		mRoomItemEntity = roomItemEntity;
		mLatitude = latitude;
		mLongitude = longitude;
		mAccountEmail = accountEmail;
		mAccountToken = accountToken;
	}


	@Override
	public String getRequestMethod()
	{
		return REQUEST_METHOD;
	}


	@Override
	public String getAddress()
	{
		StringBuilder builder = new StringBuilder();
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		// params
		params.add(new BasicNameValuePair("houseID", String.valueOf(mRoomItemEntity)));
		if (mLatitude != NULL_DOUBLE)
			params.add(new BasicNameValuePair("lat", String.valueOf(mLatitude)));
		if (mLongitude != NULL_DOUBLE)
			params.add(new BasicNameValuePair("long", String.valueOf(mLongitude)));
		String paramsString = URLEncodedUtils.format(params, CHARSET);

		// url
		builder.append(API_ENDPOINT);
		builder.append(REQUEST_PATH);
		if (paramsString != null && !paramsString.equals(""))
		{
			builder.append("?");
			builder.append(paramsString);
		}

		String address = builder.toString();
		Logcat.e("Address: " + address);
		return address;
	}


	@Override
	public Response<RoomEntity> parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return RoomParser.parse(stream);
	}


	@Override
	public byte[] getContent()
	{
		Gson gson = new GsonBuilder().create();
		String content = gson.toJson(mRoomItemEntity);

		try
		{
			return content.getBytes(CHARSET);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String getBasicAuthUsername()
	{
		return mAccountEmail;
	}


	@Override
	public String getBasicAuthToken()
	{
		return mAccountToken;
	}
}
