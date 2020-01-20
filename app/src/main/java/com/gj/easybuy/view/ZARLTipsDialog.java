package com.gj.easybuy.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gj.easybuy.R;
import com.gy.gylibrary.utils.DisplayMetricsUtil;
import com.gy.gylibrary.view.border.SelectorFactory;


public class ZARLTipsDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvTitle;
    private ImageView ivClose;
    private TextView tvMsg;
    private Button btnNeg;
    private Button btnPos;
    private ImageView ivLine;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public ZARLTipsDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ZARLTipsDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_tips_dialog_view, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.lLayout_bg);
        tvTitle = view.findViewById(R.id.tv_alert_title);
        tvTitle.setVisibility(View.GONE);
        ivClose=view.findViewById(R.id.iv_alert_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        tvMsg = view.findViewById(R.id.tv_alert_msg);
        tvMsg.setVisibility(View.GONE);
        btnNeg = view.findViewById(R.id.btn_alert_neg);
        btnNeg.setVisibility(View.GONE);
        btnPos = view.findViewById(R.id.btn_alert_pos);
        btnPos.setVisibility(View.GONE);
        ivLine = view.findViewById(R.id.iv_alert_line);
        ivLine.setVisibility(View.GONE);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ViewAlertDialogStyle);

        SelectorFactory.newShapeSelector()
                .setShape(GradientDrawable.RECTANGLE)
                .setCornerRadius(DisplayMetricsUtil.dp2px(context, 8))
                .setDefaultBgColor(context.getResources().getColor(R.color.color_white_a1))
                .bind(lLayout_bg);


        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
        return this;
    }

    public ZARLTipsDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            tvTitle.setText("标题");
        } else {
            tvTitle.setText(title);
        }
        return this;
    }

    public ZARLTipsDialog setMsg(CharSequence msg) {
        showMsg = true;
        if ("".equals(msg)) {
            tvMsg.setText("内容");
        } else {
            tvMsg.setText(msg);
        }
        return this;
    }

    public ZARLTipsDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ZARLTipsDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ZARLTipsDialog setNegativeButton(String text, final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btnNeg.setText("取消");
        } else {
            btnNeg.setText(text);
        }
        btnNeg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            tvTitle.setText("提示");
            tvTitle.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            tvTitle.setVisibility(View.VISIBLE);
        }
        if (showMsg) {
            tvMsg.setVisibility(View.VISIBLE);
        }
        if (!showPosBtn && !showNegBtn) {
            btnPos.setText("确定");
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
            btnPos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (showPosBtn && showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_right_selector);
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.bg_alert_dialog_left_selector);
            ivLine.setVisibility(View.VISIBLE);
        }
        if (showPosBtn && !showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
        }
        if (!showPosBtn && showNegBtn) {
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
        }
    }

    public ZARLTipsDialog show() {
        setLayout();
        dialog.show();
        return this;
    }


    public boolean isShowing() {
        if (null != dialog) {
            return dialog.isShowing();
        }
        return false;
    }


    /**
     * 隐藏
     *
     * @return
     */
    public ZARLTipsDialog cancel() {
        dialog.cancel();
        return this;
    }


    /**
     * 设置用户按压行为
     *
     * @param cancelableTouch
     * @param cancelable
     */
    public void cancelableTouch(boolean cancelableTouch, boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelableTouch);
        dialog.setCancelable(cancelable);// 不可以用“返回键”取消

    }
}
