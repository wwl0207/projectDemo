package com.bs.android.activity.bsui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.model.SearchHistoryModel;
import com.bs.android.utils.ButtonUtils;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by WWL on 2019/9/10 0010:09
 * 自定义BUtton
 */
public class BSULCustomButtonActivity extends BaseActivity {
    @BindView(R.id.btn_layout)
    LinearLayout mBtnLayout;
    ArrayList<String> mList = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_button_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("动态添加按钮");
        for (int i = 0; i < 4; i++) {
            if (i == 0)
                mList.add("第一条");
            else if (i == 1)
                mList.add("第二条");
            else if (i == 2)
                mList.add("第三条");
            else
                mList.add("第四条");
        }
    }

    @Override
    protected void initHttpData() {
        mBtnLayout.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            addTextView(mBtnLayout, mList.get(i));
        }
    }


    /**
     * 动态添加按钮
     *
     * @param linearLayout
     * @param operationsBean
     */
    private void addTextView(LinearLayout linearLayout, String operationsBean) {
        TextView textView = new TextView(this);
        textView.setText(operationsBean);
        textView.setTextColor(Color.parseColor("#ff0000"));

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setStroke(1, Color.parseColor("#ff0000"));
//        gd.setColor(Color.parseColor(operationsBean.getBgcolor()));
        gd.setCornerRadius(CommentUtil.dpToPx(25));
        textView.setBackground(gd);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, (int) CommentUtil.dpToPx(10), 0);
        textView.setLayoutParams(layoutParams);
        textView.setPadding((int) CommentUtil.dpToPx(12), (int) CommentUtil.dpToPx(5), (int) CommentUtil.dpToPx(12), (int) CommentUtil.dpToPx(5));
        linearLayout.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick()) {
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
