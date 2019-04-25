// view/audio/audio.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
  },
  playEvent:function()
  {
    this.audioContext.play()
  },
  pauseEvent:function()
  {
    this.audioContext.pause()
  },
  seekEvent:function()
  {
    this.audioContext.seek(8)
  },
  playFun:function(){
    console.log('开始播放')
  },
  pauseFun:function(){
    console.log('播放暂停')
  },
  timeUpdateFun:function(e){
    console.log('时间轴更新了',e)
  },
  endedFun:function(){
    console.log('播放结束')
  },
  errFun:function(e){
    console.log('播放出错',e)
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
    this.audioContext=wx.createAudioContext('theAudio')
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