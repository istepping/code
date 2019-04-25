// view/video/video.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
        inputText:'',    videoSrc:"http://221.206.120.67/v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4?wsrid_tag=5aa543e3_PShljmdjwt3ci62_9834-55794&wsiphost=local",
      textList:[
        {
          text:'小程序',
          color:'#ff0000',
          time:3
        },
        {
          text:'小程序',
          color:'#ff00ff',
          time:6
        },
        {
          text:'小程序',
          color:'ffffff',
          time:6
        },
        {
          text:'小程序',
          color:'#ff00ff',
          time:10
        }
      ]
  },
  submitFun:function(e)
  {
     var danmuText=e.detail.value.danmuText
     this.theVideo.sendDanmu({
       text:danmuText,
       color:'#ffffff'
     })
     this.setData({
       inputText:''
     })
  },
  getLocalVideoFun:function(e)
  {
    var that=this
    wx.chooseVideo({
      sourceType:['album','camera'],
      maxDuration:60,
      camera:['front','back'],
      success:function(res)
      {
        that.setData({
          videoSrc:res.tempFilePath
        })
      }
    })
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
     this.theVideo=wx.createVideoContext('theVideo')
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
  
  }
})