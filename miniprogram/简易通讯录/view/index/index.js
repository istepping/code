// view/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    thisYear:new Date().getFullYear(),
     contactData:[
       {
         id:'1',
         name:'小张',
         phone:18742586015,
         birthday:1998
       },
       {
         id: '2',
         name: '小孙',
         phone: 18155339233,
         birthday: 1998
       },
       {
         id: '3',
         name: '小陈',
         phone: 15072066915,
         birthday: 1998
       },
       {
         id: '4',
         name: '小姜',
         phone: 18742585840,
         birthday: 1998
       }
     ]
  },
submitFun:function(e)
{
  var newContactData = this.data.contactData
  var newItem = {
    id: newContactData.length + 1,
    name:e.detail.value.name,
    phone:e.detail.value.phone,
    birthday: e.detail.value.birthday
  }
  newContactData.push(newItem)
  this.setData({
    contactData: newContactData
  })
},
addData:function(){
   var newContactData=this.data.contactData
   var newItem={
     id:newContactData.length+1,
     name:'新人',
     phone:'1238947920',
     birthday:'1970'
   }    
   newContactData.push(newItem)
   this.setData({
     contactData:newContactData
   })
  },
deleteMe:function(e){
   console.log(e)
   var newContactData=this.data.contactData
   var index=e.currentTarget.dataset.index
   newContactData.splice(index,1)
   this.setData({
     contactData:newContactData
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