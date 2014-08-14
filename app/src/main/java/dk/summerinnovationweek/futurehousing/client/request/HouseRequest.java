package dk.summerinnovationweek.futurehousing.client.request;

import com.fasterxml.jackson.core.JsonParseException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import dk.summerinnovationweek.futurehousing.client.parser.HouseParser;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.utility.Logcat;


public class HouseRequest extends Request
{
	private static final String REQUEST_METHOD = "POST";
	private static final String REQUEST_PATH = "getHouse";
	
	private int mHouseId;
	

	public HouseRequest(int houseId)
	{
		mHouseId = houseId;
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
		params.add(new BasicNameValuePair("houseID", Integer.toString(mHouseId)));
		String paramsString = URLEncodedUtils.format(params, CHARSET);

		// url
		builder.append(API_ENDPOINT);
		builder.append(REQUEST_PATH);
		if(paramsString!=null && !paramsString.equals(""))
		{
			builder.append("?");
			builder.append(paramsString);
		}

		String address = builder.toString();
		Logcat.e("Address: " + address);
		return address;
	}


	@Override
	public Response<HouseEntity> parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return HouseParser.parse(stream);
	}


	@Override
	public byte[] getContent()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("content");
		
		try
		{
			return builder.toString().getBytes(CHARSET);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String getBasicAuthUsername()
	{
		return "myusername";
	}


	@Override
	public String getBasicAuthPassword()
	{
		return "mypassword";
	}
}
