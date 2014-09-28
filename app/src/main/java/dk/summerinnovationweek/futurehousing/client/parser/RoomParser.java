package dk.summerinnovationweek.futurehousing.client.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dk.summerinnovationweek.futurehousing.adapter.RoomItemEntityAdapter;
import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public class RoomParser extends Parser
{
	public static Response<RoomEntity> parse(InputStream stream) throws IOException
	{
		Response<RoomEntity> response = null;

		Reader reader = new InputStreamReader(stream);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoomItemEntity.class, new RoomItemEntityAdapter());
		Gson gson = gsonBuilder.create();

		RoomEntity room = gson.fromJson(reader, RoomEntity.class);

		response = new Response<RoomEntity>();
		response.setResponseObject(room);

		return response;
	}
}
