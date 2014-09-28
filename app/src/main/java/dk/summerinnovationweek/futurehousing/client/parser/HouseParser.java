package dk.summerinnovationweek.futurehousing.client.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dk.summerinnovationweek.futurehousing.adapter.RoomItemEntityAdapter;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class HouseParser extends Parser
{
	public static Response<HouseEntity> parse(InputStream stream) throws IOException
	{
		Response<HouseEntity> response = null;

		Reader reader = new InputStreamReader(stream);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoomItemEntity.class, new RoomItemEntityAdapter());
		Gson gson = gsonBuilder.create();

		HouseEntity house = gson.fromJson(reader, HouseEntity.class);

		response = new Response<HouseEntity>();
		response.setResponseObject(house);

		return response;
	}
}
