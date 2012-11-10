package co.stormwatch.android.Stormwatch;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 11/10/12
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends SherlockActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.text);
//        ((TextView)findViewById(R.id.text)).setText(R.string.app_name);

        //This is a workaround for http://b.android.com/15340 from http://stackoverflow.com/a/5852198/132047
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            bg.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            getSupportActionBar().setBackgroundDrawable(bg);

            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
            bgSplit.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Save")
                .setIcon(R.drawable.ic_compose)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Search")
                .setIcon(R.drawable.ic_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Refresh")
                .setIcon(R.drawable.ic_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
}
