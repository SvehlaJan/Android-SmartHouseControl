package dk.summerinnovationweek.futurehousing.client.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.LoginResponseEntity;


public class LoginParser extends Parser
{
	public static Response<LoginResponseEntity> parse(InputStream stream) throws IOException
	{
		Response<LoginResponseEntity> response = null;

		Reader reader = new InputStreamReader(stream);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		LoginResponseEntity loginResponse = gson.fromJson(reader, LoginResponseEntity.class);

		response = new Response<LoginResponseEntity>();
		response.setResponseObject(loginResponse);

		return response;
	}
}
