package dk.summerinnovationweek.futurehousing.client.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.StatisticsEntity;


public class StatisticsParser extends Parser
{
	public static Response<StatisticsEntity> parse(InputStream stream) throws IOException
	{
		Response<StatisticsEntity> response = null;

		Reader reader = new InputStreamReader(stream);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		StatisticsEntity statistics = gson.fromJson(reader, StatisticsEntity.class);

		response = new Response<StatisticsEntity>();
		response.setResponseObject(statistics);

		return response;
	}
}
