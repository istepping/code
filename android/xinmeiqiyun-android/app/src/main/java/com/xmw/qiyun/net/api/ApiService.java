package com.xmw.qiyun.net.api;

import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.cargo.CargoDetailBean;
import com.xmw.qiyun.data.model.net.cargo.CargoListBean;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerBean;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerListBean;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.map.TruckMarkerListBean;
import com.xmw.qiyun.data.model.net.other.StatusBean;
import com.xmw.qiyun.data.model.net.other.VersionBean;
import com.xmw.qiyun.data.model.net.price.PriceListBean;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.publish.PublishBodyListBean;
import com.xmw.qiyun.data.model.net.publish.PublishDetailBean;
import com.xmw.qiyun.data.model.net.publish.PublishStreamListBean;
import com.xmw.qiyun.data.model.net.publish.RegionBody;
import com.xmw.qiyun.data.model.net.publish.RegionResultBean;
import com.xmw.qiyun.data.model.net.standard.AssemblyBean;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.GoodsTypeBean;
import com.xmw.qiyun.data.model.net.standard.GoodsUnitBean;
import com.xmw.qiyun.data.model.net.standard.PaymentMethodBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.RemarkBean;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.data.model.net.store.AliResultBean;
import com.xmw.qiyun.data.model.net.store.OrderSubmitBody;
import com.xmw.qiyun.data.model.net.store.WxResultBean;
import com.xmw.qiyun.data.model.net.store.PayResultBody;
import com.xmw.qiyun.data.model.net.truck.TruckDetailBean;
import com.xmw.qiyun.data.model.net.truck.TruckListBean;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.data.model.net.user.CodeBody;
import com.xmw.qiyun.data.model.net.user.ImageUploadBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBody;
import com.xmw.qiyun.data.model.net.user.UserInfoBean;
import com.xmw.qiyun.data.model.net.user.UserInfoBody;
import com.xmw.qiyun.data.model.net.user.UserInfoEditBean;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompanyBean;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac on 2017/7/26.
 */

public interface ApiService {

    //获取车长
    @GET("sysoption/getoption?OptionCode=VehicleLength")
    Observable<VehicleLengthBean> getVehicleLength();

    //获取车型
    @GET("sysoption/getoption?OptionCode=VehicleType")
    Observable<VehicleTypeBean> getVehicleType();

    //获取省
    @GET("sysoption/getoption?OptionCode=Province")
    Observable<ProvinceBean> getProvince();

    //获取市
    @GET("sysoption/getoption?OptionCode=City")
    Observable<CityBean> getCity();

    //获取区
    @GET("sysoption/getoption?OptionCode=County")
    Observable<CountyBean> getCounty();

    //获取货物类型
    @GET("sysoption/getoption?OptionCode=GoodsType")
    Observable<GoodsTypeBean> getGoodsType();

    //获取货物单位
    @GET("sysoption/getoption?OptionCode=GoodsUnit")
    Observable<GoodsUnitBean> getGoodsUnit();

    //获取装货方式
    @GET("sysoption/getoption?OptionCode=Assembly")
    Observable<AssemblyBean> getAssembly();

    //获取付款方式
    @GET("sysoption/getoption?OptionCode=PaymentMethod")
    Observable<PaymentMethodBean> getPaymentMethod();

    //获取其他备注
    @GET("sysoption/getoption?OptionCode=Remark")
    Observable<RemarkBean> getRemark();

    //获取图形验证码
    @GET("goodsuser/buildvalidationimage")
    Observable<ImageUploadBean> getImage(@Query("mobileCode") String mobileCode, @Query("sysCode") int sysCode);

    //获取注册验证码
    @POST("goodsuser/regverification")
    Observable<CommonResponse> getRegisterCode(@Body CodeBody codeBody);

    //获取登录验证码
    @POST("goodsuser/loginverification")
    Observable<CommonResponse> getLoginCode(@Body CodeBody codeBody);

    //注册
    @POST("goodsuser/register")
    Observable<LoginAndRegisterBean> register(@Body LoginAndRegisterBody loginAndRegisterBody);

    //登录
    @POST("goodsuser/login")
    Observable<LoginAndRegisterBean> login(@Body LoginAndRegisterBody loginAndRegisterBody);

    //重新登录
    @POST("goodsuser/relogin/{mobile}")
    Observable<LoginAndRegisterBean> reLogin(@Path("mobile") String mobile);

    //获取基本信息
    @GET("goodsownerinfo/getinfo/{id}")
    Observable<UserInfoBean> getUserInfo(@Path("id") String id);

    //修改基本信息
    @PUT("goodsownerinfo/editinfo/{id}")
    Observable<UserInfoEditBean> editUserInfo(@Path("id") String id, @Body UserInfoBody userInfoBody);

    //获取货主验证基本信息
    @GET("goodsauthenticationinfo/getauthenticationinfo")
    Observable<VerifyPersonalAndCompanyBean> getPersonalAndCompanyInfo();

