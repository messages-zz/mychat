/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.lightchat.mychat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity (Activity AppCompatActivity)
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2017-12-28 14:31:02
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String SHARED_PREFERENCES_NAME = "shared_pref";

    private int[] navigationIcons = {
            R.drawable.ic_chat, R.drawable.ic_shopping_bag,
            R.drawable.ic_world, R.drawable.ic_star,
            R.drawable.ic_feather, R.drawable.ic_credit_card,
            R.drawable.ic_home, R.drawable.ic_grid,
    };

    private String[] navigationTexts = {
            "Chat", "Buy", "Discovery", "Favorite", "Write", "Wallet", "Home", "More"
    };

    private SharedPreferences preferences;
    private MyChatApplication mApp;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private GridView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // TODO 获得 Application 对象
//        mApp = (MyChatApplication) getApplication();
//        // 1.获取进程中的全局变量值
//        if (DEBUG) Log.d(TAG, "Application 初始值:" + mApp.getValue());
//        // 2.重新设置值
//        mApp.setValue("Harvey Ren");
//        // 3.再次获取进程中的全局变量值
//        if (DEBUG) Log.d(TAG, "Application 修改后:" + mApp.getValue());

//        // TODO Android 版本兼容处理
//        // Make sure we're running on HONEYCOMB or higher to use APIs
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//        }

//        // TODO 获取当前系统语言及地区,并更改语言
//        // 1.获得当前的语言或者国家
//        String able = getResources().getConfiguration().locale.getCountry();
//        // 2.进行判断：如果是中文则返回的able.equals("CN")
//        // 3.进行设置的代码为 选择中文
//        Configuration config = getResources().getConfiguration();
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        config.locale = Locale.SIMPLIFIED_CHINESE;
//        getResources().updateConfiguration(config, dm);

//        // TODO 清除通知
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.cancel(0);

//        // TODO 竖屏/横屏
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            // 竖屏......
//        } else {
//            // 横屏......
//        }

//        // TODO dp与px单位转换
//        // 1.dp -> px
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        int px = (int) (dp * scale + 0.5f);
//        // 2.px -> dp
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        int dp = (int) (px / scale + 0.5f);

        initViews();

        if (savedInstanceState != null && savedInstanceState.containsKey("main_status")) {
            // mSelectPosition = savedInstanceState.getInt("main_status");
        }
        preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private void initViews() {
        // TODO 初始化内容页
        // ChatFragment chatFragment = new ChatFragment();
        // mFragmentList.add(chatFragment);

        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setAdapter(new BottomNavigationGridViewAdapter(getApplicationContext()));
    }

    private static class ViewHolder {
        ImageView bottomNavigationIcon;
    }

    private class BottomNavigationGridViewAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;

        private BottomNavigationGridViewAdapter(Context context) {
            this.mContext = context;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
                convertView.setTag(viewHolder);
            } else {
                convertView = mInflater.inflate(R.layout.bottom_navigation_item, null);

                viewHolder = new ViewHolder();
                ImageView imageView = convertView.findViewById(R.id.bottom_navigation_icon);
                imageView.setImageResource(navigationIcons[position]);
                imageView.setContentDescription(navigationTexts[position]);
                viewHolder.bottomNavigationIcon = imageView;
                convertView.setTag(viewHolder);
            }
            return convertView;
        }
    }
}

/*
private int[] bottomNavigationIcon = { R.drawable.address_book, R.drawable.calendar, R.drawable.camera, R.drawable.clock, R.drawable.games_control, R.drawable.messenger, R.drawable.ringtone, R.drawable.settings, R.drawable.speech_balloon, R.drawable.weather, R.drawable.world, R.drawable.youtube };
private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声", "设置", "语音", "天气", "浏览器", "视频" };
private List<Map<String, Object>> dataList;

view_item.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="10dp">
    <ImageView
        android:id="@+id/image"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:src="@mipmap/ic_launcher" />

    <TextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="5dp"
        android:text="文字"
        android:textColor="#ffffff" />
</LinearLayout>

dataList = new ArrayList<Map<String, Object>>();
for (int i = 0; i < bottomNavigationIcon.length; i++) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("image", bottomNavigationIcon[i]);
    map.put("text", iconName[i]);
    dataList.add(map);
}

String[] from = {"image", "text"};
int[] to = {R.id.image, R.id.text};
SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, dataList, R.layout.view_item, from, to);
mGirdView.setAdapter(mSimpleAdapter);
*/
