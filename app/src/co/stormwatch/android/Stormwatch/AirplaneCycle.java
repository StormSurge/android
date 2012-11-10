package co.stormwatch.android.Stormwatch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 11/10/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirplaneCycle
{

    public static final String TURN_ON = "turnOn";

    public static void scheduleAirplaneToggle(Context context, boolean turnOn, long timeFromNow)
    {
        PendingIntent service = makePendingIntent(context, turnOn);
        scheduleAlarmForPendingIntent(context, service, timeFromNow, AlarmManager.RTC_WAKEUP);
    }

    private static PendingIntent makePendingIntent(Context context, boolean turnOn)
    {
        Intent intent = new Intent(context, AirplaneToggleService.class);
        intent.putExtra(TURN_ON, turnOn);
        return PendingIntent.getService(context, 0, intent, 0);
    }

    public static void toggleCycle(Context context, boolean cycle)
    {
        if(cycle)
        {
            turnOnAirplaneMode(context);
            scheduleAirplaneToggle(context, false, 60*1000);
        }
        else
            cancelToggle(context);
    }

    public static void cancelToggle(Context context)
    {
        PendingIntent pendingIntent = makePendingIntent(context, true);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        turnOffAirplaneMode(context);
    }

    private static void scheduleAlarmForPendingIntent(Context context, PendingIntent pendingIntent, long timeFromNow, int alarmType)
    {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.set(alarmType, System.currentTimeMillis() + timeFromNow, pendingIntent);
    }

    public static void turnOnAirplaneMode(Context context)
    {
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
        refreshAirplaneMode(context);
    }

    public static void turnOffAirplaneMode(Context context)
    {
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        refreshAirplaneMode(context);
    }

    public static void refreshAirplaneMode(Context context)
    {
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", true);
        context.sendBroadcast(intent);
    }
}
