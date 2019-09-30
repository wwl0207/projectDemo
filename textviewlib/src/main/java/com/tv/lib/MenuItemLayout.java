package com.tv.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * created by WWL on 2019/9/7 0007:14
 */
public class MenuItemLayout extends FrameLayout {

    private Context mContext;
    private View mView;
    private TextView titleTv;
    private EditText contentTv;
    private ImageView iconImg;
    private ImageView redContentImg;
    private ImageView rightimg;
    private OnClickListener onClickListener;
    private String titleText;
    private String contentText;
    private String iconImgUri;
    private String jumpUrl;
    private int iconImgId;
    private int contentImgId;
    private String onclickId;
    public static final int NO_LINE = 0;
    public static final int DIVIDE_LINE = 1;
    public static final int DIVIDE_AREA = 2;
    public int divideLineStyle = NO_LINE;
    private boolean isShowTitleImg = false;
    private boolean isShowRedHintImg = false;
    private boolean isShowRightImg = true;
    private boolean isShowContentTv = true;

    public int getIconImgId() {
        return iconImgId;
    }

    /**设置图标**/
    public MenuItemLayout setTitleImgId(int iconImgId) {
        if (iconImgId != 10000) {
            this.iconImgId = iconImgId;
            iconImg.setImageResource(iconImgId);
        }
        return this;
    }
    /**设置标题权重
     * 它是在ContentTV为GONE的情况下才是用，其他情况不使用
     * **/
    public MenuItemLayout setTitleWeight(boolean isWeight){
        if (isWeight)
            titleTv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));
        return this;
    }

    /**
     * 标题离左边图片的间距
     * 本来已经设置了，这个主要是当setTitleWeight设置之后一起使用的，其他情况一般不适用
     * **/
    public MenuItemLayout setTitleMargPaddingLeft(int padding){
        titleTv.setPadding(padding,0,0,0);
        return this;
    }

    /**
     * 设置内容右边的图标
     * @param contentImgId
     */
    public MenuItemLayout setContentImgId(int contentImgId) {
        if (contentImgId != 10000) {
            this.contentImgId = contentImgId;
            redContentImg.setImageResource(contentImgId);
        }
        return this;
    }

    public String getTitleText() {
        return titleText;
    }

    /**
     * 设置标题名称
     * @param titleText
     */
    public MenuItemLayout setTitleText(String titleText) {
        if (titleText != null) {
            this.titleText = titleText;
            titleTv.setText(titleText);
        }
        return this;
    }

    public MenuItemLayout setContentLength(int length) {
        contentTv.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(length)
        });
        return this;
    }


    /**
     * 标题字体大小
     * @param size
     */
    public MenuItemLayout setTitleSize(float size) {
        if (size > 0) {
            titleTv.setTextSize(size);
        }
        return this;
    }

    /**
     * 标题字体颜色
     * @param color
     */
    public MenuItemLayout setTitleColor(int color) {
        if (color > 0) {
            titleTv.setTextColor(mContext.getResources().getColor(color));
        }
        return this;
    }

    public String getContentText() {
        return contentText;
    }

    /**
     * 内容
     * @param contentText
     */
    public MenuItemLayout setContentText(String contentText) {
        if (contentText != null) {
            this.contentText = contentText;
            contentTv.setText(contentText);
        }
        return this;
    }

    /**
     * 提示语
     * @param hintText
     * @return
     */
    public MenuItemLayout setContentHintText(String hintText){
        if (hintText!=null){
            contentTv.setHint(hintText);
        }
        return this;
    }

    /**
     * 设置内容字体大小
     * @param size
     */
    public MenuItemLayout setContentSize(float size) {
        if (size > 0) {
            contentTv.setTextSize(size);
        }
        return this;
    }

    /**
     * 设置内容字体颜色
     * @param color
     */
    public MenuItemLayout setContentColor(int color) {
        if (color > 0) {
            contentTv.setTextColor(mContext.getResources().getColor(color));
        }
        return this;
    }

    /**
     * 设置内容位置 对齐方式
     * @param gray
     */
    public MenuItemLayout setContextGray(int gray) {
        switch (gray){
            case 1:
                contentTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                break;
            case 2:
                contentTv.setGravity(Gravity.CENTER);
                break;
            case 3:
                contentTv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                break;
        }

        return this;
    }

    /**
     * 设置内容是否可编辑状态
     * @param eable
     */
    public MenuItemLayout setContentEable(boolean eable) {
        contentTv.setEnabled(eable);
        return this;
    }

    /**
     * 设置内容的行数，一般只有一行
     * 多行自行处理
     * @param lines
     */
    public MenuItemLayout setContentLines(int lines){
//        if (lines==1){
//            contentTv.setSingleLine(true);
//            contentTv.setLines(1);
//        }else {
        contentTv.setLines(lines);
        contentTv.setMaxLines(lines);
//        }
        return this;
    }

    /**
     * 是否加粗
     *
     * @param bold
     */
    public MenuItemLayout setContentBold(boolean bold) {
        if (bold) {
            TextPaint paint = contentTv.getPaint();
            paint.setFakeBoldText(true);
        }
        return this;
    }



    /**
     * 软键盘类型  不知道怎么回事，只要调用了这个方法，行数设置就不起作用了，一直都是单行，不能换行了
     */
    public MenuItemLayout setContentKeyboardType(int type){
        switch (type) {
            case 1:
                contentTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                break;
            case 2://电话
                contentTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                break;
            case 3://数字类型
                contentTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                break;
            case 4://英文
                contentTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 5://密码
                contentTv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
        return this;
    }

    public boolean isShowTitleImg() {
        return isShowTitleImg;
    }

    /**
     * 是否显示标题左侧图标
     * @param showTitleImg
     */
    public MenuItemLayout setShowTitleImg(boolean showTitleImg) {
        isShowTitleImg = showTitleImg;
        iconImg.setVisibility(showTitleImg ? VISIBLE : GONE);
        return this;
    }

    /**
     * 是否显示右侧箭头图标
     * @param showRigthImg
     */
    public MenuItemLayout setShowRightImg(boolean showRigthImg) {
        isShowRightImg = showRigthImg;
        rightimg.setVisibility(showRigthImg ? VISIBLE : GONE);
        return this;
    }

    /**
     * 是否显示内容
     * @param showcontenttv
     */
    public MenuItemLayout setShowContentTv(boolean showcontenttv) {
        isShowContentTv = showcontenttv;
        contentTv.setVisibility(showcontenttv ? VISIBLE : GONE);
        return this;
    }

    public boolean isShowContentImg() {
        return isShowRedHintImg;
    }

    /**
     * 是否显示内容右侧图标
     * @param showRedHintImg
     */
    public MenuItemLayout setShowContentImg(boolean showRedHintImg) {
        isShowRedHintImg = showRedHintImg;
        redContentImg.setVisibility(showRedHintImg ? VISIBLE : GONE);
        return this;
    }

    public MenuItemLayout setContentPaddingLeft(int paddding) {
        contentTv.setPadding(paddding,0,0,0);
        return this;
    }
    public MenuItemLayout setContentPaddingRight(int paddding) {
        contentTv.setPadding(0,0,paddding,0);
        return this;
    }



    public String getJumpUrl() {
        return jumpUrl;
    }

    public MenuItemLayout setJumpUrl(String jumpUrl) {
        if (jumpUrl != null) {
            this.jumpUrl = jumpUrl;
        }
        return this;
    }

    public String getOnclickId() {
        return onclickId;
    }

    public void setOnclickId(String onclickId) {
        this.onclickId = onclickId;
    }

    public MenuItemLayout(Context context) {
        this(context, null);
    }

    public MenuItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.item_menu_layout, this, true);
        titleTv = (TextView) mView.findViewById(R.id.title_text);
        contentTv = (EditText) mView.findViewById(R.id.content_text);
        iconImg = (ImageView) mView.findViewById(R.id.title_img);
        redContentImg = (ImageView) mView.findViewById(R.id.content_img);
        rightimg = (ImageView) mView.findViewById(R.id.rigth_img);

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MenuItemLayout);
        setTitleText(a.getString(R.styleable.MenuItemLayout_title_text));//标题名称
        setContentText(a.getString(R.styleable.MenuItemLayout_content_text));//内容
        setTitleImgId(a.getResourceId(R.styleable.MenuItemLayout_icon_reference, 10000));//标题图标
        setContentImgId(a.getResourceId(R.styleable.MenuItemLayout_icon_reference, 10000));//内容图标
        setTitleColor(a.getResourceId(R.styleable.MenuItemLayout_title_color, -1));//标题颜色
        setContentColor(a.getResourceId(R.styleable.MenuItemLayout_content_color, -1));//内容颜色
        setTitleSize(a.getDimensionPixelSize(R.styleable.MenuItemLayout_title_size,15));//标题字体大小
        setContentSize(a.getDimensionPixelSize(R.styleable.MenuItemLayout_content_size,15));//内容字体大小
        setShowTitleImg(a.getBoolean(R.styleable.MenuItemLayout_show_title_icon,true));//标题图标是否可见
        setShowContentImg(a.getBoolean(R.styleable.MenuItemLayout_show_content_icon,false));//内容右侧图标是否可见
        setShowContentTv(a.getBoolean(R.styleable.MenuItemLayout_show_content_tv,true));//内容是否可见
        setShowRightImg(a.getBoolean(R.styleable.MenuItemLayout_show_right_icon,true));//右侧图标是否可见
        setContentEable(a.getBoolean(R.styleable.MenuItemLayout_content_eable,false));//内容是否可编辑
        setJumpUrl(a.getString(R.styleable.MenuItemLayout_jump_url));
        setContextGray(a.getResourceId(R.styleable.MenuItemLayout_content_gray,1));//位置
        setContentHintText(a.getString(R.styleable.MenuItemLayout_hint_text));//提示语
        setContentKeyboardType(a.getInt(R.styleable.MenuItemLayout_content_inputType,1));//键盘类型
        setContentLength(a.getInt(R.styleable.MenuItemLayout_maxlength,100));//最大长度设置
        setContentBold(a.getBoolean(R.styleable.MenuItemLayout_content_isbold,false));//是否加粗
        setContentPaddingLeft(a.getDimensionPixelSize(R.styleable.MenuItemLayout_conent_paddingleft,0));//左间距设置
        setContentPaddingRight(a.getDimensionPixelSize(R.styleable.MenuItemLayout_conent_paddingright,0));//右间距
        divideLineStyle = a.getInt(R.styleable.MenuItemLayout_divide_line_style, NO_LINE);
        setDivideLine(divideLineStyle);
    }

    /**
     * 设置列表间距
     * @param bootomLineStyle 1表示一个间距  2表示10个间距  暂时只设置了这两个行间距
     */
    public MenuItemLayout setDivideLine(int bootomLineStyle) {
        View lineView = findViewById(R.id.divide_line_view);
        View areaView = findViewById(R.id.divide_area_view);
        lineView.setVisibility(GONE);
        areaView.setVisibility(GONE);
        if (bootomLineStyle == DIVIDE_LINE) {
            lineView.setVisibility(VISIBLE);
        } else if (bootomLineStyle == DIVIDE_AREA) {
            areaView.setVisibility(VISIBLE);
        }
        return this;
    }

    public void setViewOnlickListener(OnClickListener onlickListener) {
        this.onClickListener = onlickListener;
        mView.setOnClickListener(onlickListener);
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public EditText getContentTv() {
        return contentTv;
    }
}
