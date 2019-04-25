package com.xmw.qiyun.util.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.xmw.qiyun.App;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.event.ReLoginUpdateEvent;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.standard.GoodsTypeBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.common.OptionTopAdapter;
import com.xmw.qiyun.ui.information.InformationContract;
import com.xmw.qiyun.ui.publish.PublishContract;
import com.xmw.qiyun.ui.publish.PublishFragment;
import com.xmw.qiyun.ui.verify.verifyCompany.VerifyCompanyActivity;
import com.xmw.qiyun.ui.verify.verifyCompany.VerifyCompanyContract;
import com.xmw.qiyun.ui.verify.verifyPersonal.VerifyPersonalActivity;
import com.xmw.qiyun.ui.verify.verifyPersonal.VerifyPersonalContract;
import com.xmw.qiyun.util.manage.AppUtil;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by hongyuan on 2017/8/29.
 */

public enum DialogUtil {

    INSTANCE;

    //头像对话框
    public void initPhotoDialog(final Context context, final InformationContract.Presenter informationPresenter) {

        final AlertDialog build = new AlertDialog.Builder(context).create();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_photo_dialog, null);
        build.setView(view, 0, 0, 0, 0);
        build.show();

        WindowManager.LayoutParams params = build.getWindow().getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        params.gravity = Gravity.BOTTOM;
        build.getWindow().setAttributes(params);

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                informationPresenter.selectImage();

