package com.xmw.qiyun.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.model.event.MeEvent;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.net.user.UserInfoBean;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/8/2.
 */

@Route(RouterUtil.ROUTER_ME)
public class MeFragment extends BaseFragment implements MeContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.me_avatar)
    ImageView mAvatar;
    @BindView(R.id.me_icon)
    ImageView mIcon;
    @BindView(R.id.me_name)
    TextView mName;
    @BindView(R.id.me_time)
    TextView mTime;

    MeContract.Presenter mPresenter;

    @Override
    public void setPresenter(MeContract.Presenter presenter) {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter = new MePresentImpl(this.getContext(), this);

        init();
    }

    @Override
    public void init() {
        mTitle.setText(getString(R.string.tab_me));
        mPresenter.getUserInfo();
    }

    @Override
    public void getData(UserInfoBean userInfoBean) {

        ImageUtil.loadAvatar(getContext(), mAvatar, userInfoBean.getData().getHeadPortraitId(), R.drawable.default_avatar);

        switch (userInfoBean.getData().getStatus()) {

            default:
                break;

            case 1: {

                mIcon.setImageResource(R.drawable.verify_type_1);
            }
            break;

            case 2: {

                mIcon.setImageResource(R.drawable.verify_type_2);
            }
            break;

            case 4: {

                mIcon.setImageResource(R.drawable.verify_type_4);
            }
            break;
        }

        mName.setText(userInfoBean.getData().getName());
        mTime.setText(userInfoBean.getData().getRegisterTime());
    }

    @OnClick({
            R.id.me_verify,
            R.id.me_approve,
            R.id.me_store,
            R.id.me_setting
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.me_verify: {

                mPresenter.goVerify();
            }
            break;

            case R.id.me_approve: {

                mPresenter.goInformation();
            }
            break;

            case R.id.me_store:{

                mPresenter.goStore();
            }
            break;

            case R.id.me_setting: {

                mPresenter.goSetting();
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeEvent(MeEvent meEvent) {

        mPresenter.getUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {

        mPresenter.getUserInfo();
    }
}
