package co.stormwatch.android.Stormwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Home extends BaseActivity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showPower(View v)
    {
        PowerSettings.callMe(this);
    }
}
