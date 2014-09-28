package dk.summerinnovationweek.futurehousing.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import dk.summerinnovationweek.futurehousing.utility.Logcat;


public class GcmIntentService extends IntentService
{
	public GcmIntentService()
	{
		super("GcmIntentService");
	}


	@Override
	protected void onHandleIntent(Intent intent)
	{
		Logcat.d("GcmIntentService.onHandleIntent(): received message " + intent.getExtras());

		// get message type
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		// handle messages
		Bundle extras = intent.getExtras();
		if(!extras.isEmpty())
		{
			if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
			{
				Logcat.d("GcmIntentService.onHandleIntent(): send error " + extras.toString());
			}
			else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
			{
				Logcat.d("GcmIntentService.onHandleIntent(): deleted messages on server " + extras.toString());
			}
			else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
			{
				Logcat.d("GcmIntentService.onHandleIntent(): received " + extras.toString());

				// get type
				String type = intent.getStringExtra("type");
				if(type!=null)
				{
					if(type.equals("message"))
					{
						try
						{
							// decode
							String text = intent.getStringExtra("text");
							if(text!=null) text = URLDecoder.decode(text, "UTF-8");

							// TODO
						}
						catch(UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}

		// release the wake lock provided by the WakefulBroadcastReceiver
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
}
