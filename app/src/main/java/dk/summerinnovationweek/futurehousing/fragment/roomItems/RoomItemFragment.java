package dk.summerinnovationweek.futurehousing.fragment.roomItems;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import dk.summerinnovationweek.futurehousing.FutureHousingApplication;
import dk.summerinnovationweek.futurehousing.R;
import dk.summerinnovationweek.futurehousing.entity.RoomItemEntity;


public abstract class RoomItemFragment<T extends RoomItemEntity> extends Fragment
{
	public abstract View getView(T roomItem);

	public abstract View getActionsView(T roomItem);

	public static void setHeatingState(int userTemperature, int measuredTemperature, TextView view)
	{
		if (userTemperature > measuredTemperature)
		{
			view.setVisibility(View.VISIBLE);
			view.setTextColor(FutureHousingApplication.getContext().getResources().getColor(R.color.global_text_light_red));
			view.setText(FutureHousingApplication.getContext().getResources().getString(R.string.room_item_heating_state_heating));
		}
		else if (userTemperature < measuredTemperature)
		{
			view.setVisibility(View.VISIBLE);
			view.setTextColor(FutureHousingApplication.getContext().getResources().getColor(R.color.global_text_light_blue));
			view.setText(FutureHousingApplication.getContext().getResources().getString(R.string.room_item_heating_state_cooling));
		}
		else
		{
			view.setVisibility(View.INVISIBLE);
		}
	}
}
