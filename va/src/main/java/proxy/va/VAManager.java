package proxy.va;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import proxy.util.ReflectUtil;

/**
 * Created by CJJ on 2017/8/29.
 *
 * @author CJJ
 */

public class VAManager {
    private static final String TAG = "VAManager";
    private static VAManager vaManager;
    private Context hostContext;

    public static VAManager getInstance() {
        if (vaManager == null) {
            synchronized (VAManager.class) {
                vaManager = new VAManager();
            }
        }
        return vaManager;
    }

    private VAManager() {

    }

    public void hook(Context context) {
        hostContext = context;

        //直接获取ActivityThread的静态变量
        //private static volatile ActivityThread sCurrentActivityThread;
        Object activityThread = null;
        Instrumentation instrumentation = null;
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            activityThread = ReflectUtil.getActivityThread(context);
            instrumentation = ReflectUtil.getInstrumentation(context);
            //双开空间的辅助器会导致失败，这种情况下插件会失效
            if (instrumentation.getClass().getName().contains("lbe")) {
                System.exit(0);
            }

            ProxyInstrumentation proxyInstrumentation = new ProxyInstrumentation(instrumentation);
            ReflectUtil.setField(activityThreadClass, activityThread, "mInstrumentation", proxyInstrumentation);
            Log.i(TAG, "hook: instrumentation done");
            //以上代码均仿造VA
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
