package proxy.com;

import android.app.Application;

import proxy.va.VAManager;


/**
 * Created by CJJ on 2017/8/28.
 *
 * @author CJJ
 */

public class BaseApp extends Application {

    private static final String TAG = "BaseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        VAManager.getInstance().hook(this);
    }


}
