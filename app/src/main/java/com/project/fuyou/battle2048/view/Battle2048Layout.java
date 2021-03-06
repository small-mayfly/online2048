package com.project.fuyou.battle2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 2048的游戏面板，加入布局文件即可开始游戏
 * Created by fuyou on 2017/3/29.
 */
public class Battle2048Layout extends RelativeLayout
{
    /**
     * 初始化接口变量
     */
    MoveActionListener moveActionListener=null;

    /**
     * 自定义控件的自定义事件
     * @param moveActionListener 接口类型
     */
    public void setonFling(MoveActionListener moveActionListener)
    {
        this.moveActionListener = moveActionListener;
    }

    /**
     * 设置Item的数量n*n；默认为5
     */
    private int mColumn = 5;
    /**
     * 存放所有的Item
     */
    public Battle2048Item[][] mGame2048Items;

    /**
     * Item横向与纵向的边距
     */
    private int mMargin = 10;
    /**
     * 面板的padding
     */
    private int mPadding;
    /**
     * 检测用户滑动的手势
     */
    private GestureDetector mGestureDetector;

    /**
     * 运动方向的枚举
     */
    public enum ACTION
    {
        LEFT, RIGHT, UP, DOWM
    }

    private List<Integer> tempValKey;
    private List<Integer> tempValVal;

    /**
     * 记录新生成数字
     */
    public String randomNum;

    public static boolean isYourTurn=false;

