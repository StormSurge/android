package co.stormwatch.android.Stormwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 11/10/12
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PowerSettings extends BaseActivity
{

    public static final int BRIGHTNESS = 100;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_settings);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.cycleAirplane);
        checkBox
                .setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        AirplaneCycle.toggleCycle(PowerSettings.this, checkBox.isChecked());
                    }
                });
    }

    public void showBT(View view)
    {
        startSettings(Settings.ACTION_BLUETOOTH_SETTINGS);
    }

    public void showWifi(View view)
    {
        startSettings(Settings.ACTION_WIFI_SETTINGS);
    }

    public void showWireless(View view)
    {
//        startSettings(Settings.ACTION_WIRELESS_SETTINGS);
        startSettings(Settings.ACTION_DATA_ROAMING_SETTINGS);
    }

    public void setMinimalScreenSettings(View v) throws Settings.SettingNotFoundException
    {
//        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

        int brightnessMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = ((float)BRIGHTNESS/255f);
        getWindow().setAttributes(lp);

        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    }

    private void startSettings(String action)
    {
        Intent intentBluetooth = new Intent();
        intentBluetooth.setAction(action);
        startActivity(intentBluetooth);
    }

    public static void callMe(Context c)
    {
        c.startActivity(new Intent(c, PowerSettings.class));
    }
}