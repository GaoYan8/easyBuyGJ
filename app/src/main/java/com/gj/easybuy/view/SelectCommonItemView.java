package com.gj.easybuy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gj.easybuy.R;
import com.gy.gylibrary.utils.StringUtils;

/**
 * 点击选择条目布局通用view
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/10/17
 * @Describe
 */
public class SelectCommonItemView extends LinearLayout {
    private ImageView icivLeftimg;
    private TextView scivLeftText;
    private ImageView icivRightimg;
    private TextView scivRightText;
    private View scivView;

    public SelectCommonItemView(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_select_common_layout_view, this);
        icivLeftimg = view.findViewById(R.id.iv_select_common_item_left_img);
        scivLeftText = view.findViewById(R.id.tv_select_common_item_left_text);
        icivRightimg = view.findViewById(R.id.iv_select_common_item_right_img);
        scivRightText = view.findViewById(R.id.tv_select_common_item_right_text);

        scivView = view.findViewById(R.id.view_select_common_item_line);
    }


    public SelectCommonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectCommonItemView);

        //左边图片
        int leftResource = typedArray.getResourceId(R.styleable.SelectCommonItemView_sciv_left_img, 0);
        if (leftResource != 0) {
            icivLeftimg.setImageResource(leftResource);
        }
        //左边图片隐藏
        boolean leftVisible = typedArray.getBoolean(R.styleable.SelectCommonItemView_sciv_left_img_visible, true);
        icivLeftimg.setVisibility(leftVisible ? View.VISIBLE : View.GONE);


        CharSequence leftText = typedArray.getText(R.styleable.SelectCommonItemView_sciv_left_text);
        if (!TextUtils.isEmpty(leftText)) {
            scivLeftText.setText(leftText);
        }

        CharSequence rightText = typedArray.getText(R.styleable.SelectCommonItemView_sciv_right_text);
        if (!TextUtils.isEmpty(rightText)) {
            scivRightText.setText(rightText);
        }
        //右侧提示文字颜色
        int rightTextColor = typedArray.getColor(R.styleable.SelectCommonItemView_sciv_right_hint_text_color, getResources().getColor(R.color.color_light_gray_b1));
        scivRightText.setHintTextColor(rightTextColor);

        CharSequence rightHintText = typedArray.getText(R.styleable.SelectCommonItemView_sciv_right_hint_text);
        if (!TextUtils.isEmpty(rightHintText)) {
            scivRightText.setHint(rightHintText);
        }

        //右边图片箭头
        int resource = typedArray.getResourceId(R.styleable.SelectCommonItemView_sciv_right_img, R.mipmap.img_arrow_black);
        icivRightimg.setImageResource(resource);
        //右边图片隐藏
        boolean rightVisible = typedArray.getBoolean(R.styleable.SelectCommonItemView_sciv_right_img_visible, true);
        icivRightimg.setVisibility(rightVisible ? View.VISIBLE : View.GONE);


        //下边黑线
        boolean visible = typedArray.getBoolean(R.styleable.SelectCommonItemView_sciv_line_visible, false);
        scivView.setVisibility(visible ? View.VISIBLE : View.GONE);

        float marginLeft = typedArray.getDimension(R.styleable.SelectCommonItemView_sciv_line_margin_left, 0f);
        float marginRight = typedArray.getDimension(R.styleable.SelectCommonItemView_sciv_line_margin_right, 0f);
        LayoutParams lp = (LayoutParams) scivView.getLayoutParams();
        if(marginLeft != 0) {
            lp.leftMargin = (int) marginLeft;
        }
        if(marginRight != 0) {
            lp.rightMargin = (int) marginRight;
        }
        scivView.setLayoutParams(lp);


        typedArray.recycle();
    }


    /**
     * 设置输入文字
     *
     * @param text
     */
    public void setRightText(String text) {
        this.scivRightText.setText(text);
    }

    /**
     * 设置提示文字
     *
     * @param text
     */
    public void setRightHint(String text) {
        this.scivRightText.setHint(text);
    }

    public String getRightText() {
        return scivRightText.getText().toString().trim();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            this.scivLeftText.setText(title);
        }
    }

    /**
     * 设置箭头显示隐藏
     *
     * @param setVisibility
     */
    public void setArrowVisibility(boolean setVisibility) {
        if (null != icivRightimg) {
            icivRightimg.setVisibility(setVisibility ? VISIBLE : INVISIBLE);
        }
    }


    /**
     * 设置文字颜色
     *
     * @param color 资源颜色
     */
    public void setTextColor(int color) {
        this.scivRightText.setTextColor(color);
    }
}
