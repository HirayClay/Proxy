package proxy.va;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by CJJ on 2017/8/29.
 *
 * @author CJJ
 */

public class LoadedApk {
    String location;
    PackageInfo packageInfo;
    AssetManager assetManager;
    Resources resources;
    //宿主的上下文
    Context hostContext;
    //插件的上下文
    Context pluginContext;
}
