Page({

  /**
   * 页面的初始数据
   */
  data: {
    btn1: '',
    btn2: '',
    btn3: '',
    btn4: '',
    disabled1:false,
    disabled2:false,
    disabled3:false,
    disabled4:false,
    input: '',
    current: 1,
    lookKey:0,
    Q:[
    ['1','1','1','8'],
    ['1','1','2','6'],
    ['1','1','2','7'],
    ['1','1','2','8'],
    ['1','1','2','9'],
    
    ['1','8','8','9'],
    ['1','1','3','4'],
    ['1','1','3','5'],
    ['1','1','3','6'],
    ['1','1','3','7'],
    
    ['2','2','5','5'],
    ['2','2','5','6'],
    ['2','2','5','7'],
    ['2','2','5','8'],
    ['2','2','5','9'],

    ['2','2','2','3'],
    ['2','2','6','6'],
    ['2','2','6','7'],
    ['2','2','6','8'],
    ['2','2','6','9'],
     
    ['3','3','3','3'],
    ['3','3','3','4'],
    ['3','3','3','5'],
    ['3','3','3','6'],
    ['3','3','3','7'],

    ['3','3','3','8'],
    ['3','3','3','9'],
    ['3','3','5','5'],
    ['3','3','5','6'],
    ['3','3','5','7'],

    ['4','4','4','4'],
    ['4','4','4','5'],
    ['4','4','4','6'],
    ['4','4','4','7'],
    ['4','4','4','8'],

    ['4','4','4','9'],
    ['4','4','5','5'],
    ['4','4','5','6'],
    ['4','4','5','7'],
    ['4','4','5','8'],

    ['5','5','5','5'],
    ['5','5','5','6'],
    ['5','5','5','9'],
    ['5','5','6','6'],
    ['5','5','6','7'],

    ['5','5','6','8'],
    ['5','5','7','7'],
    ['5','5','7','8'],
    ['5','5','8','8'],
    ['5','5','8','9']
    ],
    key: ['(1+1+1)*8','(1+1+2)*6','(1+2)*(1+7)','(1*1+2)*8','(1+2)*(9-1)',
    '(8+9)-(1-8)','(1+1)*3*4','(1+3)*(1+5)','(1*1+3)*6','(1*1+7)*3',
    '5*5-2/2','(5-2/2)*6','2*5+2*7','(5+8)*2','(9-(2-5))*2',
    '(2+2)*2*3','(2+6)/2*6','(2+7)*2+6','(8-(2+2))*6','(6/2+9)*2',
    '3*3*3-3','(3*3-3)*4','3*3+3*5','(3+3)*3+6','(3+3)*(7-3)',
    '(3+3)-3*8','(9-3/3)*3','5*5-3*3','(3+3)*5-6','(3*5-7)*3',
    '4+4+4*4','(4/4+5)*4','(4+4-4)*6','(4+4)*(7-4)','(4+4)*4-8',
    '4-(4-9)*4','5*5-4/4','(5-4/4)*6','(4-5+7)*4','(4+4-5)*8',
    '5*5-5/5','5*5+5-6','5+5+5+9','(5+5-6)*6','5*5+6-7',
    '5+5+6+8','5+5+7+7','(5+5-7)*8','5*5-9/9','5*5+8-9'
    ],
    isTrue:false,
    max:50
  },
  lookRule:function()
  {
    wx.showModal({
      title: '规则',
      content: '用所给的4位数字组成和为24的算式',
      showCancel:false
    })
  },
  lookKey:function()
  {
    var that=this;
    if(that.data.lookKey>0)
    {
      wx.showModal({
        title: 'Key',
        content: this.data.key[this.data.current-1],
        showCancel:false
      })
      this.setData({
        lookKey:that.data.lookKey-1
      })
    }
    else
    {
      wx.showModal({
        title: '提示',
        content: '点击右上角转发到群即可查看答案！',
        showCancel: false
      })
    }
  },
  btn: function (e) {
    console.log(e);
    var s = this.data.input;
    switch (e.target.dataset.id) {
      case '1':
        s += this.data.btn1;
        this.setData({
          disabled1:true
        });
        break;
      case '2':
        s += this.data.btn2;
        this.setData({
          disabled2: true
        });
        break;
      case '3':
        s += this.data.btn3;
        this.setData({
          disabled3: true
        });
        break;
      case '4':
        s += this.data.btn4;
        this.setData({
          disabled4: true
        });
        break;
      case '5':
        s += '+';
        break;
      case '6':
        s += '-';
        break;
      case '7':
        s += '*';
        break;
      case '8':
        s += '/';
        break;
      case '9':
        s += '(';
        break;
      case '10':
        s += ')';
        break;
      case '11':
        s = "";
        this.setData({
          disabled1:false,
          disabled2:false,
          disabled3:false,
          disabled4:false
        })
        break;
      default: break;
    }
    this.setData({
      input: s
    })
  },
  btn2: function (e) {
    if(this.data.input==')(')
    {
      this.setData({
        lookKey: this.data.lookKey + 1
      })
      this.lookKey();
      this.setData({
        input:''
      })
      return 0;
    }
    if (this.calculate() == 1)
    {
      wx.showToast({
        title: '回答正确！',
        icon:'success',
        duration:1000
      })
      this.setData({
        isTrue:'true'
      })
    }
    //計算正確。
    else
    {
      wx.showToast({
        title: '答案错啦~',
        icon: 'none',
        duration: 1000
      })
    }
    //计算不正确。
  },
  calculate:function()
  {
    console.log('calculate');
    var s=this.data.input.split('');
    console.log(s);
    if (this.isExpression(s))
    {
      console.log("isExpression");
    }
    if (this.isExpression(s) && this.postfixCalculate(this.infixTranPostfix(s))==24)
    {
      return 1;
    }
    else
    {
      return 0;
    }
  },
  isExpression:function(s){
    var expression=[];
    expression=s;
    var i=0;
    var leftBracket=0;
    var rightBracket=0;
    for(i=0;i<expression.length;i++)
    {
      if(i==0 && !(this.isNumber(expression[i]) || expression[i]=='('))
      {
        console.log('1');
        return 0;
      }
      if(i!=0 && this.isOperator(expression[i-1]) && (this.isOperator(expression[i]) || expression==')'))
      {
        console.log('2');
        return 0;
      }
      if(i!=0 && this.isOperator(expression[i-1])=='('&& !this.isNumber(expression[i-1]))
      {
        console.log('3');
        return 0;
      }
      if(expression[i]=='(')
      {
        console.log('lB');
        leftBracket++;
      }
      if(expression[i]==')')
      {
        console.log('rB');
        rightBracket++;
      }
    }
    if(leftBracket==rightBracket)
    {
      return 1;
    }
    else
    {
      console.log('4');
      return 0;
    }
  },//检测输入是否合法
  infixTranPostfix:function(s)
  {
    var infex=[];//中缀表达式
    infex=s;
    console.log(infex);
    var temp=[];//临时变量作为操作符栈
    var postfix=[];//后缀表达式
    for(var i=0;i<infex.length;i++)
    {
      //数字或括号直接压入
      if(this.isNumber(infex[i]))
      {
        postfix.push(infex[i]);
      }
      else if(this.isOperator(infex[i]))
      {
        if(!temp.length)
        {
          temp.push(infex[i]);
        }
        else
        {
          if(this.priority(temp[temp.length-1])<this.priority(infex[i]))
          {
            temp.push(infex[i])
          }
          else
          {
            postfix.push(temp[temp.length-1]);
            temp.pop();
            temp.push(infex[i]);
          }
        }
      }
      else if(infex[i]=='(')
      {
         temp.push(infex[i]);
      }
      //')'
      else
      {
        while(1)
        {
          if(temp[temp.length-1]=='(')
          {
            temp.pop();
            break;
          }
          else
          {
            postfix.push(temp[temp.length-1]);
            temp.pop();
          }
        }
      }
    }
    while(1)
    {
      if(!temp.length)
      {
        break;
      }
      else{
        postfix.push(temp.pop());
      }
    }
    console.log(postfix);
    return postfix;
  },//中缀转后缀
  postfixCalculate:function(s)
  {
  var postfix=[];
  postfix=s; 
  var temp=[];
  for(var i=0;i<postfix.length;i++)
  {
    if(this.isNumber(postfix[i]))
    {
      temp.push(postfix[i]);
    }
    else if(this.isOperator(postfix[i]))
    {
      var op=postfix[i];
      var left=temp.pop();
      var right=temp.pop();
      temp.push(this.basicCalculate(left,op,right));
    }
    else{}
  }
  console.log(temp[temp.length - 1])
  return (Number)(temp[temp.length - 1]);
  },//计算后缀表达式
  basicCalculate:function(left,op,right)
  {
     if(op=='+')
     {
       console.log((String)((Number)(left) + (Number)(right)));
       return (String)((Number)(left)+(Number)(right));
     }
     else if(op=='-')
     {
       console.log((String)((Number)(right) - (Number)(left)));
       return (String)((Number)(right) - (Number)(left));
     }
     else if (op == '*') {
       console.log((String)((Number)(left) * (Number)(right)));
       return (String)((Number)(left) * (Number)(right));
     }
     else{
       return (String)((Number)(right) / (Number)(left));
     }
  },
  isNumber:function(c){
    if(c>='0'&&c<='9')
    {
      return true;
    }
    else{
      return false;
    }
  },
  isOperator:function(c)
  {
    if(c=='+' || c=='-' ||c=='*' || c=='/')
    {
      return true;
    }
    else{
      return false;
    }
  },
  isBracket:function(c)
  {
    if(c=='(' || c==')')
    {
      return true;
    }
    else
    {
      return false;
    }
  },
  priority:function(c)
  {
    if(c==')')
    {
      return 4;
    }
    else if(c=='^')
    {
      return 3;
    }
    else if(c=='*' || c=='/')
    {
      return 2;
    }
    else if(c=='+' || c=='-')
    {
      return 1;
    }
    //'('
    else
    {
      return 0;
    }
  },
  next:function(e)
  {
    var that=this;
    var currentTemp=this.data.current;
    currentTemp++;
    if(currentTemp>this.data.max)
    {
      wx.showModal({
        title: '提示',
        content: '更多关卡正在开发中',
      })
      return 0;
    }
    this.setData({
      current:currentTemp
    })
    this.setData({
      btn1:this.data.Q[that.data.current-1][0],
      btn2:this.data.Q[that.data.current-1][1],
      btn3:this.data.Q[that.data.current-1][2],
      btn4:this.data.Q[that.data.current-1][3],
      isTrue:false,
      input:'',
      disabled1:false,
      disabled2: false,
      disabled3: false,
      disabled4: false
    })
    wx.setStorage({
      key: 'current',
      data: this.data.current
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   var that=this;
   wx.getStorage({
     key: 'current',
     success: function(res) {
       that.setData({
         current:res.data
       })
     },
   })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that=this;
    console.log(this.data.current);
    that.setData({
      btn1: that.data.Q[that.data.current - 1][0],
      btn2: that.data.Q[that.data.current - 1][1],
      btn3: that.data.Q[that.data.current - 1][2],
      btn4: that.data.Q[that.data.current - 1][3]
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
  onShareAppMessage: function (res) {
      var that=this;
      return {
        title: '数字游戏，不服来战',
        path: '/pages/home/home',
        success: function (res) {
          // 转发成功
         that.setData({
           lookKey:that.data.lookKey+1
         })
        },
        fail: function (res) {
          // 转发失败
        }
      }
  }
})