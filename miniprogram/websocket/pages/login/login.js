// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    avatarUrl:"",
    nickName:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
     var that=this
     wx.login({
       success:function(res){
         if(res.code)
         {
           console.log('用户登陆成功')
           wx.getUserInfo({
             success:function(res)
             {
               var userInfo=res.userInfo
               var _nickName=userInfo.nickName
               var _avatarUrl=userInfo.avatarUrl
               var gender=userInfo.gender
               var provience=userInfo.province
               var city=userInfo.city
               var country=userInfo.country
               that.setData({
                 avatarUrl:_avatarUrl,
                 nickName:_nickName
               })
               console.log('成功获取用户信息',res);
             }
           })

         }
         else
         {
           console.log('获取用户登陆失败'+res.errMsg)
         }
       }
     })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return{
      title:'第8章',
      desc:'fenx',
      path:'pages/login/login'
    }
  }
})