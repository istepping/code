const app=getApp();
var QQMapWX=require('../../util/QQmap/qqmap-wx-jssdk.js');
var QQMapSDK=new QQMapWX({
  key:'PGQBZ-R34HF-HCDJ4-JSPDO-S2J6Z-CHFOT'
});
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address:'点击获取位置',
    checkedID:1,
    goods:app.globalData.goods,
    sorts:[
      [
      {
        id:1,
        icon:'http://m.qpic.cn/psb?/V11MDig5401uVY/Kx46MpApjmqN1scQ.hxPmJmyUvO9T1R*EmOIroOUkxo!/b/dFYBAAAAAAAA&bo=lgCWAAAAAAARBzA!&rf=viewer_4',
        name:'美食',
        url:''
      },
      {
        id:2,
        icon:'http://m.qpic.cn/psb?/V11MDig5401uVY/ryATo16hfRVXxd8uPsdkbPnzFohpS2HNF.gxOp9aEFI!/b/dEMBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name:'超市',
        url:''
      },
      {
        id: 3,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/RZfAv3TLgEPVnaM3ku219vrytg*gwId1vCv8g5..w0M!/b/dEEBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '生鲜果蔬',
        url: ''
      },
      {
        id: 4,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/ueiMGfq3sMKZGuW4twBfSBvZtHVz3JVGdSYo5rnUE*o!/b/dJEAAAAAAAAA&bo=lgCWAAAAAAARBzA!&rf=viewer_4',
        name: '甜点饮品',
        url: ''
      },
      {
        id: 5,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/I4pDhuzBmmCKEc8Affl4jDwii7c5Wrhe4DaCC3ugjww!/b/dEEBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '正餐优选',
        url: ''
      },
      {
        id: 6,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/RZfAv3TLgEPVnaM3ku219vrytg*gwId1vCv8g5..w0M!/b/dEEBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '快餐小吃',
        url: ''
      },
      {
        id: 7,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/Vfvol8SCg86hD4Nbi5XqaR9ffjn4IdqD12P.k6b3ad8!/b/dEQBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '汉堡披萨',
        url: ''
      },
      {
        id: 8,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/5QUOth0vJN01HxbHdImRDdeJieoxhKDYvIFnwkt*31o!/b/dEUBAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '新商家',
        url: ''
      }
    ],
    [
      {
        id: 9,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 10,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 11,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 12,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 13,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 14,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 15,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      },
      {
        id: 16,
        icon: 'http://m.qpic.cn/psb?/V11MDig5401uVY/z1D6ZDS1KSGtQkLMv2szx5U5weSG5xuPo4JpKnz*6*w!/b/dJUAAAAAAAAA&bo=lgCWAAAAAAARFyA!&rf=viewer_4',
        name: '免费配送',
        url: ''
      }
    ]
    ]
  },
  checkedFun:function(res)
  {
    this.setData({
      checkedID:res.target.dataset.id
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  getLocation:function()
  {
    var that=this;
    wx.getLocation({
      success: function(res) {
        var latitude=res.latitude;
        var longitude=res.longitude;
        QQMapSDK.reverseGeocoder({
          location:{
            latitude:latitude,
            longitude:longitude
          },
          success:function(res)
          {
            that.setData({
              address:res.result.formatted_addresses.recommend
            })
          }
        })
      },
      fail:function()
      {
         //提示信息
         wx.showToast({
           title: '获取位置失败，请先打开定位功能',
           icon: 'none',
           duration: 2000,
           mask: true,
           success: function(res) {},
           fail: function(res) {},
           complete: function(reress) {},
         })
      }
    })
  },
  onLoad: function (options) {
    this.getLocation();
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