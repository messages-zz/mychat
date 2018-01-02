package net.lightchat.mychat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * DefaultUncaughtExceptionHandler
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2017-12-28 14:31:02
 */
public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = DefaultUncaughtExceptionHandler.class.getName();

    // 日期格式化
    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // 存储设备信息和异常信息
    private Map<String, String> tempInfos = new HashMap<>();

    private static class LazyHolder {
        private static final DefaultUncaughtExceptionHandler INSTANCE = new DefaultUncaughtExceptionHandler();
    }

    private DefaultUncaughtExceptionHandler(){

    }

    public static final DefaultUncaughtExceptionHandler getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置为程序默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // mDefaultHandler.uncaughtException(thread, throwable);
        handleException(throwable);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Log.e(TAG, "Error : ", e);
        }

        // 退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private void handleException(Throwable throwable) {

        Log.e(TAG, throwable.getMessage(), throwable);

        // TODO 显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "糟糕,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        // 收集应用信息
        collectApplicationInformation(mContext);

        // TODO 保存日志文件
    }

    /**
     * 收集环境信息
     *
     * @param ctx
     */
    public void collectApplicationInformation(Context ctx) {
        try {
            PackageManager packageManager = ctx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES
            );

            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                tempInfos.put("version_name", versionName);
                tempInfos.put("version_code", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "An error occurred", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                tempInfos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "An error occurred", e);
            }
        }
    }
}
