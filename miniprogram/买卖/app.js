App({

  /**
   * 当小程序初始化完成时，会触发 onLaunch（全局只触发一次）
   */
  login:function(){
    var that=this;
    wx.login({
      success: function(res) {
        console.log('login seccess');
        // console.log(res);
        that.globalData.rd_session=res.code;
        wx.setStorageSync('rd_session', that.globalData.rd_session)
        console.log('rd_session',that.globalData.rd_session)
        that.globalData.hasLogin=true;
        console.log('hasLogin true');
      },
      fail: function(res) {},
      complete: function(res) {},
    })
  },
  getUserInfo:function()
  {
    console.log('获取用户信息')
    var that=this;
    wx.getUserInfo({
      success:res=>{
        that.globalData.userInfo=res.userInfo;
        that.globalData.hasUserInfo=true;
        console.log("hasUserInfo true");
        console.log(res.userInfo)
      }
    })
  },
  onLaunch: function () {
    // console.log('App onLaunch');
    var that=this;
    var rd_session=wx.getStorageSync('rd_session')|| []
    console.log('rd_session',rd_session);
    //未登陆
    if(!rd_session)
    {
      console.log('第一次登陆')
      that.login();
      that.getUserInfo();
    }
    else{
      console.log('不是第一次登陆')
      wx.checkSession({
        success:function()
        {
          console.log('checkSession seccess');
          that.globalData.rd_session=rd_session;
          that.getUserInfo();
        },
        fail:function()
        {
          console.log('登陆过期')
          that.login();
          that.getUserInfo();
        }
      })
    }
  },
  globalData:{
    hasLogin:false,
    hasUserInfo:false,
    rd_session:"",
    userInfo:null,
    goods:[
      {
        id:1,
        img:'https://fuss10.elemecdn.com/e/86/6556774d6a3e34a5fc700ec213a13jpeg.jpeg?imageMogr/format/webp/thumbnail/!218x218r/gravity/Center/crop/218x218/',
        name:'低卡减脂轻食套餐鸡肉',
        RMB:14,
        distance:1.5,
        sales:102,
        score:78,
        info:'套餐里面都配酱料'
      },
      {
        id: 2,
        img: 'https://fuss10.elemecdn.com/2/cd/fa9b19818d2cedebbe54fd65b0c02jpeg.jpeg?imageMogr/format/webp/thumbnail/!218x218r/gravity/Center/crop/218x218/',
        name: '香辣鸡腿堡+香辣鸡翅+可乐',
        RMB:28,
        distance: 1.2,
        sales:821,
        score:85,
        info: '限购一个'
      }
    ]
  },
  /**
   * 当小程序启动，或从后台进入前台显示，会触发 onShow
   */
  onShow: function (options) {
    // console.log('App onShow');
  },

  /**
   * 当小程序从前台进入后台，会触发 onHide
   */
  onHide: function () {
    // console.log('App onHide');
  },

  /**
   * 当小程序发生脚本错误，或者 api 调用失败时，会触发 onError 并带上错误信息
   */
  onError: function (msg) {
    // console.log('App onError');
  }
})
