package com.lock;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemSpace extends RecyclerView.ItemDecoration {
    private int verticalSpace,horizontalSpace;

    public ItemSpace(int verticalSpace,int horizontalSpace) {
        this.verticalSpace=verticalSpace;
        this.horizontalSpace=horizontalSpace;

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % 3;
        outRect.left = column * horizontalSpace / 3;
        outRect.right = horizontalSpace - (column + 1) * horizontalSpace/3;
        if (position >= 3) {
            outRect.top = verticalSpace;
        }
    }
}
