package com.gj.easybuy.ui.home;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.gj.easybuy.R;
import com.gj.easybuy.ui.homepage.HomePageActivity;
import com.gj.easybuy.ui.me.MeActivity;
import com.gj.easybuy.ui.shoppingcart.ShoppingCartActivity;
import com.gy.gylibrary.utils.AppManager;
import com.gy.gylibrary.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/11/23
 * @Describe
 */
public class HomeActivity extends TabActivity {


    // 首页页标识
    public static final String HOMEPAGE_ACT = "HOMEPAGE_ACT";
    // 工作台页标识
    public static final String SHOPPINGCART_ACT = "SHOPPINGCART_ACT";
    // 我的页标识
    public static final String ME_ACT = "ME_ACT";


    /**
     * TabHost
     */
    private TabHost mTabHost;

    /**
     * 首页标签页
     */
    @BindView(R.id.imgbtn_homepage)
    ImageButton imgbtnHomepage;

    /**
     * 成果标签页
     */
    @BindView(R.id.imgbtn_shoppingcart)
    ImageButton imgbtnShoppingCart;
    /**
     * 需求标签页
     */
    @BindView(R.id.imgbtn_me)
    ImageButton imgbtnMe;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initViews();
    }

    /**
     * 初始化视图
     */
    protected void initViews() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //设置状态栏
        setTitleBarTint();

        initTab();
        initData();

    }


    /**
     * title Bar
     */
    private void setTitleBarTint() {

        if (Build.VERSION.SDK_INT >= AppUtils.MINIMUM_VERSION_CODES) {
            //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  状态栏颜色是黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            //底部虚拟按键背景色
            getWindow().setNavigationBarColor(getResources().getColor(R.color.tab_bottom_background_color));
            /**
             * 设置状态栏高度
             */
            /*ViewGroup.LayoutParams layoutParams = viewTitleBar.getLayoutParams();
            layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, DisplayUtils.getStatusBarHeight(this), getResources().getDisplayMetrics()));
            viewTitleBar.setLayoutParams(layoutParams);*/
        } else {
            //设置状态栏字体颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }


    /**
     * 初始化标签页
     */
    private void initTab() {
        mTabHost = getTabHost();


        Intent homepageIntent = new Intent(this, HomePageActivity.class);
        Intent shoppingCartIntent = new Intent(this, ShoppingCartActivity.class);
        Intent meIntent = new Intent(this, MeActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(HOMEPAGE_ACT).setIndicator(HOMEPAGE_ACT).setContent(homepageIntent));
        mTabHost.addTab(mTabHost.newTabSpec(SHOPPINGCART_ACT).setIndicator(SHOPPINGCART_ACT).setContent(shoppingCartIntent));
        mTabHost.addTab(mTabHost.newTabSpec(ME_ACT).setIndicator(ME_ACT).setContent(meIntent));
        mTabHost.setCurrentTabByTag(HOMEPAGE_ACT);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        setButtonSelected(R.id.imgbtn_homepage);

    }

    /**
     * 设置按钮状态变化
     *
     * @param viewId
     */
    private void setButtonSelected(int viewId) {
        imgbtnHomepage.setSelected(viewId == R.id.imgbtn_homepage ? true : false);
        imgbtnShoppingCart.setSelected(viewId == R.id.imgbtn_shoppingcart ? true : false);
        imgbtnMe.setSelected(viewId == R.id.imgbtn_me ? true : false);
    }

    /**
     * 设置当前标签页
     *
     * @param type
     */
    private void setCurrentTab(String type) {
        mTabHost.setCurrentTabByTag(type);
    }

    /**
     * 设置当前标签页
     *
     * @param id
     */
    private void setCurrentTab(int id) {
        switch (id) {
            case R.id.imgbtn_homepage:
                setCurrentTab(HOMEPAGE_ACT);
                break;
            case R.id.imgbtn_shoppingcart:
                setCurrentTab(SHOPPINGCART_ACT);
                break;
            case R.id.imgbtn_me:
                setCurrentTab(ME_ACT);
                break;

        }
    }

    /**
     * 底部菜单按钮监听事件
     *
     * @param view
     */
    @OnClick({
            R.id.imgbtn_homepage,
            R.id.imgbtn_shoppingcart,
            R.id.imgbtn_me
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftlayout:

                break;
            case R.id.imgbtn_homepage:
            case R.id.imgbtn_shoppingcart:
            case R.id.imgbtn_me:
            default:
                setButtonSelected(view.getId());
                setCurrentTab(view.getId());
                break;

        }

    }
}
