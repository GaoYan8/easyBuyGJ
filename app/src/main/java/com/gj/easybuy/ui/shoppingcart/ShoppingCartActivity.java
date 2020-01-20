package com.gj.easybuy.ui.shoppingcart;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.gj.easybuy.R;
import com.gj.easybuy.base.BaseHttpActivity;
import com.gj.easybuy.constant.CommonEnum;
import com.gy.gylibrary.utils.AppManager;
import com.gy.gylibrary.utils.DisplayMetricsUtil;
import com.gy.gylibrary.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 购物车
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2020/1/19
 * @Describe
 */
public class ShoppingCartActivity extends BaseHttpActivity {


    @BindView(R.id.rl_shopping_cart_title_root)
    RelativeLayout rl_work_title_root;
    @BindView(R.id.rv_shopping_cart_recyclerview)
    RecyclerView rv_work_recyclerview;

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


    private CommonAdapter commonAdapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected CommonEnum.SystemTitleBar setSystemTitleBar() {
        return CommonEnum.SystemTitleBar.white;
    }


    @Override
    protected int getLayoutId()  {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void initTitle() {
        /**
         * 设置title 高度
         */
        int bar = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, DisplayMetricsUtil.getStatusBarHeight(mActivity), getResources().getDisplayMetrics()));
        rl_work_title_root.setPadding(0, bar + DisplayMetricsUtil.dp2px(mActivity, 10), 0, +DisplayMetricsUtil.dp2px(mActivity, 12));
    }

    @Override
    protected void initView() {
        /*commonAdapter = new CommonAdapter<String>(mActivity, R.layout.item_work_bench, list) {

            @Override
            protected void convert(ViewHolder holder, final WorkIconViewEntity workIconView, final int position) {
                holder.setImageResource(R.id.iv_item_work_bench_icon, workIconView.getIconRes());
                holder.setText(R.id.tv_item_work_bench_name, workIconView.getTitleName());

            }
        };

        //设置布局管理器 , 将布局设置成纵向
        rv_work_recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 4));
        rv_work_recyclerview.addItemDecoration(new GridSpacingItemNotBothDecoration(4, DisplayMetricsUtil.dp2px(mActivity, 18), true, false));
        //设置分隔线
        rv_work_recyclerview.setAdapter(commonAdapter);*/


    }

    @Override
    protected void setListener() {
        super.setListener();
       /* commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity_(new Intent(mActivity, list.get(position).getTargetClass()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });*/
    }


}
