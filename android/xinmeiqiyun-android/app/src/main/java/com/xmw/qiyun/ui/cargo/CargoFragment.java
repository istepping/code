package com.xmw.qiyun.ui.cargo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.kyleduo.switchbutton.SwitchButton;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.ui.adapter.cargo.CargoPagerAdapter;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListFragment;
import com.xmw.qiyun.ui.cargo.cargoOwnerList.CargoOwnerListFragment;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/11/1.
 */

@Route(RouterUtil.ROUTER_CARGO)
public class CargoFragment extends BaseFragment implements CargoContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.title_switch)
    SwitchButton mTitleSwitch;
    @BindView(R.id.title_button)
    View mTitleButton;
    @BindView(R.id.cargo_pager)
    ViewPager mCargoPager;

    CargoContract.Presenter mPresenter;

    private boolean mIsShowCargoList = true;

    @Override
    public void setPresenter(CargoContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cargo, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        init();
    }

    @Override
    public void init() {

        mPresenter = new CargoPresentImpl(getContext());

        initTitleBar(mIsShowCargoList);
        //fragment管理货主货源切换
        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new CargoListFragment());
        fragmentList.add(new CargoOwnerListFragment());

        mCargoPager.setAdapter(new CargoPagerAdapter(this.getChildFragmentManager(), fragmentList));

        //滑动容器滑动监听
        mCargoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mIsShowCargoList = position == 0;

                initTitleBar(mIsShowCargoList);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //切换按钮滑动监听
        mTitleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                initTitleBar(!b);
                mCargoPager.setCurrentItem(!b ? 0 : 1);
            }
        });
    }

    @Override
    public void initTitleBar(boolean isCargoList) {

        mTitle.setText(isCargoList ? getString(R.string.cargo_title) : getString(R.string.cargo_owner_title));
        mTitleButton.setVisibility(isCargoList? View.INVISIBLE : View.VISIBLE);
        mTitleSwitch.setChecked(!isCargoList);
    }

    @OnClick({
            R.id.title_button
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.title_button: {

                mPresenter.goMap();
            }
            break;
        }
    }
}
