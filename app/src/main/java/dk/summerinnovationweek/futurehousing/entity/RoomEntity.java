package dk.summerinnovationweek.futurehousing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class RoomEntity implements Serializable {
	@SerializedName("id")
    private long mId;
	@SerializedName("name")
    private String mName;
	@SerializedName("light")
    private boolean mMeasuredIsLightOn;
    private boolean mInputIsLightOn;
	@SerializedName("temperature")
    private int mMeasuredTemperature;
    private int mInputTemperature;


    public RoomEntity() {

    }

    public RoomEntity(long id, String name, boolean isMeasuredIsLightOn, int measuredTemperature) {
        mId = id;
        mName = name;
        mInputIsLightOn = isMeasuredIsLightOn;
        mMeasuredTemperature = measuredTemperature;
    }

    public RoomEntity(RoomEntity origin) {
        mId = origin.mId;
        mInputIsLightOn = origin.mInputIsLightOn;
        mMeasuredTemperature = origin.mMeasuredTemperature;
        mInputTemperature = origin.mInputTemperature;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public boolean isInputIsLightOn() {
        return mInputIsLightOn;
    }

    public void setInputIsLightOn(boolean isLightOn) {
        mInputIsLightOn = isLightOn;
    }

    public int getMeasuredTemperature() {
        return mMeasuredTemperature;
    }

    public void setMeasuredTemperature(int measuredTemperature) {
        mMeasuredTemperature = measuredTemperature;
    }

    public int getInputTemperature() {
        return mInputTemperature;
    }

    public void setInputTemperature(int inputTemperature) {
        mInputTemperature = inputTemperature;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isMeasuredIsLightOn() {
        return mMeasuredIsLightOn;
    }

    public void setMeasuredIsLightOn(boolean measuredIsLightOn) {
        mMeasuredIsLightOn = measuredIsLightOn;
    }


    public String ReadCPUinfo() {
        ProcessBuilder cmd;
        String result = "";

        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while (in.read(re) != -1) {
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
