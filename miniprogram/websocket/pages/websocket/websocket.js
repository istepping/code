Page({

  isSocketOpened:false,
  url:'ws://localhost:8010',
  tempVoiceFilePath:'',
  /**
   * 页面的初始数据
   */
  data: {
    imgSrc:'',
    isShowImage:false,
    videoSrc:'',
    isShowVideo:false
  },

  createSocketConnect:function(){
    var that=this
    wx.connectSocket({
      url: this.url,
      complete:function(){
        console.log('完成接口调用')

      },
      success:function(){
        console.log('连接websocket成功')
      },
      fail:function(e){
        console.log('连接websocket失败',e)
      }
    }),
    wx.onSocketOpen(function(res){
      that.isSocketOpened=true;
      console.log('websocket连接已成功创建',res)
    }),
    wx.onSocketClose(function(){
      that.isSocketOpened=false;
      console.log('webSocket连接已关闭')
    })
  },
  sendMessage:function(text){
    if(this.isSocketOpened){
        wx.sendSocketMessage({
          data: text,
          success:function(){
            console.log('向服务器发送消息成功')
          },
          fail:function(){
            console.log('向服务器发送消息失败')
          },
          complete:function(){
            console.log('调用完成')
          }
        })
    }
    else{
      wx.showModal({
        title: '错误',
        content: '未创建socket连接',
        showCancel:false,
        success:function(res){
          if(res.confirm){
            console.log('用户点击确定')
          }
        }
      })
    }
  },  

  requestGetFun:function(){
    wx.request({
      url: 'https://www.baidu.com/',
      method:'GET',
      data:{
        id:'1',
        type:'50'
      },
      header:{
        'content-type':'application/json'
      },
      success:function(res)
      {
        console.log(res.data)
      },
      fail:function(res){
        console.log(res)
      }
    })
  },

  submitFun:function(e){
    this.sendMessage(e.detail.value.message)
  },
  closeWebSocketFun:function(e)
  {
    wx.closeSocket()
  },


  chooseImageFun:function(){
    var that=this
    wx.chooseImage({
      count:1,
      sizeType:['original','compressed'],
      sourceType:['album','camera'],
      success: function(res) {
         var tempFilePaths=res.tempFilePaths
         that.setData({
           imgSrc:tempFilePaths[0]
         })
         if(tempFilePaths.length>0){
           that.setdata({
             isShowImage:true
           })
         }
         console.log('选择的图片路径为:',tempFilePaths)
      },
    })
  },

  chooseVideoFun:function(e)
  {
    var that=this
    wx.chooseVideo({
      maxDuration:60,
      sourceType:['album','camera'],
      camera:['front','back'],
      success:function(res){
        console.log(res)
        that.setData({
          videoSrc:res.tempFilePath
        })
        if(that.data.videoSrc!=''){
          that.setData({
            isShowVideo:true
          })
        }
      },
      fail:function(e){
         console.log('获取视屏失败')
      }
    })
  },
  previewImageFun:function(){
    wx.previewImage({
      current: '',
      urls: [
        'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg'
      ],
      success: function(res) {console.log('图片预览成功')},
      fail: function(res) {},
      complete: function(res) {},
    })
  },
  startRecordFun:function(){
    console.log('录音开始')
    var that=this
    wx.startRecord({
      success:function(res){
        console.log('录音成功')
        that.tempVoiceFilePath=res.tempFilePath
      },
      fail:function(){

      },
      complete:function(){

      }
    })
  },
  playRecordVoice:function()
  {
    var that=this
    if(this.tempVoiceFilePath!='')
    {
        wx.playVoice({
          filePath: that.tempVoiceFilePath,
          success:function(){
            console.log('播放录音成功')
          },
          fail:function(){

          },
          complete:function()
          {

          }
        })
    }
    else
    {
      wx.showToast({
        title: '未录音',
        icon:'success',
        duration:2000
      })
    }
  },

  pauseRecordVoice:function()
  {
    wx.pauseVoice()
  },
  stopRecordVoice:function()
  {
    wx.stopVoice()
  },
  stopRecordFun:function()
  {
    wx.stopRecord()
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this
    this.createSocketConnect()
    wx.onSocketError(function(res){
      that.isSocketOpended=false
      console.log('webSocket发生异常,请检查!')
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
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