package com.gj.easybuy.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gj.easybuy.R;
import com.gj.easybuy.constant.CommonEnum;
import com.gy.gylibrary.utils.AppManager;
import com.gy.gylibrary.utils.AppUtils;
import com.gy.gylibrary.utils.NetUtils;
import com.gy.gylibrary.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity 父类
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected static String TAG = BaseActivity.class.getSimpleName();
    /**
     * 当前应用环境
     */
    public BaseApplication mApplication;
    /**
     * 当前Activity
     */
    public Activity mActivity;


    private View mRootView;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = (BaseApplication) getApplication();
        mActivity = this;

        doBeforeSetcontentView();
        mRootView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(mRootView);
        mUnBinder = ButterKnife.bind(this);

        if (isStartBarTint()) {
            setTitleBarTint();
        }

        initPreViewAction();
        NetUtils.isNetworkErrThenShowMsg(mActivity);
        initTitle();
        initView();
        setListener();
        initData();
        sendNetWork();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置layout前配置
     */
    protected void doBeforeSetcontentView() {
        //window对用户可见的情况下，打开屏幕并且亮着
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 添加转场动画
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Fade());
        }*/
        AppManager.getAppManager().addActivity(this);
    }


    /**
     * 是否开始沉浸式头布局，默认是开启的
     */
    protected boolean isStartBarTint() {
        return true;
    }

    /**
     * 设置标题栏字体颜色
     * @return
     */
    protected CommonEnum.SystemTitleBar setSystemTitleBar() {
        return CommonEnum.SystemTitleBar.black;
    }

    /**
     * StatueBar 设置背景色
     */
    public void setTitleBarTint() {
        if (Build.VERSION.SDK_INT >= AppUtils.MINIMUM_VERSION_CODES) {

            if (setSystemTitleBar() == CommonEnum.SystemTitleBar.black) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //底部虚拟按键背景色
                getWindow().setNavigationBarColor(getResources().getColor(R.color.tab_bottom_background_color));
            } else if (setSystemTitleBar() == CommonEnum.SystemTitleBar.white) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                //底部虚拟按键背景色
                getWindow().setNavigationBarColor(getResources().getColor(R.color.color_black));
            }
        }
    }


    /**
     * 设置布局
     */
    protected abstract int getLayoutId();

    /**
     * 预加载数据操作
     */
    protected void initPreViewAction() {

    }

    /**
     * 初始化标题栏
     */
    protected abstract void initTitle();

    /**
     * 界面布局初始化
     */
    protected abstract void initView();


    /**
     * 界面数据初始化
     **/
    protected void initData() {

    }

    /**
     * 界面UI事件监听
     **/
    protected void setListener() {

    }

    /**
     * 请求网络数据
     */
    protected void sendNetWork() {

    }

    public View getmRootView() {
        return mRootView;
    }

    /**
     * 短暂显示Toast提示(来自res)
     **/
    protected void showShortToast(int resId) {
        ToastUtils.show(mActivity, getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    protected void showShortToast(String text) {
        ToastUtils.show(mActivity, text, Toast.LENGTH_SHORT);
    }


    protected void startActivity_(Intent intent) {
        if (null != intent) {
            startActivity(intent);
        }
    }

    /**
     * 含有Bundle通过Class 返回跳转界面
     *
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResult_(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
    /**
     * 进度条  默认点击不能消失
     *
     * @param message
     * @return
     */
    /*protected Dialog getProgressDialog(String title, String message) {
        return getProgressDialog(title, message, false, true);
    }*/

    /**
     * 进度条
     *
     * @param message
     * @param cancelableTouch
     * @param cancelable
     * @return
     */
    /*protected Dialog getProgressDialog(String title, String message, boolean cancelableTouch, boolean cancelable) {
        Dialog mLoadingDialog = new Dialog(mActivity);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置宽度
        Window window = mLoadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);

        View layout = View.inflate(mActivity, R.layout.view_progress_wheel_layout_view, null);
        TextView titleTextView = (TextView) layout.findViewById(R.id.tv_view_progress_wheel_layout_view_title);
        TextView messageTextView = (TextView) layout.findViewById(R.id.tv_view_progress_wheel_layout_view_message);
        //如果title为空的话，就隐藏title
        if (StringUtils.isEmpty(title)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(title);
        }
        messageTextView.setText(message);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                (int) (DisplayMetricsUtil.getScreenWidth(this) * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        mLoadingDialog.setCanceledOnTouchOutside(cancelableTouch);
        mLoadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
        return mLoadingDialog;
    }*/



}