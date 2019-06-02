package com.lock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.LockSavingRemoving.SharedPreference;
import com.loop.appslocker.R;
import java.util.ArrayList;
import java.util.List;


public class PatternLockView extends ViewGroup {
    private Paint paint;
    private Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private List<Pair<NodeView, NodeView>> lineList;
    private NodeView currentNode;
    private StringBuilder stringbuilder;
    private CallBack callBack;
    private SharedPreference sP;
    private int patternLockSize;
    private int nodeSrc, nodeOnSrc;
    private int lineWidth;
    private int lineColor;

    public PatternLockView(Context context) {
        this(context, null);

    }

    public PatternLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PatternLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PatternLockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        sP=new SharedPreference(context);
        initFromAttributes(attrs, defStyleAttr);
    }



    private int getNodeSource() {
        return nodeSrc;
    }

    private int getNodeOnSource() {
        return nodeOnSrc;
    }

    private int getPatternLockSize() {
        return patternLockSize * patternLockSize;
    }


    private void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
        patternLockSize =sP.patternSize();
        nodeSrc = sP.patternDot();
        if (sP.patternVisibility().equals("invisible")){
            nodeOnSrc=sP.patternDot();
            lineColor=Color.TRANSPARENT;
            lineWidth=sP.patternLineSize();

        }else {
            nodeOnSrc =sP.patternHighlighted();
            lineColor = Color.parseColor(sP.patternLineColor());
            lineWidth = sP.patternLineSize();

        }


        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineColor);
        paint.setAntiAlias(true);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.widthPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        for (int n = 0; n < getPatternLockSize(); n++) {
            NodeView node = new NodeView(getContext(), n + 1);
            addView(node);

        }
        lineList = new ArrayList<Pair<NodeView, NodeView>>();
        stringbuilder = new StringBuilder();

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!changed) {
            return;
        }
        int width = right - left;
        int nodeWidth = (width / patternLockSize);
        int nodePadding = nodeWidth / 10;

        for (int n = 0; n < getPatternLockSize(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            int row = n / patternLockSize;
            int col = n % patternLockSize;
            int l = col * nodeWidth + nodePadding - 10;
            int t = row * nodeWidth + nodePadding - 10;
            int r = col * nodeWidth + nodeWidth - 10;
            int b = row * nodeWidth + nodeWidth - 10;
            node.layout(l, t, r, b);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                NodeView nodeAt = getNodeAt(event.getX(), event.getY());
                if (nodeAt == null && currentNode == null) {
                    return true;
                } else {
                    clearScreenAndDrawList();
                    if (currentNode == null) {
                        currentNode = nodeAt;
                        currentNode.setHighLighted(true);
                        stringbuilder.append(currentNode.getNum());
                    } else if (nodeAt == null || nodeAt.isHighLighted()) {

                        canvas.drawLine(currentNode.getCenterX(), currentNode.getCenterY(), event.getX(), event.getY(), paint);
                    } else {
                        canvas.drawLine(currentNode.getCenterX(), currentNode.getCenterY(), nodeAt.getCenterX(), nodeAt.getCenterY(), paint);
                        nodeAt.setHighLighted(true);
                        Pair<NodeView, NodeView> pair = new Pair<NodeView, NodeView>(currentNode, nodeAt);
                        lineList.add(pair);
                        currentNode = nodeAt;
                        stringbuilder.append(currentNode.getNum());
                    }
                    invalidate();
                }
                return true;

            case MotionEvent.ACTION_UP:

                if (stringbuilder.length() <= 0) {
                    return super.onTouchEvent(event);
                }

                if (callBack != null) {
                    callBack.onFinish(stringbuilder.toString());
                    stringbuilder.setLength(0);
                }

                currentNode = null;
                lineList.clear();
                clearScreenAndDrawList();

                for (int n = 0; n < getChildCount(); n++) {
                    NodeView node = (NodeView) getChildAt(n);
                    node.setHighLighted(false);
                }

                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<NodeView, NodeView> pair : lineList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(), pair.second.getCenterX(), pair.second.getCenterY(), paint);
        }
    }

    private NodeView getNodeAt(float x, float y) {

        for (int n = 0; n < getChildCount(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            if (!(x >= node.getLeft() && x < node.getRight())) {
                continue;
            }
            if (!(y >= node.getTop() && y < node.getBottom())) {
                continue;
            }
            return node;
        }
        return null;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onFinish(String password);
    }

    public class NodeView extends View {

        private int num;
        private boolean highLighted;

        private NodeView(Context context) {
            super(context);
        }

        public NodeView(Context context, int num) {
            this(context);
            this.num = num;
            highLighted = false;
            if (getNodeSource() == 0) {
                setBackgroundResource(0);
            } else {
                setBackgroundResource(getNodeSource());
            }
        }

        public boolean isHighLighted() {
            return highLighted;
        }

        public void setHighLighted(boolean highLighted) {
            this.highLighted = highLighted;
            if (highLighted) {
                if (getNodeOnSource() == 0) {
                    setBackgroundResource(0);
                } else {
                    setBackgroundResource(getNodeOnSource());

                }
            } else {
                if (getNodeSource() == 0) {
                    setBackgroundResource(0);
                } else {
                    setBackgroundResource(getNodeSource());
                }
            }
        }

        public int getCenterX() {
            return (getLeft() + getRight()) / 2;
        }

        public int getCenterY() {
            return (getTop() + getBottom()) / 2;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}