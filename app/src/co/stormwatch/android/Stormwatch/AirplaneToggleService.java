package co.stormwatch.android.Stormwatch;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 11/10/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirplaneToggleService extends IntentService
{
    public AirplaneToggleService()
    {
        super(AirplaneToggleService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        boolean turnOn = intent.getBooleanExtra(AirplaneCycle.TURN_ON, true);
        if(turnOn)
        {
            AirplaneCycle.turnOnAirplaneMode(this);
            AirplaneCycle.scheduleAirplaneToggle(this, false, 5000);
        }
        else
        {
            AirplaneCycle.turnOffAirplaneMode(this);
            AirplaneCycle.scheduleAirplaneToggle(this, true, 60 * 1000);
        }
    }
}
