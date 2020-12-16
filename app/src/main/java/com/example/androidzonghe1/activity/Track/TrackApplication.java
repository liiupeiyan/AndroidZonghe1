package com.example.androidzonghe1.activity.Track;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.AddEntityRequest;
import com.baidu.trace.api.entity.AddEntityResponse;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.Utils.lsbWork.ScreenUtils;
import com.example.androidzonghe1.Utils.lsbWork.ToastUtils;
import com.example.androidzonghe1.activity.lpyWork.TracingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by baidu on 17/1/12.
 */

public class TrackApplication extends Application {

    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    private LocRequest locRequest = null;

    private static Notification notification = null;

    public Context mContext = null;

    public List<ItemInfo> itemInfos = new ArrayList<>();

    public SharedPreferences trackConf = null;

    /**
     * 轨迹客户端
     */
    public static LBSTraceClient mClient = null;

    /**
     * 轨迹服务
     */
    public static Trace mTrace = null;

    /**
     * 轨迹服务ID
     * 225228
     */
    public static long serviceId = 225208;
//    public long serviceId = 225228;
    /**
     * Entity标识
     */
    public static String entityName = "init";


    public boolean isRegisterReceiver = false;

    public static boolean isNeedObjectStorage = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    public static int screenWidth = 0;

    public static int screenHeight = 0;

    //MyApplication
    Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (ConfigUtil.driverPhone.equals("")){
            Toast.makeText(this, "请先选择司机才能查看司机行驶轨迹！", Toast.LENGTH_LONG).show();
        }
        Log.e("entityName",ConfigUtil.driverPhone);
        mContext = getApplicationContext();
//        entityName = CommonUtil.getEntityName();

        // 若为创建独立进程，则不初始化成员变量
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(mContext))) {
            return;
        }

        SDKInitializer.initialize(mContext);
        MultiDex.install(mContext);
        ToastUtils.init(mContext);
        ScreenUtils.init(mContext);
        initView();
        initNotification();
        mClient = new LBSTraceClient(mContext);
        mTrace = new Trace(serviceId, entityName,isNeedObjectStorage);
        mTrace.setNotification(notification);

        trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
        locRequest = new LocRequest(serviceId);

        mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }

            @Override
            public Map<String, String> onTrackAttributeCallback(long locTime) {
                System.out.println("onTrackAttributeCallback, locTime : " + locTime);
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        });
        AddEntityRequest request = new AddEntityRequest();
        request.setServiceId(serviceId);
        request.setEntityName(entityName);
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("district", "裕华区");
        columns.put("city", "石家庄");
//        columns.put("ACC_NBR", "1234567890");
        request.setColumns(columns);
       mClient.addEntity(request, new OnEntityListener() {
            @Override
            public void onAddEntityCallback(AddEntityResponse addEntityResponse) {
                super.onAddEntityCallback(addEntityResponse);
            }
        });

        clearTraceStatus();
    }

    public static void initTrace(){
        mTrace = new Trace(serviceId, entityName,isNeedObjectStorage);
        mTrace.setNotification(notification);
    }

    /**
     * 获取当前位置
     */
    public void getCurrentLocation(OnEntityListener entityListener, OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位
        if (NetUtil.isNetworkAvailable(mContext)
                && trackConf.contains("is_trace_started")
                && trackConf.contains("is_gather_started")
                && trackConf.getBoolean("is_trace_started", false)
                && trackConf.getBoolean("is_gather_started", false)) {
            LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
            ProcessOption processOption = new ProcessOption();
            processOption.setNeedDenoise(true);
            processOption.setRadiusThreshold(100);
            request.setProcessOption(processOption);
            Toast.makeText(this,"queryLatestPoint",Toast.LENGTH_LONG).show();
            mClient.queryLatestPoint(request, trackListener);
        } else {
            Toast.makeText(this,"queryRealTimeLoc",Toast.LENGTH_LONG).show();
            mClient.queryRealTimeLoc(locRequest, entityListener);
        }
    }

    private void initView() {
        ItemInfo tracing = new ItemInfo(R.mipmap.icon_tracing, R.string.tracing_title, R.string.tracing_desc,
                TracingActivity.class);
//        ItemInfo trackQuery = new ItemInfo(R.mipmap.icon_track_query, R.string.track_query_title,
//                R.string.track_query_desc, TrackQueryActivity.class);
//        ItemInfo fence = new ItemInfo(R.mipmap.icon_fence, R.string.fence_title,
//                R.string.fence_desc, FenceActivity.class);
//        ItemInfo bos = new ItemInfo(R.mipmap.icon_bos, R.string.bos_title, R.string.bos_desc, BosActivity.class);
//        ItemInfo cacheManage = new ItemInfo(R.mipmap.icon_cache_manage,
//                R.string.cache_manage_title, R.string.cache_manage_desc, CacheManageActivity.class);
//        ItemInfo faq = new ItemInfo(R.mipmap.icon_fag, R.string.fag_title, R.string.faq_desc, FAQActivity.class);
        itemInfos.add(tracing);
//        itemInfos.add(trackQuery);
//        itemInfos.add(fence);
//        itemInfos.add(bos);
//        itemInfos.add(cacheManage);
//        itemInfos.add(faq);

        getScreenSize();
    }

    @TargetApi(16)
    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, TracingActivity.class);

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.icon_tracing);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 设置PendingIntent
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(icon)  // 设置下拉列表中的图标(大图标)
                .setContentTitle("百度鹰眼") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.icon_tracing) // 设置状态栏内的小图标
                .setContentText("服务正在运行...") // 设置上下文内容
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && null != notificationManager) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("trace", "trace_channel",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);

            builder.setChannelId("trace"); // Android O版本之后需要设置该通知的channelId
        }

        notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }


    /**
     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
     * <p>
     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
     */
    private void clearTraceStatus() {
        if (trackConf.contains("is_trace_started") || trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }

    /**
     * 初始化请求公共参数
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }

    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }

    public void clear() {
        itemInfos.clear();
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public Handler getHandler(){
        return handler;
    }

}