    //个人信息修改
    @PUT("personalinfo/editinfo")
    Observable<CommonResponse> editPersonalInfo(@Body VerifyPersonalInfo verifyPersonalInfo);

    //公司信息修改
    @PUT("companyinfo/editinfo")
    Observable<CommonResponse> editCompanyInfo(@Body VerifyCompanyInfo verifyCompanyInfo);

    //申请认证
    @POST("goodsauthenticationinfo/applyauth")
    Observable<CommonResponse> appPersonalAndCompanyInfo();

    //车源列表
    @POST("vehicleowner/list")
    Observable<TruckListBean> getTruckList(@Query("page") int page, @Body TruckSearchBody truckSearchBody);

    //车源信息
    @GET("vehicleowner/getinfo/{id}")
    Observable<TruckDetailBean> getTruckDetail(@Path("id") String id);

    //车源定位
    @GET("vehicleposition/getposition")
    Observable<TruckMarkerListBean> getTruckMarkerList(@Query("Longitude") double longitude,
                                                       @Query("Latitude") double latitude,
                                                       @Query("VehicleType") String vehicleType,
                                                       @Query("VehicleLength") String vehicleLength);

    //获取货主状态
    @GET("goodsownerinfo/getstatus")
    Observable<StatusBean> getStatus();

    //计算里程数
    @POST("gaode/mileage")
    Observable<RegionResultBean> getMileage(@Body RegionBody regionBody);

    //发布货源
    @POST("goodssource/publish")
    Observable<CommonResponse> publishCargo(@Body PublishBody publishBody);

    //待审核货源列表
    @GET("goodssource/pendings")
    Observable<PublishStreamListBean> getPublishStreamByList(@Query("page") int page);

    //发布中货源列表
    @GET("goodssource/releaseings")
    Observable<PublishStreamListBean> getPublishStreamInList(@Query("page") int page);

    //已关闭货源列表
    @GET("goodssource/closeds")
    Observable<PublishStreamListBean> getPublishStreamStopList(@Query("page") int page);

    //重发货源
    @PUT("goodssource/repeat/{id}")
    Observable<CommonResponse> doResendCargo(@Path("id") String id);

    //关闭货源
    @PUT("goodssource/close/{id}")
    Observable<CommonResponse> doCloseCargo(@Path("id") String id);

    //删除货源
    @PUT("goodssource/remove/{id}")
    Observable<CommonResponse> doDeleteCargo(@Path("id") String id);

    //货源信息
    @GET("goodssource/goodsinfo/{id}")
    Observable<PublishDetailBean> getPublishDetail(@Path("id") String id);

    //常发货源
    @GET("oftengoodssource/list")
    Observable<PublishBodyListBean> getPublishRegularList(@Query("page") int page);

    //删除常发货源
    @DELETE("oftengoodssource/remove/{id}")
    Observable<CommonResponse> deletePublishRegular(@Path("id") String id);

    //货源列表
    @POST("goodssource/list")
    Observable<CargoListBean> getCargoList(@Query("page") int page, @Body CargoSearchBody cargoSearchBody);

    //货主列表
    @GET("goodsownerbusinesscard/list")
    Observable<CargoOwnerListBean> getCargoOwnerList(@Query("page") int page,
                                                     @Query("RegionType") int regionType,
                                                     @Query("RegionId") String regionId,
                                                     @Query("CompanyShortName") String companyShortName);

    //货主地图
    @GET("goodsownerbusinesscard/maplist")
    Observable<CargoOwnerListBean> getCargoOwnerMap(@Query("Longitude") double longitude, @Query("Latitude") double latitude, @Query("GoodsOwnerId") String id);

    //货主地图
    @GET("goodsownerbusinesscard/mustcities")
    Observable<CargoOwnerListBean> getCargoOwnerOriginMap(@Query("ExcludeCityCode") String excludeCityCode, @Query("GoodsOwnerId") String goodsOwnerId);

    //货源信息
    @GET("goodssource/goodsinfo/{id}")
    Observable<CargoDetailBean> getCargoDetail(@Path("id") String id);

    //货主信息
    @GET("goodssource/goodsownerinfo/{id}")
    Observable<CargoOwnerBean> getCargoMaster(@Path("id") String id);

    //创建Ali订单
    @POST("shop/order/createorder")
    Observable<AliResultBean> submitAliOrder(@Body OrderSubmitBody orderSubmitBody);

    //创建Wx订单
    @POST("shop/order/createorder")
    Observable<WxResultBean> submitWxOrder(@Body OrderSubmitBody orderSubmitBody);

    //查询支付结果
    @POST("shop/order/payresult")
    Observable<CommonResponse> getWxResult(@Body PayResultBody payResultBody);

    //汽运价格
    @GET("truckingprice/pricelist")
    Observable<PriceListBean> getPriceList(@Query("page") int page, @Query("type") String type);

    //版本检查
    @GET("appverson/goodsversonandorid")
    Observable<VersionBean> checkVersion();

    //上传图片
    @POST("filedata/upload")
    Observable<ImageUploadBean> upload(@Body MultipartBody multipartBody);
}
