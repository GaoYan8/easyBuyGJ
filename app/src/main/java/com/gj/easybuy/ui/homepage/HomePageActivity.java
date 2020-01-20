package com.gj.easybuy.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.gj.easybuy.R;
import com.gj.easybuy.base.BaseHttpActivity;
import com.gj.easybuy.constant.CommonEnum;
import com.gy.gylibrary.banner.Banner;
import com.gy.gylibrary.banner.BannerConfig;
import com.gy.gylibrary.banner.listener.OnBannerListener;
import com.gy.gylibrary.banner.loader.ImageLoader;
import com.gy.gylibrary.image.ImageLoaderUtils;
import com.gy.gylibrary.permission.Action;
import com.gy.gylibrary.permission.AndPermission;
import com.gy.gylibrary.permission.Permission;
import com.gy.gylibrary.utils.AppManager;
import com.gy.gylibrary.utils.DisplayMetricsUtil;
import com.gy.gylibrary.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2020/1/19
 * @Describe
 */
public class HomePageActivity extends BaseHttpActivity {


    //title
    @BindView(R.id.rl_home_page_title_root)
    RelativeLayout rl_home_page_title_root;

    @BindView(R.id.sr_homepage_slide)
    SmartRefreshLayout sr_homepage_slide;
    // banner
    @BindView(R.id.ban_homepage_banner)
    Banner banner;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();




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
    protected void initPreViewAction() {
        super.initPreViewAction();
        //checkPermission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initTitle() {
        /**
         * 设置title 高度
         */
        int bar = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, DisplayMetricsUtil.getStatusBarHeight(mActivity), getResources().getDisplayMetrics()));
        rl_home_page_title_root.setPadding(0, bar + DisplayMetricsUtil.dp2px(mActivity, 10), 0, +DisplayMetricsUtil.dp2px(mActivity, 12));
    }

    @Override
    protected void initView() {
        initBanner();
    }

    @Override
    protected void initData() {

        titles.clear();
        images.clear();
        for (int i=0;i<5;i++) {
            titles.add("高京"+i);
        }
        images.add("http://t7.baidu.com/it/u=3616242789,1098670747&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1580027163&t=de1b9a2fdd09ce7844b895f3edb8cf9d");
        images.add("http://t9.baidu.com/it/u=1307125826,3433407105&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1580027163&t=103b41d034b1cf26fe4ea97dd57b03ae");
        images.add("http://t9.baidu.com/it/u=3363001160,1163944807&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1580027163&t=b01a093a5d2143927ee26ea59d5b203e");
        images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1388212479,1974245426&fm=26&gp=0.jpg");
        images.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3870360878,804346467&fm=26&gp=0.jpg");
        banner.update(images, titles);

    }

    @Override
    protected void setListener() {
        sr_homepage_slide.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });


    }



    /**
     * 头部新闻
     */
    public void initBanner() {
        banner.setOnBannerListener(position -> {
           /* if (null != bannerList && bannerList.size() > 0) {
                Intent intent = new Intent(mActivity, NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.NEWS_ID, bannerList.get(position).getID());
                startActivity_(intent);
            }*/
        });
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderUtils.getInstance().display(mActivity, path, imageView);
            }
        });
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片集合
        //banner.setImages(images);
        //设置banner动画效果
        //banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        // banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        //banner.start();
    }















    /**
     * 校验权限
     */
    private void checkPermission() {
        AndPermission.with(mActivity)
                .permission(Permission.READ_PHONE_STATE, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //showShortToast("用户主动授权");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showShortToast("没有权限无法使用呦");

                        /*Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);*/

                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        new Handler().postDelayed(() -> AppManager.getAppManager().appExit(), 2000);
                    }
                }).start();
    }





}
