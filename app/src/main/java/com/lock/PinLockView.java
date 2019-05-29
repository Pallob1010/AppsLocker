package com.lock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PinLockView extends RecyclerView {
    Context context;
    private CallBack callBack;
    private NumberHoldingAdapter numberHoldingAdapter;
    public PinLockView(@NonNull Context context) {
        super(context);
        this.context = context;
        initials();


    }

    public PinLockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initials();
    }

    public PinLockView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initials();

    }

    private void initials() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        setLayoutManager(gridLayoutManager);
        numberHoldingAdapter = new NumberHoldingAdapter(getContext());
        setAdapter(numberHoldingAdapter);
        addItemDecoration(new ItemSpace(getPaddingTop(), getPaddingLeft()));


    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
        numberHoldingAdapter.addListener(callBack);
    }

    public interface CallBack {
        void onNumberClicked(String number);

        void onArrowClicked();

        void onCrossClicked();
    }
}
