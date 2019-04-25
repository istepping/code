package com.example.dell.mygame10;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by dell on 2017/8/9.
 */
public class MonsterManager {
    public static final List<Monster> dieMonsterList=new ArrayList<>();
    public static final List<Monster> monsterList=new ArrayList<>();
    public static int KILL_NUMBER=0;
    public static int FIRST_KILL=0;
    public static void selectSound() {
        if(KILL_NUMBER==0);
        else if(KILL_NUMBER==1 && FIRST_KILL==0)
            ViewManager.soundPool.play(ViewManager.soundMap.get(4), 1, 1, 0, 0, 1);
        else if(KILL_NUMBER==2)
            ViewManager.soundPool.play(ViewManager.soundMap.get(5), 1, 1, 0, 0, 1);
        else if(KILL_NUMBER==3)
            ViewManager.soundPool.play(ViewManager.soundMap.get(6), 1, 1, 0, 0, 1);
        else if(KILL_NUMBER==4)
            ViewManager.soundPool.play(ViewManager.soundMap.get(7), 1, 1, 0, 0, 1);
        else if(KILL_NUMBER==5)
        {
            ViewManager.soundPool.play(ViewManager.soundMap.get(8), 1, 1, 0, 0, 1);
            KILL_NUMBER=0;
        }
    }
    public static void generateMonster() {
        if(monsterList.size()<2+Graphics.rand(Monster.TYPE_ALL))
        {
            Monster monster=new Monster(1+Graphics.rand(Monster.TYPE_ALL));
            monsterList.add(monster);
        }
    }
    public static void updatePosition(int shift) {
        Monster monster=null;
        List<Monster> delList=new ArrayList<>();
        for(int i=0;i<monsterList.size();i++)
        {
            monster=monsterList.get(i);
            if(monster==null)
            {
                continue;
            }
            monster.updateShift(shift);
            if(monster.getX()<0)
            {
                delList.add(monster);
            }
        }
        monsterList.removeAll(delList);
        delList.clear();
        for(int i=0;i<dieMonsterList.size();i++)
        {
            monster=dieMonsterList.get(i);
            if(monster==null)
            {
                continue;
            }
            monster.updateShift(shift);
            if(monster.getX()<0)
            {
                delList.add(monster);
            }
            dieMonsterList.removeAll(delList);
            GameView.player.updateBulletShift(shift);
            GameView.player2.updateBulletShift(shift);
        }
    }
    public static void checkMonster() {
        List<Bullet> bulletList = GameView.player.getBulletList();//异常
        bulletList.addAll(GameView.player2.getBulletList());
        if (bulletList == null) {
            bulletList = new ArrayList<>();
        }
        Monster monster = null;
        List<Monster> delList = new ArrayList<>();
        List<Bullet> delBulletList = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            if (monster == null) {
                continue;
            }
            if (monster.getType() == Monster.TYPE_BOMB) {
                if (GameView.player.isHurt(monster.getStartX(), monster.getEndX(), monster.getStartY(), monster.getEndY())) {
                    KILL_NUMBER=0;
                    monster.setDie(true);
                    ViewManager.soundPool.play(ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
                    delList.add(monster);
                    GameView.player.setHp(GameView.player.getHp() - 5);
                }
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet == null || !bullet.isEffect()) {
                    continue;
                }
                if (monster.isHurt(bullet.getX(), bullet.getY())) {
                    bullet.setEffect(false);
                    monster.setDie(true);
                    if (monster.getType() == Monster.TYPE_FLY) {
                        ViewManager.soundPool.play(ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
                        KILL_NUMBER++;
                        FIRST_KILL++;
                        selectSound();
                    }
                    if (monster.getType() == Monster.TYPE_MAN) {
                        ViewManager.soundPool.play(ViewManager.soundMap.get(3), 1, 1, 0, 0, 1);
                        KILL_NUMBER++;
                        FIRST_KILL++;
                        selectSound();
                    }
                    delList.add(monster);
                    delBulletList.add(bullet);
                }
            }
            bulletList.removeAll(delBulletList);
            monster.checkBullet();
        }
        dieMonsterList.addAll(delList);
        monsterList.removeAll(delList);
    }
    public static void drawMonster(Canvas canvas) {
        Monster monster=null;
        for(int i=0;i<monsterList.size();i++)
        {
            monster=monsterList.get(i);
            if(monster==null)
            {
                continue;
            }
            monster.draw(canvas);
        }
        List<Monster> delList=new ArrayList<>();
        for(int i=0;i<dieMonsterList.size();i++)
        {
            monster=dieMonsterList.get(i);
            if(monster==null)
            {
                continue;
            }
            monster.draw(canvas);
            if(monster.getDieMaxDrawCount()<=0)
            {
                delList.add(monster);
            }
        }
        dieMonsterList.removeAll(delList);
    }
}
