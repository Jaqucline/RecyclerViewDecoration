package com.ysl.recyclerviewdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import org.w3c.dom.Text;

/**
 * Created on 2017/6/27.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration{

    private int mGroupTextColor = Color.GRAY;
    private int mGroupBackground = Color.WHITE;
    private int mGroupHeight = 80;
    private int mTextSize = 40;
    private int mLeftMargin = 10;
    private GetProvinceName mGetProvinceName;

    //设置画笔
    private TextPaint mTextPaint;
    private Paint mGroupPaint;

    public MyItemDecoration(GetProvinceName getProvinceName) {
        this.mGetProvinceName = getProvinceName;
        //设置粘性头部画笔
        mGroupPaint = new Paint();
        mGroupPaint.setColor(mGroupBackground);
        //设置文字画笔
        mTextPaint = new TextPaint();
        //抗锯齿
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mGroupTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(TextPaint.Align.LEFT);
    }

    public String getProvinceName(int position){
        if (mGetProvinceName != null){
           return mGetProvinceName.getProvinceName(position);
        }
        return null;
    }
    /**
     * 判断是否在同一个分组
     *
     * @param position
     * @return
     */
    public boolean isFirstGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            String proGroupName = getProvinceName(position - 1);
            String nowGroupName = getProvinceName(position);
            return !TextUtils.equals(proGroupName, nowGroupName);
        }
    }

    /**
     * 获取偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        String provinceName = getProvinceName(position);
        if (TextUtils.isEmpty(provinceName)) {
            return;
        }
        if (position == 0 || isFirstGroup(position)) {
            outRect.top = mGroupHeight;
        }
    }

    /**
     * 在画布上绘制内容在item之前
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 在画布上绘制，在item之前
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getLeft() + parent.getPaddingLeft();
        int right = parent.getRight() - parent.getPaddingRight();

        String preGroupName;
        String nowGroupName = null;

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            preGroupName = nowGroupName;
            nowGroupName = getProvinceName(childAdapterPosition);
            if (nowGroupName == null || TextUtils.equals(preGroupName,nowGroupName)) {
                continue;
            }
            int viewBottom = view.getBottom();
            int top = Math.max(mGroupHeight, view.getTop());
            if (childAdapterPosition + 1<itemCount ){
                //获取下一个GroupName
                String nextGroupName = getProvinceName(childAdapterPosition +1);
                if (!TextUtils.equals(nowGroupName,nextGroupName) && viewBottom <top){
                    top = viewBottom;
                }
            }
            //根据top绘制group
            c.drawRect(left,top - mGroupHeight,right,top,mGroupPaint);
            Paint.FontMetrics  fm = mTextPaint.getFontMetrics();
            //文字居中显示
            float baseLine = top - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(nowGroupName,left + mLeftMargin,baseLine,mTextPaint);
        }
    }
}
