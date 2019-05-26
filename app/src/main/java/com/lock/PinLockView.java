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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.loop.appslocker.R;

import java.util.ArrayList;
import java.util.List;


public class PinLockView extends ViewGroup {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private List<Pair<NodeView, NodeView>> lineList;
    private NodeView currentNode;
    private StringBuilder stringbuilder;
    private CallBack callBack;
    private int patternLockSize = 3;
    private int nodeSrc, nodeOnSrc;
    private int normal[] = {R.drawable.white_normal, R.drawable.green_normal, R.drawable.blue_normal, R.drawable.red_normal, R.drawable.black_normal};
    private int highlighted[] = {R.drawable.white_highlight, R.drawable.green_high_lighted, R.drawable.blue_highlighted, R.drawable.red_highlighted, R.drawable.black_highlight};


    public PinLockView(Context context) {
        this(context, null);
    }

    public PinLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PinLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PinLockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(attrs, defStyleAttr);
    }

    public void setNodeAtNormal(int position) {
        this.nodeSrc = normal[position];
    }

    public void setNodeOnHighlighted(int position) {
        this.nodeOnSrc = highlighted[position];
    }

    private int getNodeSource() {
        return nodeSrc;
    }

    private int getNodeOnSource() {
        return nodeOnSrc;
    }

    private int getPatternLockSize() {
        return 12;
    }

    public void setPatternLockSize(int patternLockSize) {
        this.patternLockSize = patternLockSize;
    }

    private void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PatternLockView, defStyleAttr, 0);
        patternLockSize = a.getInt(R.styleable.PatternLockView_patternSize, 3);
        int lineColor = Color.argb(0, 0, 0, 0);
        lineColor = a.getColor(R.styleable.PatternLockView_lineColor, lineColor);
        float lineWidth = 20.0f;
        lineWidth = a.getDimension(R.styleable.PatternLockView_lineWidth, lineWidth);

        a.recycle();

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineColor);
        paint.setAntiAlias(true);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.widthPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        for (int n = 0; n < 12; n++) {
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
        int height=bottom-top;
        int dif=(height-width)/8;
        int nodeVerticalPadding=dif*3;
        int nodeWidth = (width / patternLockSize)- (width/12);
        int expad=nodeWidth/20;
        int nodePadding = nodeWidth / 10;
        for (int n = 0; n < 12; n++) {
            NodeView node = (NodeView) getChildAt(n);
            int row = n / patternLockSize;
            int col = n % patternLockSize;
            int l = col * nodeWidth + nodePadding+(width/11);
            int t = row * nodeWidth + nodePadding+dif -expad+nodeVerticalPadding;
            int r = col * nodeWidth + nodeWidth - expad+(width/11);
            int b = row * nodeWidth + nodeWidth+dif -expad+nodeVerticalPadding;
            node.layout(l, t, r, b);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onFinish(String password);
    }

    public class NodeView extends android.support.v7.widget.AppCompatButton {

        private int num;
        private boolean highLighted;

        private NodeView(Context context) {
            super(context);
        }

        public NodeView(Context context, int num) {
            this(context);
            this.num = num;
            highLighted = false;
        }
    }
}