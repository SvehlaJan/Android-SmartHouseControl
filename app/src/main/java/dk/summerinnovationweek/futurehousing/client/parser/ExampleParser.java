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


public class ExampleParser extends Parser
{
	public static Response<HouseEntity> parse(InputStream stream) throws IOException, JsonParseException
	{
		Response<HouseEntity> response = null;
		
		
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
				
				response = new Response<HouseEntity>();
				response.setError(true);
				response.setErrorType(type);
				response.setErrorMessage(message);
			}
			
			// response
			else if(parser.getCurrentName().equals("product"))
			{
				long id = -1l;
				String name = null;

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
					else
					{
						// unknown parameter
						handleUnknownParameter(parser);
					}
				}

				HouseEntity product = new HouseEntity();
				product.setId(id);

				response = new Response<HouseEntity>();
				response.setResponseObject(product);
			}
		}
		

		// close parser
		if(parser!=null) parser.close();
		return response;
	}
}
