package proxy.va;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageParser;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

/**
 * Created by CJJ on 2017/8/29.
 *
 * @author CJJ
 */

public class Plugin {
    String mApkPath;
    PackageParser.Package mPackage;
    PackageInfo mPackageInfo;
    AssetManager assetManager;
    Resources resources;
    //宿主的上下文
    Context hostContext;
    //插件的上下文
    Context pluginContext;

    public  Plugin (VAManager vaManager,Context context,File apk){
        this.mApkPath = apk.getAbsolutePath();
        this.hostContext = context;
        mPackage = ApkParser.parse(context,apk,apk.getAbsolutePath(),PackageParser.PARSE_MUST_BE_APK);
        mPackageInfo = new PackageInfo();
        mPackageInfo.packageName = mPackage.packageName;
        mPackageInfo.applicationInfo = mPackage.applicationInfo;
    }
}
