package dk.summerinnovationweek.futurehousing.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;

// http://stackoverflow.com/questions/5800433/polymorphism-with-gson

public class RoomItemEntityAdapter implements JsonSerializer<RoomItemEntity>, JsonDeserializer<RoomItemEntity>
{
	private static final String CLASSNAME = "classname";
	private static final String INSTANCE = "instance";

	@Override
	public JsonElement serialize(RoomItemEntity src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		String className = ((Object)src).getClass().getCanonicalName();
		jsonObject.addProperty(CLASSNAME, className);
		JsonElement element = context.serialize(src);
		jsonObject.add(INSTANCE, element);

		return jsonObject;
	}

	@Override
	public RoomItemEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
		String className = primitive.getAsString();

		Class<?> c = null;
		try
		{
			c = Class.forName(className);
		} catch (ClassNotFoundException e)
		{
			throw new JsonParseException(e.getMessage());
		}

		return context.deserialize(jsonObject.get(INSTANCE), c);
	}
}
