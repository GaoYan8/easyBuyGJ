package com.gj.easybuy.ui.me;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gj.easybuy.R;
import com.gj.easybuy.base.BaseHttpActivity;
import com.gj.easybuy.constant.CommonEnum;
import com.gj.easybuy.view.SelectCommonItemView;
import com.gj.easybuy.view.ZARLTipsDialog;
import com.gy.gylibrary.headzoomlayout.HeadZoomLayout;
import com.gy.gylibrary.image.ImageLoaderUtils;
import com.gy.gylibrary.utils.AppManager;
import com.gy.gylibrary.utils.AppUtils;
import com.gy.gylibrary.utils.DisplayMetricsUtil;
import com.gy.gylibrary.utils.ToastUtils;
import com.gy.gylibrary.view.NiceImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2020/1/19
 * @Describe
 */
public class MeActivity extends BaseHttpActivity {

    //确认退出app
    private ZARLTipsDialog exitDialog;

    @BindView(R.id.rl_me_title_root)
    RelativeLayout rl_me_title_root;


    @BindView(R.id.sciv_me_system_version)
    SelectCommonItemView sciv_me_system_version;

    @BindView(R.id.tv_me_logout)
    TextView tv_me_logout;
    @BindView(R.id.niv_me_user_head_portrait)
    NiceImageView niv_me_user_head_portrait;
    @BindView(R.id.hzl_me_zoomLayout)
    HeadZoomLayout hzl_me_zoomLayout;
    @BindView(R.id.iv_me_head_bg)
    ImageView iv_me_head_bg;
    @BindView(R.id.tv_me_user_name)
    TextView tv_me_user_name;


    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        //再判断是否双击退出
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.show(mActivity, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            AppManager.getAppManager().appExit();
        }
    }


    @Override
    protected CommonEnum.SystemTitleBar setSystemTitleBar() {
        return CommonEnum.SystemTitleBar.white;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void initTitle() {
        /**
         * 设置title 高度
         */
        int bar = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, DisplayMetricsUtil.getStatusBarHeight(mActivity), getResources().getDisplayMetrics()));
        rl_me_title_root.setPadding(0, bar + DisplayMetricsUtil.dp2px(mActivity, 10), 0, DisplayMetricsUtil.dp2px(mActivity, 12));
    }

    @Override
    protected void initView() {
        //tv_me_user_name.setText(mApplication.getCurrentLoginUser().getUsername());
        sciv_me_system_version.setRightHint("版本" + AppUtils.getVersionName(mActivity));
        exitDialog = new ZARLTipsDialog(mActivity).builder()
                .setTitle("提示")
                .setMsg("您确认要退出当前账号吗?")
                .setPositiveButton("确认", (v) -> {
                    mApplication.logoutSucceed();
                    AppManager.getAppManager().finishAllActivity();
                    AppManager.getAppManager().restartApp2(mApplication);

                }).setNegativeButton("取消", (v) -> {
                });


        ImageLoaderUtils.getInstance().displayUserHead(mActivity,
                "http://img3.imgtn.bdimg.com/it/u=2819213726,3159432041&fm=26&gp=0.jpg"
                , niv_me_user_head_portrait);
        ImageLoaderUtils.getInstance().loadResDrawableImage(mActivity,
                "http://img3.imgtn.bdimg.com/it/u=2819213726,3159432041&fm=26&gp=0.jpg"
                , iv_me_head_bg);
        /*iv_me_head_bg.setBackgroundColor(getResources().getColor(R.color.default_theme_color));*/
    }

    @OnClick({
            R.id.sciv_me_message_notice,
            R.id.sciv_me_change_password,
            R.id.iv_me_user_info,
            R.id.tv_me_logout
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sciv_me_message_notice:
//                startActivity_(new Intent(mActivity, MeActivity.class));
                break;
            case R.id.sciv_me_change_password:
                //startActivity_(new Intent(mActivity, ChangePasswordActivity.class));
                break;
            case R.id.iv_me_user_info:
                showShortToast("点击了");
                break;
            case R.id.tv_me_logout:
                exitDialog.show();
                break;

        }
    }

   /* @Override
    protected void setListener() {
        super.setListener();
        hzl_me_zoomLayout.addOnHeadZoomListener(new HeadZoomLayout.OnHeadZoomListener() {
            @Override
            public void onHeadZoom(boolean isRecovering, float zoomDistance) {
                LogUtils.e("isRecovering "+isRecovering+"========= zoomDistance "+zoomDistance);
            }
        });
    }*/
}