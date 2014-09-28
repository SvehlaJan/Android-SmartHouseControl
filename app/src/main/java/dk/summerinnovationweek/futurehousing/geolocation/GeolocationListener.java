package dk.summerinnovationweek.futurehousing.geolocation;

import android.location.Location;


public interface GeolocationListener
{
	public void onGeolocationRespond(Geolocation geolocation, Location location);
	public void onGeolocationFail(Geolocation geolocation);
}
