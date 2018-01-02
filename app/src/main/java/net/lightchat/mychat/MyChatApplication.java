package net.lightchat.mychat;

import android.app.Application;

/**
 * MyChatApplication
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2017-12-28 14:31:02
 */
public class MyChatApplication extends Application {

    private static final String TAG = MyChatApplication.class.getName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

//    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
//    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
//    // PNG is lossless so quality is ignored but must be provided
//    private static int DISK_IMAGECACHE_QUALITY = 100;

    private static final String VALUE = "Harvey";

    private String value;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化全局变量
        setValue(VALUE);

        // 异常处理
        DefaultUncaughtExceptionHandler defaultUncaughtExceptionHandler = DefaultUncaughtExceptionHandler.getInstance();
        defaultUncaughtExceptionHandler.init(getApplicationContext());
    }

//    /**
//     * Intialize the request manager and the image cache
//     */
//    private void init() {
//        RequestManager.init(this);
//        createImageCache();
//    }
//
//    /**
//     * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.
//     */
//    private void createImageCache(){
//        ImageCacheManager.getInstance().init(this,
//                this.getPackageCodePath()
//                , DISK_IMAGECACHE_SIZE
//                , DISK_IMAGECACHE_COMPRESS_FORMAT
//                , DISK_IMAGECACHE_QUALITY
//                , CacheType.MEMORY);
//    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
