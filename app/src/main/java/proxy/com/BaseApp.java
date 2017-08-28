package proxy.com;

import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            Field at = activityThreadClass.getField("sCurrentActivityThread");
            at.setAccessible(true);
            activityThread = at.get(null);
            Class<Instrumentation>  instrumentationClass = Instrumentation.class;
            Class<?>[] parameterType = new Class<?>[]{};
            Method method = instrumentationClass.getDeclaredMethod("getInstrumentation", parameterType);
            instrumentation = (Instrumentation) method.invoke(activityThread, null);
            //双开空间的辅助器会导致失败，这种情况下插件会失效
            if (instrumentation.getClass().getName().contains("lbe")) {
                System.exit(0);
            }

            ProxyInstrumentation proxyInstrumentation = new ProxyInstrumentation(instrumentation);
            Field mInstrumentation = activityThreadClass.getField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Log.i(TAG, "hook: "+activityThread);
            mInstrumentation.set(activityThread,proxyInstrumentation);
            //以上代码均仿造VA
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
