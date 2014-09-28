package dk.summerinnovationweek.futurehousing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class RoomItemEntity implements Serializable {
	@SerializedName("id")
    private long mId;
	@SerializedName("type")
	private String mType;
	@SerializedName("name")
    private String mName;

	public static final String TYPE_LIGHT = "light";
	public static final String TYPE_HEATING = "heating";
	public static final String TYPE_AQUARIUM = "aquarium";
	public static final String TYPE_OVEN = "oven";
	public static final String TYPE_FRIDGE = "fridge";


    public RoomItemEntity() {

    }

    public RoomItemEntity(long id, String type, String name) {
        mId = id;
	    mType = type;
        mName = name;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


	public String getType()
	{
		return mType;
	}


	public void setType(String type)
	{
		mType = type;
	}
}
