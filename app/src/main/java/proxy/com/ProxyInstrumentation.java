package proxy.com;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import proxy.com.util.Utils;


/**
 * Created by CJJ on 2017/8/28.
 *
 * @author CJJ
 */

public class ProxyInstrumentation extends Instrumentation implements Handler.Callback {

    private static final String TAG = "ProxyInstrumentation";

    Instrumentation baseInstrumentation;

    public static final String KEY_CLASSNAME = "ClassNameKey";

    public ProxyInstrumentation(Instrumentation baseInstrumentation) {
        this.baseInstrumentation = baseInstrumentation;
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    //在发往服务端校验之前执行该方法，先保存真正要启动的Activty 名字等需要的信息，
    // 总之进行拦截，最后通过反射调用Instrumentation的同名方法
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        ActivityResult activityResult = null;
        ComponentName componentName = intent.getComponent();
        if (componentName != null) {
            Log.i(TAG, "execStartActivity: " + componentName.getClassName());
            intent.setClassName(who, who.getPackageName() + ".Activity.Standard");
            intent.putExtra(KEY_CLASSNAME, componentName.getClassName());
        }


        //baseInstrumentation.execStartActivity(who, contextThread, token, target, intent, requestCode, options);
        //反射调用
        Class[] parameterType = new Class[]{Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class};
        try {
            activityResult = (ActivityResult) Utils.invoke(Instrumentation.class, baseInstrumentation, "execStartActivity", parameterType,
                    who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityResult;
    }


    //服务端验证完成后回到这个方法进行Activity的创建
    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        String targetClassName = intent.getStringExtra(KEY_CLASSNAME);
        if (targetClassName != null)
            return baseInstrumentation.newActivity(cl, targetClassName, intent);
        return super.newActivity(cl, className, intent);
    }
}

