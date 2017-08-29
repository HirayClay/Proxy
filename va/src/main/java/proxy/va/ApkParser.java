package proxy.va;

import android.content.Context;
import android.content.pm.PackageParser;
import android.os.Build;

import java.io.File;

/**
 * Created by CJJ on 2017/8/29.
 *
 * @author CJJ
 */
//在android 5.0 7.0 解析方式有一些不同，兼容版本，保持与版本的解析方式一致
public class ApkParser {
    public static PackageParser.Package parse(Context context, File apk, String path, int flags) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//
//        }else {
//
//        }
       return new PackageParser().parsePackage(apk,path,context.getResources().getDisplayMetrics(),flags);
    }


}
