package com.example.dell.mygame10;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private static final int ID_LEFT = 9;
    private static final int ID_FIRE = ID_LEFT + 1;
    public static final int SLEEP_TIME = 40;// 两次调度之间默认的暂停时间
    public static final int MIN_SLEEP = 5; // 最小的暂停时间
    public static final int STAGE_NO_CHANGE = 0;// 代表场景不改变的常量
    public static final int STAGE_INIT = 1;// 代表初始化场景的常量
    public static final int STAGE_LOGIN = 2;// 代表登录场景的常量
    public static final int STAGE_GAME = 3; // 代表游戏场景的常量
    public static final int STAGE_LOSE = 4;  // 代表失败场景的常量
    public static final int STAGE_QUIT = 99;// 代表退出场景的常量
    public static final int STAGE_ERROR = 255;// 代表错误场景的常量
    private static final int INIT = 1;// 步骤：初始化
    private static final int LOGIC = 2;//步骤：逻辑
    private static final int CLEAN = 3;// 步骤：清除
    private static final int PAINT = 4;// 步骤：画
    public static final Player player = new Player("突击者", Player.MAX_HP,0);//实例化player
    public static final Player player2 = new Player("突击者", Player.MAX_HP,1);
    private Context mainContext = null;// 保存当前Android应用的主Context
    private Paint paint = null;// 画图所需要的Paint和Canvas对象
    private Canvas canvas = null;
    private SurfaceHolder surfaceHolder; // SurfaceHolder负责维护SurfaceView上绘制的内容
    private int currentStage = 0;// 定义该游戏当前处于何种场景的变量
    public static final List<Integer> stageList =
            Collections.synchronizedList(new ArrayList<Integer>());// 定义一个集合来保存该游戏已经加载到所有场景
    public GameView(Context context, int firstStage) {
        super(context);
        mainContext = context;//（1）情景
        paint = new Paint();
        // 设置抗锯齿
        paint.setAntiAlias(true);//（2）初始化
        // 设置该组件会保持屏幕常量，避免游戏过程中出现黑屏。
        setKeepScreenOn(true);
        // 设置焦点，相应事件处理
        setFocusable(true);
        // 获取SurfaceHolder
        surfaceHolder = getHolder();
        // 设置this为SurfaceHolder的回调，这要求该类实现SurfaceHolder.Callback接口
        surfaceHolder.addCallback(this);
        //初始化屏幕大小
        ViewManager.initScreen(MainActivity.windowWidth
                , MainActivity.windowHeight);
        currentStage = firstStage;
    }// 定义GameView的构造器
    public Handler setViewHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            RelativeLayout layout = (RelativeLayout) msg.obj;
            if (layout != null)
            {
                RelativeLayout.LayoutParams params = new RelativeLayout
                        .LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                MainActivity.mainLayout.addView(layout, params);
            }
        }
    };
    public Handler delViewHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            RelativeLayout layout = (RelativeLayout) msg.obj;
            if (layout != null)
            {
                MainActivity.mainLayout.removeView(layout);
            }
        }
    };
    RelativeLayout gameLayout = null; // 定义游戏界面
    private RelativeLayout loginView;// 定义登录界面
    private RelativeLayout loseView;// 定义游戏失败界面

    public void stageLogic() {
        int newStage = nextStage(currentStage, LOGIC);
        if (newStage != STAGE_NO_CHANGE && newStage != currentStage)
        {
            nextStage(currentStage, CLEAN); // 清除旧的场景
            currentStage = newStage & 0xFF;
            nextStage(currentStage, INIT);
        }
        else if (stageList.size() > 0)
        {
            newStage = STAGE_NO_CHANGE;
            synchronized (stageList)
            {
                newStage = stageList.get(0);
                stageList.remove(0);
            }
            if (newStage == STAGE_NO_CHANGE)
            {
                return;
            }
            nextStage(currentStage, CLEAN); // 清楚旧的场景
            currentStage = newStage & 0xFF;
            nextStage(currentStage, INIT);
        }
    }
    public int nextStage(int stage, int step) {
        int nextStage;
        switch (stage)
        {
            case STAGE_INIT:
                nextStage = doInit(step);
                break;
            case STAGE_LOGIN:
                nextStage = doLogin(step);
                break;
            case STAGE_GAME:
                nextStage = doGame(step);
                break;
            case STAGE_LOSE:
                nextStage = doLose(step);
                break;
            default:
                nextStage = STAGE_ERROR;
                break;
        }
        return nextStage;
    } // 处理游戏场景,返回下一个stage
    public int doInit(int step) {
        ViewManager.loadResource(); // 初始化游戏图片
        return STAGE_LOGIN;// 跳转到登录界面
    }// 执行初始化的方法
    public int doGame(int step) {
        Log.v("a","a");
        switch (step)
        {
            case INIT:
                if (gameLayout == null) // 初始化游戏界面
                {
                    gameLayout = new RelativeLayout(mainContext);
                    Button button = new Button(mainContext);// 添加向左移动的按钮
                    button.setId(ID_LEFT);
                    button.setBackgroundResource(R.drawable.left); // 设置按钮的背景图片
                    RelativeLayout.LayoutParams params = new RelativeLayout
                            .LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.setMargins((int) (ViewManager.scale * 20),
                            0, 0, (int) (ViewManager.scale * 10));
                    gameLayout.addView(button, params);// 向游戏界面上添加向左的按钮
                    button.setOnTouchListener(new OnTouchListener()
                    {
                        @Override
                        public boolean onTouch(View v, MotionEvent event)// 为按钮添加事件监听器
                        {
                            switch (event.getAction())
                            {
                                case MotionEvent.ACTION_DOWN:
                                    player.setMove(Player.MOVE_LEFT);
                                    player2.setMove(Player.MOVE_LEFT);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    player.setMove(Player.MOVE_STAND);
                                    player2.setMove(Player.MOVE_STAND);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    break;
                            }
                            return false;
                        }
                    });
                    button = new Button(mainContext);// 添加向右移动的按钮
                    button.setBackgroundResource(R.drawable.right);// 设置按钮的背景图片
                    params = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.RIGHT_OF, ID_LEFT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.setMargins((int) (ViewManager.scale * 20),
                            0, 0, (int) (ViewManager.scale * 10));
                    gameLayout.addView(button, params);// 向游戏界面上添加向右的按钮
                    button.setOnTouchListener(new OnTouchListener()
                    {
                        public boolean onTouch(View v, MotionEvent event)
                        {// 为按钮添加事件监听器
                            switch (event.getAction())
                            {
                                case MotionEvent.ACTION_DOWN:
                                    player.setMove(Player.MOVE_RIGHT);
                                    player2.setMove(Player.MOVE_RIGHT);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    player.setMove(Player.MOVE_STAND);
                                    player2.setMove(Player.MOVE_STAND);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    break;
                            }
                            return false;
                        }
                    });
                    button = new Button(mainContext);// 添加射击按钮
                    button.setId(ID_FIRE);
                    button.setBackgroundResource(R.drawable.fire);// 设置按钮的背景图片
                    params = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.setMargins(0, 0, (int) (ViewManager.scale * 10),
                            (int) (ViewManager.scale * 8));
                    gameLayout.addView(button, params);// 向游戏界面上添加射击的按钮
                    button.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {// 为按钮添加事件监听器
                            if(player.getLeftShootTime() <= 0)// 当角色的leftShootTime为0时（上一枪发射结束），角色才能发射下一枪
                            {
                               Player.addBullet();
                            }
                        }
                    });
                    button = new Button(mainContext);// 添加跳的按钮
                    button.setBackgroundResource(R.drawable.jump);// 设置按钮的背景图片
                    params = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.LEFT_OF, ID_FIRE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.setMargins(0, 0, (int) (ViewManager.scale * 8),
                            (int) (ViewManager.scale * 8));
                    gameLayout.addView(button, params);// 向游戏界面上添加跳的按钮
                    button.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        { // 为按钮添加事件监听器
                            if(player.getY()>= (ViewManager.SCREEN_HEIGHT * 75 / 100))
                            player.setJump(true);
                            player2.setJump(true);
                        }
                    });
                    setViewHandler.sendMessage(setViewHandler
                            .obtainMessage(0, gameLayout));
                }
                break;
            case LOGIC:
                MonsterManager.generateMonster();// 随机生成怪物
                MonsterManager.checkMonster();// 检查碰撞
                player.logic();// 角色跳与移动
                player2.logic();
                if (player.isDie())// 角色死亡
                {
                    stageList.add(STAGE_LOSE);
                }
                break;
            case CLEAN:
                if (gameLayout != null)// 清除游戏界面
                {
                    delViewHandler.sendMessage(delViewHandler
                            .obtainMessage(0, gameLayout));
                    gameLayout = null;
                }
                break;
            case PAINT:// 画游戏元素
                ViewManager.clearScreen(canvas);//瞬间黑屏切换下一个界面
                ViewManager.drawGame(canvas);
                break;
        }
        return STAGE_NO_CHANGE;
    }
    public int doLogin(int step) {
        switch (step)
        {
            case INIT:
                player.setHp(Player.MAX_HP);// 初始化角色血量
                if (loginView == null)// 初始化登录界面
                {
                    loginView = new RelativeLayout(mainContext);
                    loginView.setBackgroundResource(R.drawable.game_back);
                    Button button = new Button(mainContext);// 创建按钮
                    button.setBackgroundResource(R.drawable.button_selector);// 设置按钮的背景图片
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    loginView.addView(button, params);// 添加按钮
                    button.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            stageList.add(STAGE_GAME);// 将游戏场景的常量添加到stageList集合中
                        }
                    });
                    setViewHandler.sendMessage(setViewHandler
                            .obtainMessage(0, loginView));  // 通过Handler通知主界面加载loginView组件
                }
                break;
            case LOGIC:
                break;
            case CLEAN:
                if (loginView != null) // 清除登录界面
                {
                    delViewHandler.sendMessage(delViewHandler// 通过Handler通知主界面删除loginView组件
                            .obtainMessage(0, loginView));
                    loginView = null;
                }
                break;
            case PAINT:
                break;
        }
        return STAGE_NO_CHANGE;
    }
    public int doLose(int step) {
        switch (step)
        {
            case INIT:
                if (loseView == null)// 初始化失败界面
                {
                    loseView = new RelativeLayout(mainContext); // 创建失败界面
                    loseView.setBackgroundResource(R.drawable.game_back);
                    Button button = new Button(mainContext);
                    button.setBackgroundResource(R.drawable.again);
                    RelativeLayout.LayoutParams params = new RelativeLayout
                            .LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    loseView.addView(button, params);
                    button.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            stageList.add(STAGE_GAME);// 跳转到继续游戏的界面
                            player.setHp(Player.MAX_HP);// 让角色的生命值回到最大值
                        }
                    });
                    setViewHandler.sendMessage(setViewHandler
                            .obtainMessage(0, loseView));
                }
                break;
            case LOGIC:
                break;
            case CLEAN:
                if (loseView != null)// 清除界面
                {
                    delViewHandler.sendMessage(delViewHandler
                            .obtainMessage(0, loseView));
                    loseView = null;
                }
                break;
            case PAINT:
                break;
        }
        return STAGE_NO_CHANGE;
    }
    class GameThread extends Thread {
        public SurfaceHolder surfaceHolder = null;
        public boolean needStop = false;
        public GameThread(SurfaceHolder holder)
        {
            this.surfaceHolder = holder;
        }

        public void run() {
            long t1, t2;
            Looper.prepare();
            synchronized (surfaceHolder)
            {
                while (currentStage != STAGE_QUIT && needStop == false)// 游戏未退出
                {
                    try
                    {
                        stageLogic();// 处理游戏的场景逻辑，到开始游戏界面
                        t1 = System.currentTimeMillis();
                        canvas = surfaceHolder.lockCanvas();//canvas实例化
                        if (canvas != null)
                        {
                            nextStage(currentStage, PAINT);// 处理游戏场景
                        }
                        t2 = System.currentTimeMillis();
                        int paintTime = (int) (t2 - t1);
                        long millis = SLEEP_TIME - paintTime;
                        if (millis < MIN_SLEEP)
                        {
                            millis = MIN_SLEEP;
                        }
                        sleep(millis);// 该线程暂停millis毫秒后再次调用doStage()方法
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            if (canvas != null)
                            {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Looper.loop();
            try
            {
                sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }// 游戏线程

    private GameThread thread = null;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 启动主线程执行部分
        paint.setTextSize(15);
        if (thread != null)
        {
            thread.needStop = true;
        }
        thread = new GameThread(surfaceHolder);
        thread.start();//游戏线程开始
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}
