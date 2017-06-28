package com.ysl.recyclerviewdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

/**
 * Created on 2017/6/27.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration implements GetProvinceName {

    private int mGroupHeight = 30;

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
        String nowGroupName;

//        for (int i = 0; i < ; i++) {
//
//        }
    }


    @Override
    public String getProvinceName(int position) {
        return null;
    }
}
