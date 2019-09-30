package com.bs.android.customview.radiobutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.bs.android.R;


/**
 * Created by admin on 2017/12/21.
 */

public class MyRadioButton extends AppCompatRadioButton {
    //图片大小
    //private int drawableSize;

    private Context mContext = null;

    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setClickable(true);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyRadioButton);
        //drawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_rbDrawableTopSize, 50);
        Drawable drawableTop = a.getDrawable(R.styleable.MyRadioButton_rbDrawableTop);

        //释放资源
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
    }


    public void setDrable(Drawable res) {

        setCompoundDrawablesWithIntrinsicBounds(null, res, null, null);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (top != null) {
            //这里只要改后面两个参数就好了，一个宽一个是高，如果想知道为什么可以查找源码
            int size = getResources().getDimensionPixelOffset(R.dimen.my20dp);
            top.setBounds(0, 0, size, size);
            //top.setBounds(0,0,72,72);
        }
        setCompoundDrawables(left, top, right, bottom);
    }


    /**
     * 设置图片选择器
     */
    public void setRbDrawableTop(Drawable top) {
        setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
    }


}
