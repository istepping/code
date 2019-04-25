package com.example.dell.mygame10;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Player
{
    public static final int MAX_HP = 50;// 定义角色的最高生命值
    public static final float DEFAULT_SPEED=15* ViewManager.scale;
    public static final float DEFAULT_JUMP_SPEED=30*ViewManager.scale;
    public static final float DEFAULT_ACCELERATE_SPEED=4* ViewManager.scale;
    public static final int ACTION_STAND_RIGHT = 1;// 定义控制角色动作的常量
    public static final int ACTION_STAND_LEFT = 2;
    public static final int ACTION_RUN_RIGHT = 3;
    public static final int ACTION_RUN_LEFT = 4;
    public static final int ACTION_JUMP_RIGHT = 5;
    public static final int ACTION_JUMP_LEFT = 6;
    public static final int DIR_RIGHT = 1; // 定义角色向右移动的常量
    public static final int DIR_LEFT = 2;// 定义角色向左移动的常量
    public  int X_DEFAULT = 0;// 控制角色的默认坐标
    public  int Y_DEFAULT = 0;
    public static int Y_JUMP_MAX = 0;
    public static final int MOVE_STAND = 0;// 定义控制角色移动的常量
    public static final int MOVE_RIGHT = 1;
    public static final int MOVE_LEFT = 2;

    private String name;// 保存角色名字的成员变量
    private int hp;// 保存角色生命值的成员变量
    private int copy;//copy！=0时为复制
    private int action = ACTION_STAND_RIGHT;// 保存角色当前动作的成员变量（默认向右站立）
    private int x = -1;// 代表角色X坐标的成员变量
    private int y = -1;// 代表角色Y坐标的成员变量
    private List<Bullet> bulletList = new ArrayList<>();// 保存角色射出的所有子弹
    public int move = MOVE_STAND;// 保存角色移动方式的成员变量
    public static final int MAX_LEFT_SHOOT_TIME = 10;
    private  int leftShootTime = 0;//每当用户发射一枪时，leftShootTime会被设为MAX_LEFT_SHOOT_TIME。只有当leftShootTime变为0时，用户才能发射下一枪
    public boolean isJump = false;// 保存角色是否跳动的成员变量
    public boolean isJumpMax = false; // 保存角色是否跳到最高处的成员变量
    public int jumpStopCount = 0;// 控制跳到最高处的停留时间
    private int indexLeg = 0;// 当前正在绘制角色脚部动画的第几帧
    private int indexHead = 0;// 当前正在绘制角色头部动画的第几帧
    private int currentHeadDrawX = 0;// 当前绘制头部图片的X坐标
    private int currentHeadDrawY = 0; // 当前绘制头部图片的Y坐标
    private Bitmap currentLegBitmap = null;// 当前正在画的脚部动画帧的图片
    private Bitmap currentHeadBitmap = null;// 当前正在画的头部动画帧的图片
    private int drawCount = 0;// 该变量控制用于控制动画刷新的速度
    public Player(String name, int hp,int copy) {
        this.name = name;
        this.hp = hp;
        this.copy=copy;
    } // 定义Player的构造器

    public void move() {
        if (move == MOVE_RIGHT)
        {
            MonsterManager.updatePosition((int) (DEFAULT_SPEED));// 更新怪物的位置
            setX(getX() + (int) (DEFAULT_SPEED));// 更新角色位置
            if (!isJump())
            {
                setAction(Player.ACTION_RUN_RIGHT);// 不跳的时候，需要设置动作
            }
        }
        else if (move == MOVE_LEFT)
        {
            if (getX() - (int) (DEFAULT_SPEED) <X_DEFAULT)
            {
                MonsterManager.updatePosition(-(getX() - X_DEFAULT));// 更新怪物的位置
            }
            else
            {
                MonsterManager.updatePosition(-(int) (DEFAULT_SPEED)); // 更新怪物的位置
            }
            setX(getX() - (int) (DEFAULT_SPEED));// 更新角色位置
            if (!isJump())
            {
                setAction(Player.ACTION_RUN_LEFT); // 不跳的时候，需要设置动作
            }
        }
        else if (getAction() != Player.ACTION_JUMP_RIGHT
                && getAction() != Player.ACTION_JUMP_LEFT)
        {
            if (!isJump())
            {
                setAction(Player.ACTION_STAND_RIGHT);// 不跳的时候，需要设置动作
            }
        }
    }
    public void initPosition() {
        if(copy==0) {
            x = ViewManager.SCREEN_WIDTH * 15 / 100;
            y = ViewManager.SCREEN_HEIGHT * 75 / 100;
        }
        else
        {
            x = ViewManager.SCREEN_WIDTH * 8/ 100;
            y = ViewManager.SCREEN_HEIGHT * 75 / 100;
        }
        X_DEFAULT = x;
        Y_DEFAULT = y;
        Y_JUMP_MAX = ViewManager.SCREEN_HEIGHT * 40/ 100;
    } // 初始化角色的初始化位置，角色能跳的最大高度
    public void draw(Canvas canvas) {
        if (canvas == null)
        {
            return;
        }
        switch (action)
        {
            case ACTION_STAND_RIGHT:
                drawAni(canvas, ViewManager.legStandImage, ViewManager.headStandImage, DIR_RIGHT);
                break;
            case ACTION_STAND_LEFT:
                drawAni(canvas, ViewManager.legStandImage, ViewManager.headStandImage, DIR_LEFT);
                break;
            case ACTION_RUN_RIGHT:
                drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_RIGHT);
                break;
            case ACTION_RUN_LEFT:
                drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_LEFT);
                break;
            case ACTION_JUMP_RIGHT:
                drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_RIGHT);
                break;
            case ACTION_JUMP_LEFT:
                drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_LEFT);
                break;
            default:
                break;
        }
    }// 画角色的方法
    public void drawAni(Canvas canvas, Bitmap[] legArr, Bitmap[] headArrFrom, int dir) {
        if (canvas == null)
        {
            return;
        }
        if (legArr == null)
        {
            return;
        }
        Bitmap[] headArr = headArrFrom;
        // 射击状态停留次数每次减1
        if (leftShootTime > 0)
        {
            headArr = ViewManager.headShootImage;
            leftShootTime--;
        }
        if (headArr == null)
        {
            return;
        }
        indexLeg = indexLeg % legArr.length;
        indexHead = indexHead % headArr.length;
        // 是否需要翻转图片
        int trans = dir == DIR_RIGHT ? Graphics.TRANS_MIRROR : Graphics.TRANS_NONE;
        Bitmap bitmap = legArr[indexLeg];
        if (bitmap == null || bitmap.isRecycled())
        {
            return;
        }
        // 先画脚
        int drawX = X_DEFAULT;
        int drawY = y - bitmap.getHeight();
        Graphics.drawMatrixImage(canvas, bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
        currentLegBitmap = bitmap;
        // 再画头
        Bitmap bitmap2 = headArr[indexHead];
        if (bitmap2 == null || bitmap2.isRecycled())
        {
            return;
        }
        drawX = drawX - ((bitmap2.getWidth() - bitmap.getWidth()) >> 1);
        if (action == ACTION_STAND_LEFT)
        {
            drawX += (int) (6 * ViewManager.scale);
        }
        drawY = drawY - bitmap2.getHeight() + (int) (10 * ViewManager.scale);
        Graphics.drawMatrixImage(canvas, bitmap2, 0, 0, bitmap2.getWidth(),
                bitmap2.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
        currentHeadDrawX = drawX;
        currentHeadDrawY = drawY;
        currentHeadBitmap = bitmap2;
        // drawCount控制该方法每调用4次才会切换到下一帧位图
        drawCount++;
        if (drawCount >= 4)
        {
            drawCount = 0;
            indexLeg++;
            indexHead++;
        }
        // 画子弹
        drawBullet(canvas);
        // 画左上角的角色、名字、血量
        drawLeftHead(canvas);
    }// 绘制角色的动画帧
    public void drawLeftHead(Canvas canvas) {
        if(copy!=0)
        {
            return;
        }
        if (ViewManager.head == null)
        {
            return;
        }
        Graphics.drawMatrixImage(canvas, ViewManager.head, 0, 0,
                ViewManager.head.getWidth(),ViewManager.head.getHeight(),
                Graphics.TRANS_MIRROR, 0, 0, 0, Graphics.TIMES_SCALE);// 画头像
        Paint p = new Paint();
        p.setTextSize(30);
        Graphics.drawBorderString(canvas, 0xa33e11, 0xffde00, name,
                ViewManager.head.getWidth(), (int) (ViewManager.scale * 20), 3, p);// 画名字
        Graphics.drawBorderString(canvas, 0x066a14, 0x91ff1d, "HP: " + hp,
                ViewManager.head.getWidth(), (int) (ViewManager.scale * 40), 3, p);  // 画生命值
    }// 绘制左上角的角色、名字、生命值的方法
    public void drawBullet(Canvas canvas) {
        List<Bullet> deleteList = new ArrayList<>();
        Bullet bullet;
        for (int i = 0; i < bulletList.size(); i++)// 遍历角色发射的所有子弹
        {
            bullet = bulletList.get(i);
            if (bullet == null)
            {
                continue;
            }
            if (bullet.getX() < 0 || bullet.getX() > ViewManager.SCREEN_WIDTH)// 将所有越界的子弹收集到deleteList集合中
            {
                deleteList.add(bullet);
            }
        }
        Bitmap bitmap;
        bulletList.removeAll(deleteList);// 清除所有越界的子弹正常
        for (int i = 0; i < bulletList.size(); i++)// 遍历用户发射的所有子弹
        {
            bullet = bulletList.get(i);
            if (bullet == null)
            {
                continue;
            }
            bitmap = bullet.getBitmap();// 获取子弹对应的位图
            if (bitmap == null)
            {
                continue;
            }
            bullet.move();// 子弹移动
            Graphics.drawMatrixImage(canvas, bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), bullet.getDir() == Player.DIR_LEFT ?
                            Graphics.TRANS_MIRROR : Graphics.TRANS_NONE,
                    bullet.getX(), bullet.getY(), 0, Graphics.TIMES_SCALE);
        }
    }// 画子弹
    public static void addBullet() {

        int bulletX = GameView.player.getDir() == DIR_RIGHT ? GameView.player.X_DEFAULT +(int)
                (ViewManager.scale * 50) : GameView.player.X_DEFAULT - (int)(ViewManager.scale * 50);// 计算子弹的初始X坐标
        Bullet bullet = new Bullet(Bullet.BULLET_TYPE_1, bulletX,
                GameView.player.getY() - (int) (ViewManager.scale * 60), GameView.player.getDir());// 创建子弹对象

        GameView.player.bulletList.add(bullet); // 将子弹添加到用户发射的子弹集合中
        int bullet2X = GameView.player2.getDir() == DIR_RIGHT ? GameView.player2.X_DEFAULT +(int)
                (ViewManager.scale * 50) : GameView.player2.X_DEFAULT - (int)(ViewManager.scale * 50);// 计算子弹的初始X坐标
        Bullet bullet2 = new Bullet(Bullet.BULLET_TYPE_1, bullet2X,
                GameView.player2.getY() - (int) (ViewManager.scale * 60), GameView.player2.getDir());// 创建子弹对象

        GameView.player.bulletList.add(bullet2); // 将子弹添加到用户发射的子弹集合中
        GameView.player.leftShootTime = MAX_LEFT_SHOOT_TIME;// 发射子弹时，将leftShootTime设置为射击状态最大值
        ViewManager.soundPool.play(ViewManager.soundMap.get(1), 1, 1, 0, 0, 1);// 播放射击音效

    }// 发射子弹的方法
    public void logic() {
        if (!isJump())
        {
            move();
            return;
        }
        if (!isJumpMax)// 如果还没有跳到最高点
        {
            setAction(getDir() == Player.DIR_RIGHT ?
                    Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT);
            setY(getY() - (int) (DEFAULT_JUMP_SPEED));// 更新Y坐标
            setBulletYAccelerate(-(int) (DEFAULT_ACCELERATE_SPEED));// 设置子弹在Y方向上具有向上的加速度
            if (getY() <= Player.Y_JUMP_MAX)// 已经达到最高点
            {
                isJumpMax = true;
            }
        }
        else
        {
            jumpStopCount--;
            if (jumpStopCount <= 0)// 如果在最高点停留次数已经使用完
            {
                setY(getY() + (int) (DEFAULT_JUMP_SPEED));// 更新Y坐标
                setBulletYAccelerate((int) (DEFAULT_ACCELERATE_SPEED));// 设置子弹在Y方向上具有向下的加速度
                if (getY() >= Y_DEFAULT) // 已经掉落到最低点
                {
                    setY(Y_DEFAULT); // 恢复Y坐标
                    isJump = false;
                    isJumpMax = false;
                    setAction(Player.ACTION_STAND_RIGHT);
                }
                else
                {
                    setAction(getDir() == Player.DIR_RIGHT ?
                            Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT); // 未掉落到最低点，继续使用跳的动作
                }
            }
        }
        move();// 控制角色移动
    }//处理角色移动与跳的逻辑关系
    public void updateBulletShift(int shift) {
        for (Bullet bullet : bulletList)
        {
            if (bullet == null)
            {
                continue;
            }
            bullet.setX(bullet.getX() + shift);
        }
    }// 更新子弹的位置（子弹位置同样会受到角色的位移的影响）

    public boolean isDie()
    {
        return hp <= 0;
    }// 判断角色是否已经死亡
    public boolean isHurt(int startX, int endX, int startY, int endY) {
        if (currentHeadBitmap == null || currentLegBitmap == null)
        {
            return false;
        }
        int playerStartX = currentHeadDrawX;// 计算角色的图片所覆盖的矩形区域
        int playerEndX = playerStartX + currentHeadBitmap.getWidth();
        int playerStartY = currentHeadDrawY;
        int playerEndY = playerStartY + currentHeadBitmap.getHeight()
                + currentLegBitmap.getHeight();
        return ((startX >= playerStartX && startX <= playerEndX) ||
                (endX >= playerStartX && endX <= playerEndX))
                && ((startY >= playerStartY && startY <= playerEndY) ||
                (endY >= playerStartY && endY <= playerEndY));// 如果子弹出现的位置与角色图片覆盖的矩形区域有重叠，即可判断角色被子弹打中
    }// 判断该角色是否被子弹打中的方法
    public void setBulletYAccelerate(int accelerate) {
        for (Bullet bullet : bulletList)
        {
            if (bullet == null || bullet.getyAccelerate() != 0)
            {
                continue;
            }
            bullet.setyAccelerate(accelerate);
        }
    }// 游戏的设计是：当角色跳动时，子弹会具有垂直方向上的加速度
    public List<Bullet> getBulletList()
    {
        return bulletList;
    }// 获取该角色发射的所有子弹
    public int getDir() {
        if (action % 2 == 1)
        {
            return DIR_RIGHT;
        }
        return DIR_LEFT;
    }// 获取该角色当前方向：action成员变量为奇数代表向右
    public int getShift() {
        if (x <= 0 || y <= 0)
        {
            initPosition();
        }
        return X_DEFAULT - x;
    }// 返回该角色在游戏界面上的位移
    public int getLeftShootTime()
    {
        return leftShootTime;
    }
    public int getX() {
        if (x <= 0 || y <= 0)
        {
            initPosition();
        }

        return x;
    }
    public void setX(int x) {
        this.x = x % (ViewManager.map.getWidth() + X_DEFAULT);
        // 如果角色移动到屏幕最左边
        if (this.x < X_DEFAULT)
        {
            this.x = X_DEFAULT;
        }
    }
    public int getY() {
        if (x <= 0 || y <= 0)
        {
            initPosition();
        }
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getHp()
    {
        return hp;
    }
    public void setHp(int hp)
    {
        this.hp = hp;
    }
    public int getAction()
    {
        return action;
    }
    public void setAction(int action)
    {
        this.action = action;
    }
    public void setMove(int move)
    {
        this.move = move;
    }
    public boolean isJump()
    {
        return isJump;
    }
    public void setJump(boolean isJump) {
        this.isJump = isJump;
        jumpStopCount = 10;
    }//设置最高点停留时间
}
