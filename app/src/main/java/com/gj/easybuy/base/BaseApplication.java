package com.gj.easybuy.base;

import android.app.Application;
import android.content.Context;

import com.gj.easybuy.BuildConfig;
import com.gj.easybuy.constant.SystemConstantFlag;
import com.gj.easybuy.entity.user.UserEntity;
import com.gj.easybuy.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gy.gylibrary.GYConfig;
import com.gy.gylibrary.http.okhttp.OkHttpUtils;
import com.gy.gylibrary.http.okhttp.cookie.CookieJarImpl;
import com.gy.gylibrary.http.okhttp.cookie.store.MemoryCookieStore;
import com.gy.gylibrary.okhttplib.config.HttpConfig;
import com.gy.gylibrary.utils.SharedPreferencesUtils;
import com.gy.gylibrary.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by xiaowu on 2016/7/7 0007.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    private static Gson gson;

    /*public int SCREEN_WIDTH = -1;
    public int SCREEN_HEIGHT = -1;
    public float DIMEN_RATE = -1.0F;
    public int DIMEN_DPI = -1;*/


    /**
     * 首页界面侧拉菜单开关
     */
    public boolean isDrawerShow = false;

    /**
     * 用户是否登录
     */
    private boolean isLogin = false;
    /**
     * 当前登录用户
     */
    private UserEntity currentLoginUser;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //layout.setPrimaryColorsId(R.color.color_white_a1, R.color.color_black_a1);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context)/*.setDrawableSize(20)*/;
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        gson = getGson();
        //初始化屏幕宽高

        //getScreenSize();

        GYConfig.isDebug = BuildConfig.DEBUG;
        //文件初始化
        GYConfig.initFileDir(mApplication);
        GYConfig.initOkHttpClient();
        GYConfig.initCrashHandlerUtil(this);

        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setShowHttpLog(GYConfig.isDebug);
        httpConfig.setShowLifecycleLog(GYConfig.isDebug);
        GYConfig.initOkHttp3Client(this, httpConfig);
    }

    /**
     * 获取当前 应用程序baseApplication
     *
     * @return
     */
    public static BaseApplication getBaseApplication() {
        return mApplication;
    }

    /**
     * 初始化Gson
     *
     * @return
     */
    public Gson getGson() {
        if (null == gson) {
            GsonBuilder builder = new GsonBuilder();
            /*builder.registerTypeAdapter(Date.class, new DateJsonDeserializer());*/
            builder.setDateFormat(DateUtils.yyyy_MM_dd_HH_mm_ss);
            gson = builder.create();
        }
        return gson;
    }


    /*public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }*/

    /**
     * 初始化网络请求实例
     */
    private void initOkHttpClient() {
        OkHttpUtils.initClient(new OkHttpClient.Builder()
                .connectTimeout(com.gy.gylibrary.http.HttpConfig.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(com.gy.gylibrary.http.HttpConfig.readTimeout, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build());
    }

    /**
     * 是否登录
     *
     * @return true 登录   false  未登录
     */
    public boolean isLogin() {
        return null != currentLoginUser && isLogin;
    }

    /**
     * 登录成功后，保存用户登录账户和密码， 并修改的登录状态
     *
     * @param context
     * @param loginName
     * @param password
     */
    public void loginSucceed(Context context, String loginName, String password) {
        isLogin = true;
        if (!StringUtils.isEmpty(loginName)) {
            SharedPreferencesUtils.initSharedPreferences(context, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).put(SystemConstantFlag.SYSTEM_LOGINNAME, loginName);
        }
        if (!StringUtils.isEmpty(password)) {
            SharedPreferencesUtils.initSharedPreferences(context, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).put(SystemConstantFlag.SYSTEM_LOGINPASSWORD, password);
        }
    }

    /**
     * 退出登录后，清除保存的 用户登录账户和密码， 并修改的登录状态
     */
    public void logoutSucceed() {
        this.isLogin = false;
        this.currentLoginUser = null;
        SharedPreferencesUtils.initSharedPreferences(mApplication, SystemConstantFlag.SHARED_PREFERENCES_FILENAME)
                .remove(SystemConstantFlag.SYSTEM_ZARLLOGINUSER)
//                .remove(SystemConstantFlag.SYSTEM_LOGINNAME)
                .remove(SystemConstantFlag.SYSTEM_LOGINPASSWORD);
    }

    /**
     * 清除用户密码
     */
    public void cleanPassword() {
        SharedPreferencesUtils.initSharedPreferences(mApplication, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).remove(SystemConstantFlag.SYSTEM_LOGINPASSWORD);
    }

    /**
     * 保存用户登录结果信息
     *
     * @param loginUser
     * @return
     */
    public void setLoginUserInfo(UserEntity loginUser) {
        this.currentLoginUser = loginUser;
        SharedPreferencesUtils.initSharedPreferences(mApplication, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).put(SystemConstantFlag.SYSTEM_ZARLLOGINUSER, getGson().toJson(loginUser));
    }


    /**
     * 修改用户图像
     *
     * @param head
     */
    /*public void updateUserHeadPortrait(String head) {
        if (!StringUtils.isEmpty(head)) {
            this.currentLoginUser.setAvatar(head);
            SharedPreferencesUtils.initSharedPreferences(mApplication,SystemConstantFlag.SHARED_PREFERENCES_FILENAME).put(SystemConstantFlag.SYSTEM_ZARLLOGINUSER, getGson().toJson(this.currentLoginUser));
        }
    }*/

    /**
     * 返回用户ID
     *
     * @return ID
     */
    public String getUserId() {
        if (null != this.currentLoginUser) {
            String id = currentLoginUser.getUsersid();
            return StringUtils.isEmpty(id) ? "" : id;
        }
        return "";
    }


    /**
     * 获取当前登录用户状态
     *
     * @return
     */
    public UserEntity getCurrentLoginUser() {
        if (null == currentLoginUser) {
            String zarlLoginUser = (String) SharedPreferencesUtils.initSharedPreferences(mApplication, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).get(SystemConstantFlag.SYSTEM_ZARLLOGINUSER, "");
            if (!StringUtils.isEmpty(zarlLoginUser)) {
                currentLoginUser = getGson().fromJson(zarlLoginUser, UserEntity.class);
            }
            if (null == currentLoginUser) {
                currentLoginUser = new UserEntity();
            }
        }
        return currentLoginUser;
    }

    /**
     * 获取当前用户Gson格式数据
     *
     * @return
     */
    public String getCurrentUserGson() {
        String zarlUser = (String) SharedPreferencesUtils.initSharedPreferences(mApplication, SystemConstantFlag.SHARED_PREFERENCES_FILENAME).get(SystemConstantFlag.SYSTEM_ZARLLOGINUSER, "");
        if (StringUtils.isEmpty(zarlUser)) {
            if (null != currentLoginUser) {
                zarlUser = gson.toJson(currentLoginUser);
            } else {
                zarlUser = gson.toJson(new UserEntity());
            }
        }
        return zarlUser;
    }


    /**
     * 友盟分享
     */
   /* private void initUMConfigure() {
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        *//**
     * 初始化common库
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey
     * 参数3:【友盟+】 Channel
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret
     *//*
        UMConfigure.init(mApplication, "5c04ced0f1f5569d11000246", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        PlatformConfig.setWeixin("wx0772f72e3c099b43", "2e6aab982402ddd4b461601521d3add3");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3384239177", "7873c673042a1be39a0c48611204bbe9","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1108023740", "hGtBdm9c9yUb4BBf");
    }*/


}
