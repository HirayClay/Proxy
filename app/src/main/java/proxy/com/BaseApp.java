package proxy.com;

import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;
//import android.app.ActivityThread;

import proxy.com.util.ReflectUtil;

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
        hook();
    }

    private void hook() {

        //直接获取ActivityThread的静态变量
        //private static volatile ActivityThread sCurrentActivityThread;
        Object activityThread = null;

        Instrumentation instrumentation = null;
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            activityThread = ReflectUtil.getActivityThread(this);
            instrumentation = ReflectUtil.getInstrumentation(this);
            //双开空间的辅助器会导致失败，这种情况下插件会失效
            if (instrumentation.getClass().getName().contains("lbe")) {
                System.exit(0);
            }

            ProxyInstrumentation proxyInstrumentation = new ProxyInstrumentation(instrumentation);
            ReflectUtil.setField(activityThreadClass,activityThread,"mInstrumentation",proxyInstrumentation);
            Log.i(TAG, "hook: instrumentation done");
            //以上代码均仿造VA
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
