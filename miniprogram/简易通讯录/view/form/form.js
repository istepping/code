// view/form/form.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
     loading:false,
     disabled:false,
     items1:[
      {name:'USA',value:'美国'},
      {name:'CHN',value:'中国',checked:'true'},
      {name:'BRA',value:'巴西'},
      {name:'JPN',value:'日本'},
      {name:'ENG',value:'英国'},
      {name:'TUR',value:'法国'}
     ],
     items2:[
      {name:'BEIJING',value:'北京',checked:'true'},
      {name:'SHANGHAI',value:'上海'},
      {name:'SJANDONG',value:'山东'},
      {name:'SHENZHEN',value:'深圳'},
      {name:'HANGZHOU',value:'杭州'}
     ],
     selectedItem1:[],
     selectedItem2:[]
  },
  checkbox1Change:function(e)
  {
    console.log(e)
    var selectedItems=e.detail.value
    console.log('checkbox1发生change事件,value:',selectedItems)
    this.setData({
      selectedItems1:selectedItems
    })
    console.log('selectedItems1:',this.data.selectedItems1)
  },
  checkbox2Change: function (e) {
    var selectedItems = e.detail.value
    console.log('checkbox2发生change事件,value:', selectedItems)
    this.setData({
      selectedItems2: selectedItems
    })
    console.log('selectedItems2:', this.data.selectedItems2)
  },
  sleep:function(numberMillis){
     var now=new Date()
     var exitTime=now.getTime()+numberMillis
     while(true)
     {
       now=new Date()
       if(now.getTime()>exitTime)
       return
     }
  },
  switchState:function(){
     var that=this
     this.setData({
       loading:true,
       disabled:true
     })
     this.sleep(1000)
     var timer=setTimeout(function(){
       that.setData({
         loading:false,
         disabled:false
       })
     },1000)
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