                build.dismiss();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                build.dismiss();
            }
        });
    }

    //认证对话框
    public void initVerifyDialog(final Context context,
                                 final int type,
                                 final VerifyPersonalContract.Presenter personalPresenter,
                                 final VerifyCompanyContract.Presenter companyPresenter) {

        final AlertDialog build = new AlertDialog.Builder(context).create();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_verify_dialog, null);
        build.setView(view, 0, 0, 0, 0);
        build.show();

        WindowManager.LayoutParams params = build.getWindow().getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        params.gravity = Gravity.BOTTOM;
        build.getWindow().setAttributes(params);

        TextView title = view.findViewById(R.id.title);
        View auth = view.findViewById(R.id.auth);
        View image = view.findViewById(R.id.image);
        View card = view.findViewById(R.id.card);
        View license = view.findViewById(R.id.license);
        View shop = view.findViewById(R.id.shop);
        View ok = view.findViewById(R.id.ok);

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                build.dismiss();
            }
        });

        switch (type) {

            default:
                break;

            case 0: {

                title.setText(context.getString(R.string.verify_personal_image_toast_1));
                auth.setVisibility(View.VISIBLE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        personalPresenter.selectImage(VerifyPersonalActivity.REQUEST_IMAGE);

                        build.dismiss();
                    }
                });
            }
            break;

            case 1: {

                title.setText(context.getString(R.string.verify_personal_image_toast_1));
                image.setVisibility(View.VISIBLE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        personalPresenter.selectImage(VerifyPersonalActivity.REQUEST_IMAGE);

                        build.dismiss();
                    }
                });
            }
            break;

            case 2: {

                title.setText(context.getString(R.string.verify_personal_image_toast_2));
                card.setVisibility(View.VISIBLE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        personalPresenter.selectImage(VerifyPersonalActivity.REQUEST_IMAGE);

                        build.dismiss();
                    }
                });
            }
            break;

            case 3: {

                title.setText(context.getString(R.string.verify_company_image_toast_1));
                license.setVisibility(View.VISIBLE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        companyPresenter.selectImage(VerifyCompanyActivity.REQUEST_IMAGE);

                        build.dismiss();
                    }
                });
            }
            break;

            case 4: {

                title.setText(context.getString(R.string.verify_company_image_toast_2));
                shop.setVisibility(View.VISIBLE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        companyPresenter.selectImage(VerifyCompanyActivity.REQUEST_IMAGE);

                        build.dismiss();
                    }
                });
            }
            break;
        }
    }

    //拨打电话对话框
    public void initPhoneDialog(final Context context, final String phoneNumber) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(context);

        mMaterialDialog
                .setTitle(context.getString(R.string.dialog_phone_title))
                .setMessage(context.getString(R.string.dialog_phone_message) + phoneNumber)
                .setCanceledOnTouchOutside(true)
                .setPositiveButton(context.getString(R.string.dialog_phone_positive), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();

                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(context.getString(R.string.dialog_phone_negative), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();
                    }
                })
                .show();
    }

    //重新登陆对话框
    public void initLoginDialog(final Context context) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(context);

        mMaterialDialog
                .setTitle(context.getString(R.string.dialog_login_title))
                .setMessage(context.getString(R.string.dialog_login_message))
                .setPositiveButton(context.getString(R.string.dialog_login_positive), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();

                        UserManager.logout();

                        API.getService().reLogin(UserManager.getPhone()).subscribe(new MySubscriber<LoginAndRegisterBean>() {
                            @Override
                            public void onNext(LoginAndRegisterBean loginAndRegisterBean) {

                                if (loginAndRegisterBean.getStatusCode() == 1) {

                                    UserManager.login(loginAndRegisterBean.getData());

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            EventBus.getDefault().post(new RefreshEvent());
                                            EventBus.getDefault().post(new ReLoginUpdateEvent());

                                        }
                                    }, 1000);

                                } else {

                                    AppUtil.INSTANCE.finishAllActivity();

                                    RouterUtil.go(RouterUtil.ROUTER_LOGIN, App.getInstance());
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(context.getString(R.string.dialog_login_negative), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();

                        UserManager.logout();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                AppUtil.INSTANCE.finishAllActivity();

                                RouterUtil.go(RouterUtil.ROUTER_LOGIN, App.getInstance());

                            }
                        }, 1000);
                    }
                })
                .show();
    }

    //退出程序对话框
    public void initLogoutDialog(final Context context) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(context);

        mMaterialDialog
                .setTitle(context.getString(R.string.dialog_logout_title))
                .setMessage(context.getString(R.string.dialog_logout_message))
                .setCanceledOnTouchOutside(true)
                .setPositiveButton(context.getString(R.string.dialog_logout_positive), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();

                        AppUtil.INSTANCE.appExit();
                    }
                })
                .setNegativeButton(context.getString(R.string.dialog_logout_negative), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mMaterialDialog.dismiss();
                    }
                })
                .show();
    }

    //添加自定义标签对话框
    public void initAddDialog(final Context context, final PublishContract.Presenter presenter, final PublishBody publishBody, final int type) {

        final List<Standard> vehicleLengthList = new ArrayList<>();
        final List<String> vehicleLengthStringList = new ArrayList<>();

        vehicleLengthList.addAll(GsonImpl.init().toObject(StandardManager.getVehicleLength(), VehicleLengthBean.class).getData());

        for (Standard standard : vehicleLengthList) {

            vehicleLengthStringList.add(standard.getValue());
        }

        List<Standard> goodsTypeList = new ArrayList<>();
        final List<String> goodsTypeStringList = new ArrayList<>();

        goodsTypeList.addAll(GsonImpl.init().toObject(StandardManager.getGoodsType(), GoodsTypeBean.class).getData());

        for (Standard standard : goodsTypeList) {

            goodsTypeStringList.add(standard.getValue());
        }

        final AlertDialog build = new AlertDialog.Builder(context).create();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_dialog, null);
        build.setView(view, 0, 0, 0, 0);
        build.show();

        TextView title = view.findViewById(R.id.dialog_title);
        TextView limit = view.findViewById(R.id.dialog_limit);
        final EditText input = view.findViewById(R.id.dialog_input);
        TextView add = view.findViewById(R.id.dialog_add);
        TextView tip = view.findViewById(R.id.dialog_tip);
        final GridView list = view.findViewById(R.id.dialog_list);
        TextView close = view.findViewById(R.id.dialog_close);

        switch (type) {

            default:
                break;

            case 0: {

                title.setText(context.getString(R.string.dialog_vehicle_title));
                limit.setText(context.getString(R.string.dialog_vehicle_limit));
                tip.setText(context.getString(R.string.dialog_vehicle_tip));

                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

                final List<String> vehicleList = new ArrayList<>();

                if (!CommonUtil.isNullOrEmpty(UserManager.getVehicle())) {

                    vehicleList.addAll(GsonImpl.init().toList(UserManager.getVehicle(), String.class));
                }

                list.setAdapter(new OptionTopAdapter(context, 0, list, vehicleList));

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (input.getText().length() == 0) {

                            NoteUtil.showToast(context, context.getString(R.string.toast_empty));

                            return;
                        }

                        if (CommonUtil.checkInputInvalid(input.getText().toString())) {

                            NoteUtil.showToast(context, context.getString(R.string.toast_invalid));

                            return;
                        }

                        if (list.getAdapter().getCount() == 5) {

                            NoteUtil.showToast(context, context.getString(R.string.toast_up));

                            return;
                        }

                        String text;
                        String value;


                        if (input.getText().toString().contains(".")) {

                            text = input.getText().toString().substring(0, input.getText().toString().indexOf("."));
                            value = input.getText().toString().substring(0, input.getText().toString().indexOf(".") + 2);

                            if (Integer.parseInt(text) > 19) {

                                NoteUtil.showToast(context, context.getString(R.string.dialog_vehicle_limit));

                                return;
                            }

                        } else {

                            text = input.getText().toString();
                            value = text;

                            if (Integer.parseInt(text) > 20) {

                                NoteUtil.showToast(context, context.getString(R.string.dialog_vehicle_limit));

                                return;
                            }
                        }

                        if (((OptionTopAdapter) list.getAdapter()).getData().contains(value + "米") | vehicleLengthStringList.contains(value)) {

                            NoteUtil.showToast(context, context.getString(R.string.dialog_vehicle_invalid));

                            return;
                        }

                        vehicleList.clear();
                        vehicleList.addAll(((OptionTopAdapter) list.getAdapter()).getData());
                        vehicleList.add(value + "米");

                        list.setAdapter(new OptionTopAdapter(context, 0, list, vehicleList));

                        UserManager.saveVehicle(GsonImpl.init().toJson(vehicleList));

                        input.setText("");
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        build.dismiss();

                        final List<String> lengthList = new ArrayList<>();
                        final List<String> lengthSelectList = new ArrayList<>();

                        if (!CommonUtil.isNullOrEmpty(publishBody.getVehicleLength())) {

                            if (publishBody.getVehicleLength().contains(",")) {

                                String[] args = publishBody.getVehicleLength().split(",");

                                for (String string : args) {

                                    lengthList.add(string);
                                    lengthSelectList.add(string);
                                }

                            } else {

                                lengthList.add(publishBody.getVehicleLength());
                            }
                        }

                        for (String string : lengthSelectList) {

                            if (!GsonImpl.init().toList(UserManager.getVehicle(), String.class).contains(string)
                                    & !vehicleLengthStringList.contains(string)
                                    & !("零担").equals(string)) {

                                lengthList.remove(string);
                            }
                        }

                        if (lengthList.size() == 0) {

                            publishBody.setVehicleLength("");

                        } else if (lengthList.size() == 1) {

                            publishBody.setVehicleLength(lengthList.get(0));

                        } else {

                            String string = "";

                            for (int i = 0; i < lengthList.size(); i++) {

                                string = string + (i == 0 ? lengthList.get(i) : "," + lengthList.get(i));
                            }

                            publishBody.setVehicleLength(string);
                        }

                        presenter.getOptionMultiData(publishBody, PublishFragment.EXTRA_OPTION_VEHICLE);
                    }
                });
            }
            break;

            case 1: {

                title.setText(context.getString(R.string.dialog_type_title));
                limit.setText(context.getString(R.string.dialog_type_limit));
                tip.setText(context.getString(R.string.dialog_type_tip));

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

                final List<String> typeList = new ArrayList<>();

                if (!CommonUtil.isNullOrEmpty(UserManager.getType())) {

                    typeList.addAll(GsonImpl.init().toList(UserManager.getType(), String.class));
                }

                list.setAdapter(new OptionTopAdapter(context, 1, list, typeList));

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (input.length() == 0) {

                            NoteUtil.showToast(context, context.getString(R.string.dialog_input_invalid));

                            return;
                        }

                        if (list.getAdapter().getCount() == 5) {

                            NoteUtil.showToast(context, context.getString(R.string.toast_up));

                            return;
                        }

                        if (((OptionTopAdapter) list.getAdapter()).getData().contains(input.getText().toString()) | goodsTypeStringList.contains(input.getText().toString())) {

                            NoteUtil.showToast(context, context.getString(R.string.dialog_type_invalid));

                            return;
                        }

                        typeList.clear();
                        typeList.addAll(((OptionTopAdapter) list.getAdapter()).getData());
                        typeList.add(input.getText().toString());

                        list.setAdapter(new OptionTopAdapter(context, 0, list, typeList));

                        UserManager.saveType(GsonImpl.init().toJson(typeList));

                        input.setText("");
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        build.dismiss();

                        presenter.getOptionSingleData(publishBody, PublishFragment.EXTRA_OPTION_GOODS_TYPE);
                    }
                });
            }
            break;
        }
    }
}
