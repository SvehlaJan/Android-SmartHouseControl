package dk.summerinnovationweek.futurehousing.client.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import dk.summerinnovationweek.futurehousing.client.parser.LoginParser;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.LoginRequestEntity;
import dk.summerinnovationweek.futurehousing.entity.LoginResponseEntity;
import dk.summerinnovationweek.futurehousing.utility.Logcat;


public class LoginRequest extends Request
{
	private static final String REQUEST_METHOD = "POST";
	private static final String REQUEST_PATH = "/api/auth/login";

	private LoginRequestEntity mLoginRequestEntity;


	public LoginRequest(LoginRequestEntity loginRequestEntity)
	{
		mLoginRequestEntity = loginRequestEntity;
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
	public Response<LoginResponseEntity> parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return LoginParser.parse(stream);
	}


	@Override
	public byte[] getContent()
	{
		Gson gson = new GsonBuilder().create();
		String content = gson.toJson(mLoginRequestEntity);
		
		try
		{
			return content.getBytes(CHARSET);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
