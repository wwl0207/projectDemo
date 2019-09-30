package com.bs.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bs.android.R;
import com.bs.android.activity.LoginActivity;
import com.bs.android.activity.MarketLocationActivity;
import com.bs.android.activity.RegisterActivity;
import com.bs.android.activity.TextListActivity;
import com.bs.android.activity.bsui.BSULDemoActivity;
import com.bspopupwindow.BSPopupWindowActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class TabFourFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.headImage)
    CircleImageView headImage;
    @BindView(R.id.logout_btn)
    Button logoutBtn;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.tab_four_fragment;
    }

    @Override
    protected void initView(View view) {
        hideTitleBar();

    }

    @Override
    protected void initHttpData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.headImage, R.id.logout_btn, R.id.thread_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.thread_btn:
                startActivity(BSULDemoActivity.class);
                break;
            case R.id.headImage:
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.putExtra("isfrom", "isfrom");
                startActivity(intent);
                break;
            case R.id.logout_btn:
                startActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

}
