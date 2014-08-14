package dk.summerinnovationweek.futurehousing.client.parser;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dk.summerinnovationweek.futurehousing.client.response.Response;
import dk.summerinnovationweek.futurehousing.entity.HouseEntity;
import dk.summerinnovationweek.futurehousing.entity.RoomEntity;
import dk.summerinnovationweek.futurehousing.entity.SmartHouseEntity;


public class SmartHouseParser extends Parser
{
	public static Response<SmartHouseEntity> parse(InputStream stream) throws IOException, JsonParseException
	{
		Response<SmartHouseEntity> response = null;
		
		
		// init parser
		JsonFactory factory = new JsonFactory();
		JsonParser parser = null;
		parser = factory.createJsonParser(stream);

		
		// parse JSON
		if(parser.nextToken() == JsonToken.START_OBJECT)
		while(parser.nextToken() != JsonToken.END_OBJECT)
		{
			// error
			if(parser.getCurrentName().equals("error"))
			{
				String type = null;
				String message = null;
				
				if(parser.nextToken() == JsonToken.START_OBJECT)
				while(parser.nextToken() != JsonToken.END_OBJECT)
				{
					if(parser.getCurrentName().equals("type"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_STRING) type = parser.getText();
					}
					else if(parser.getCurrentName().equals("message"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_STRING) message = parser.getText();
					}
				}
				
				response = new Response<SmartHouseEntity>();
				response.setError(true);
				response.setErrorType(type);
				response.setErrorMessage(message);
			}
			
			// response
			else if(parser.getCurrentName().equals("smarthouse"))
			{
				SmartHouseEntity smartHouseEntity = new SmartHouseEntity();

				if(parser.nextToken() == JsonToken.START_OBJECT)
				while(parser.nextToken() != JsonToken.END_OBJECT)
				{
					if(parser.getCurrentName().equals("house"))
					{
						long id = -1l;
						String name = null;
						String floorPlan = null;
						ArrayList<RoomEntity> rooms = null;

						if(parser.nextToken() == JsonToken.START_OBJECT)
							while(parser.nextToken() != JsonToken.END_OBJECT)
							{
								if(parser.getCurrentName().equals("id"))
								{
									if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) id = parser.getLongValue();
								}
								else if(parser.getCurrentName().equals("name"))
								{
									if(parser.getCurrentToken() == JsonToken.VALUE_STRING) name = parser.getText();
								}
								else if(parser.getCurrentName().equals("floorPlanPicture"))
								{
									if(parser.getCurrentToken() == JsonToken.VALUE_STRING) floorPlan = parser.getText();
								}
								else if(parser.getCurrentName().equals("rooms"))
								{
									rooms = new ArrayList<RoomEntity>();

									if (parser.nextToken() == JsonToken.START_ARRAY)
										while (parser.nextToken() != JsonToken.END_ARRAY)
										{

											long roomId = 0;
											String roomName = null;
											int roomTemperature = 0;
											boolean roomLight = false;

											if (parser.nextToken() == JsonToken.START_OBJECT)
												while (parser.nextToken() != JsonToken.END_OBJECT)
												{
													if (parser.getCurrentName().equals("id"))
													{
														if (parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT)
															roomId = parser.getLongValue();
													} else if (parser.getCurrentName().equals("name"))
													{
														if (parser.getCurrentToken() == JsonToken.VALUE_STRING)
															roomName = parser.getText();
													} else if (parser.getCurrentName().equals("temperature"))
													{
														if (parser.getCurrentToken() == JsonToken.VALUE_STRING)
															roomTemperature = parser.getIntValue();
													} else if (parser.getCurrentName().equals("light"))
													{
														if (parser.getCurrentToken() == JsonToken.VALUE_STRING)
															roomLight = parser.getBooleanValue();
													} else
													{
														handleUnknownParameter(parser);
													}
												}

											RoomEntity room = new RoomEntity(roomId, roomName, roomLight, roomTemperature);

											rooms.add(room);
										}
								}
								else
								{
									handleUnknownParameter(parser);
								}
							}

						HouseEntity houseEntity = new HouseEntity(id, name, floorPlan, rooms);
					}

				}

				response = new Response<SmartHouseEntity>();
				response.setResponseObject(smartHouseEntity);
			}
		}
		

		// close parser
		if(parser!=null) parser.close();
		return response;
	}
}