    public Battle2048Layout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mMargin, getResources().getDisplayMetrics());
        // 设置Layout的内边距，四边一致，设置为四内边距中的最小值
        mPadding = min(getPaddingLeft(), getPaddingTop(), getPaddingRight(),getPaddingBottom());
        mGestureDetector = new GestureDetector(context , new MyGestureDetector());

    }

    public void shiftLeft(){
        for (int i=0;i<mColumn;i++){
            for (int j=0;j<mColumn;j++){//将非空元素存入一个链表中
                if (mGame2048Items[i][j].getNumber()!=0){
                    tempValKey.add(mGame2048Items[i][j].getNumber());
                    mGame2048Items[i][j].setNumber(0);
                }
            }
            for (int j=0;j<tempValKey.size();j++){//将相邻的相同数字进行合并
                int temp=j;
                if (temp+2>tempValKey.size()){
                    tempValVal.add(tempValKey.get(temp));
                }else{
                    if (tempValKey.get(temp)==tempValKey.get(temp+1)){
                        tempValVal.add(tempValKey.get(temp)*2);
                        j++;
                    }else{
                        tempValVal.add(tempValKey.get(temp));
                    }
                }
            }
            for (int j=0;j<tempValVal.size();j++){//将处理好的数字放回数组中
                mGame2048Items[i][j].setNumber(tempValVal.get(j));
            }
            tempValKey.clear();
            tempValVal.clear();
        }
    }

    public void shiftRight(){
        for (int i=0;i<mColumn;i++){
            for (int j=0;j<mColumn;j++){//将非空元素存入一个链表中
                if (mGame2048Items[i][mColumn-1-j].getNumber()!=0){
                    tempValKey.add(mGame2048Items[i][mColumn-1-j].getNumber());
                    mGame2048Items[i][mColumn-1-j].setNumber(0);
                }
            }
            for (int j=0;j<tempValKey.size();j++){//将相邻的相同数字进行合并
                int temp=j;
                if (temp+2>tempValKey.size()){
                    tempValVal.add(tempValKey.get(temp));
                }else{
                    if (tempValKey.get(temp)==tempValKey.get(temp+1)){
                        tempValVal.add(tempValKey.get(temp)*2);
                        j++;
                    }else{
                        tempValVal.add(tempValKey.get(temp));
                    }
                }
            }
            for (int j=0;j<tempValVal.size();j++){//将处理好的数字放回数组中
                mGame2048Items[i][mColumn-1-j].setNumber(tempValVal.get(j));
            }
            tempValKey.clear();
            tempValVal.clear();
        }
    }

    public void shiftUp(){
        for (int i=0;i<mColumn;i++){
            for (int j=0;j<mColumn;j++){//将非空元素存入一个链表中
                if (mGame2048Items[j][i].getNumber()!=0){
                    tempValKey.add(mGame2048Items[j][i].getNumber());
                    mGame2048Items[j][i].setNumber(0);
                }
            }
            for (int j=0;j<tempValKey.size();j++){//将相邻的相同数字进行合并
                int temp=j;
                if (temp+2>tempValKey.size()){
                    tempValVal.add(tempValKey.get(temp));
                }else{
                    if (tempValKey.get(temp)==tempValKey.get(temp+1)){
                        tempValVal.add(tempValKey.get(temp)*2);
                        j++;
                    }else{
                        tempValVal.add(tempValKey.get(temp));
                    }
                }
            }
            for (int j=0;j<tempValVal.size();j++){//将处理好的数字放回数组中
                mGame2048Items[j][i].setNumber(tempValVal.get(j));
            }
            tempValKey.clear();
            tempValVal.clear();
        }
    }

    public void shiftDown(){
        for (int i=0;i<mColumn;i++){
            for (int j=0;j<mColumn;j++){//将非空元素存入一个链表中
                if (mGame2048Items[mColumn-1-j][i].getNumber()!=0){
                    tempValKey.add(mGame2048Items[mColumn-1-j][i].getNumber());
                    mGame2048Items[mColumn-1-j][i].setNumber(0);
                }
            }
            for (int j=0;j<tempValKey.size();j++){//将相邻的相同数字进行合并
                int temp=j;
                if (temp+2>tempValKey.size()){
                    tempValVal.add(tempValKey.get(temp));
                }else{
                    if (tempValKey.get(temp)==tempValKey.get(temp+1)){
                        tempValVal.add(tempValKey.get(temp)*2);
                        j++;
                    }else{
                        tempValVal.add(tempValKey.get(temp));
                    }
                }
            }
            for (int j=0;j<tempValVal.size();j++){//将处理好的数字放回数组中
                mGame2048Items[mColumn-1-j][i].setNumber(tempValVal.get(j));
            }
            tempValKey.clear();
            tempValVal.clear();
        }
    }

    /**
     * 根据用户运动，整体进行移动合并值等
     */
    public void action(ACTION action) {
        tempValKey=new LinkedList<Integer>();
        tempValVal=new LinkedList<Integer>();
        //根据用户的动作调用不同的操作函数，来进行棋子的移动，移动后直接进行相同项合并
        switch (action){
            case LEFT:
                shiftLeft();
                break;
            case RIGHT:
                shiftRight();
                break;
            case UP:
                shiftUp();
                break;
            case DOWM:
                shiftDown();
                break;
            default:
                break;
        }


    }

    /**
     * 得到多值中的最小值
     *
     * @param params
     * @return
     */
    private int min(int... params) {
        int min = params[0];
        for (int param : params)
        {
            if (min > param)
            {
                min = param;
            }
        }
        return min;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public Battle2048Layout(Context context) {
        this(context, null);
    }

    public Battle2048Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private boolean once;

    /**
     * 测量Layout的宽和高，以及设置Item的宽和高，这里忽略wrap_content 以宽、高之中的最小值绘制正方形
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得正方形的边长
        int length = Math.min(getMeasuredHeight(), getMeasuredWidth());
        // 获得Item的宽度
        int childWidth = (length - mPadding * 2 - mMargin * (mColumn - 1))
                / mColumn;

        if (!once) {
            if (mGame2048Items == null) {
                mGame2048Items = new Battle2048Item[mColumn][mColumn];
            }
            // 放置Item
            for (int i = 0; i < mColumn*mColumn; i++) {
                Battle2048Item item = new Battle2048Item(getContext());

                mGame2048Items[i/mColumn][i%mColumn] = item;
                item.setId(i + 1);
                RelativeLayout.LayoutParams lp = new LayoutParams(childWidth,childWidth);
                // 设置横向边距,不是最后一列
                if ((i + 1) % mColumn != 0) {
                    lp.rightMargin = mMargin;
                }
                // 如果不是第一列
                if (i % mColumn != 0) {
                    lp.addRule(RelativeLayout.RIGHT_OF,mGame2048Items[i/mColumn][i%mColumn-1].getId());
                }
                // 如果不是第一行，//设置纵向边距，非最后一行
                if ((i + 1) > mColumn) {
                    lp.topMargin = mMargin;
                    lp.addRule(RelativeLayout.BELOW,mGame2048Items[i/mColumn-1][i%mColumn].getId());
                }
                addView(item, lp);
            }
            generateNum();
        }
        once = true;
        setMeasuredDimension(length, length);
    }

    /**
     * 是否填满数字,check
     *
     * @return
     */
    private boolean isFull() {
        // 检测是否所有位置都有数字
        for (int i=0;i<mColumn;i++){
            for (int j=0;j<mColumn;j++){
                if (mGame2048Items[i][j].getNumber()==0){
                    return false;
                }
            }
        }
        //如果所有位置已满
        for (int i=0;i<mColumn-1;i++) {
            for (int j=0;j<mColumn-1;j++){
                if (mGame2048Items[i][j].getNumber() == mGame2048Items[i][j+1].getNumber() || mGame2048Items[i][j].getNumber() == mGame2048Items[i+1][j].getNumber()) {
                    return false;
                }
            }
        }
//        Toast.makeText(getContext(), "defeat", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 产生一个数字,check
     */
    public void generateNum() {
        int number;
        Random random = new Random();
        int next = random.nextInt(mColumn*mColumn);
        Battle2048Item item = mGame2048Items[next/mColumn][next%mColumn];

        while (item.getNumber() != 0) {
            next = random.nextInt(mColumn*mColumn);
            item = mGame2048Items[next/mColumn][next%mColumn];
        }
        number=Math.random() > 0.75 ? 4 : 2;
        item.setNumber(number);
        randomNum=number+":"+next/mColumn+":"+next%mColumn;
    }

    /**
     * 初始化游戏棋盘
     */
    public void initBoard() {
        //清空棋盘
        for (int i=0;i<mColumn;i++)
            for(int j=0;j<mColumn;j++)
                mGame2048Items[i][j].setNumber(0);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        String flag;

        final int FLING_MIN_DISTANCE = 120;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            if (isYourTurn==true){
                if (x > FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY)) {
                    action(ACTION.RIGHT);
                    flag="RIGHT";
//                Toast.makeText(getContext(), "toRight", Toast.LENGTH_SHORT).show();

                }else if (x < -FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY)) {
                    action(ACTION.LEFT);
                    flag="LEFT";
//                 Toast.makeText(getContext(), "toLeft", Toast.LENGTH_SHORT).show();

                } else if (y > FLING_MIN_DISTANCE && Math.abs(velocityX) < Math.abs(velocityY)) {
                    action(ACTION.DOWM);
                    flag="DOWN";
//                 Toast.makeText(getContext(), "toDown", Toast.LENGTH_SHORT).show();

                } else if (y < -FLING_MIN_DISTANCE && Math.abs(velocityX) < Math.abs(velocityY)) {
                    action(ACTION.UP);
                    flag="UP";
//                 Toast.makeText(getContext(), "toUp", Toast.LENGTH_SHORT).show();
                }
                if (isFull()){//如果棋盘已死则先退出，先退出者失败
                    moveActionListener.getChangeState("exit:end");
                }else{
                    //生成随机数字数字
                    generateNum();
                    //将改变信息返回主程序
                    moveActionListener.getChangeState("game:"+flag+":"+randomNum);
                }
                isYourTurn=false;
            }
            return true;
        }

    }

}
