package proxy.com;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


/**
 * Created by CJJ on 2017/8/28.
 *
 * @author CJJ
 */

public class ProxyInstrumentation extends Instrumentation implements Handler.Callback {


    Instrumentation baseInstrumentation;

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

        return activityResult;
    }
}

