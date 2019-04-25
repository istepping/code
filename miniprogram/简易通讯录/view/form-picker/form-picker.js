Page({

  /**
   * 页面的初始数据
   */
  data: {
    sex:['男性','女性','保密'],
    selectedSexIndex:0,
    time:'15:23',
    timeStart:'09:01',
    timeEnd:'20:19',
    date:'2018-3-11',
    dateStart:'2017-1-1',
    dateEnd:'2019-1-1',
    fields:'day',
    initValue:'初始化的文字内容',
    iTypes:["text","number","idcard","digit","time","date"],
    isPassword:false,
    placeholderText:"请输入文字",
    isDisabled:false,
    maxLength:20,
    isAutoFocus:true,
    isFocus:true,
    pickedTypeIndex:0
  },
  inputKeypressEvent:function(e)
  {
    console.log(e)
    console.log('输入了内容')
  },
  inputFocusEvent:function(e)
  {
    console.log('获取了焦点')
  },
  inputBlurEvent:function(e)
  {
    console.log('失去了焦点')
  },
  pickerChangeEvent:function(e)
  {
    this.setData({
      pickedTypeIndex:e.detail.value
    })
  },
  switchPwdChangeEvent:function(e)
  {
    this.setData({
      isPassword:e.detail.value
    })
  },
  switchDisChangeEvent:function(e)
  {
     this.setData({
       isDisabled:e.detail.value
     })
  },
  selectDateChange:function(e)
  {
    console.log(e)
    this.setData({
      date:e.detail.value
    })
  },
  sliderChangeEvent:function(e)
  {
     this.setData({
       maxLength:e.detail.value
     })
  },
  selectTimeChange:function(e)
  {
    console.log(e)
    this.setData({
      time:e.detail.value
    })
  },
  selectChange:function(e)
  {
    console.log(e)
    this.setData({
    selectedSexIndex:e.detail.value
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