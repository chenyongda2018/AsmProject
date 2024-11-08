package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;


public class NodeProgressView extends View {
    private Paint bgPaint;
    private Paint proPaint;
    private float bgRadius;
    private float proRadius;
    private float startX;
    private float stopX;
    private float bgCenterY;
    private int lineBgWidth;
    private int bgColor;
    private int lineProWidth;
    private int proColor;
    private int textColor;
    private int textPadding;
    private int maxStep;
    private int textSize;
    private int proStep;
    private float interval;
    private Integer[] nodes = new Integer[]{5, 15, 25};
    private String[] titles = {"累计", "5", "8", "15", "20"};

    public NodeProgressView(Context context) {
        this(context, null);
    }

    public NodeProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressViewH);
        bgRadius = ta.getDimension(R.styleable.ProgressViewH_h_bg_radius, 10);
        proRadius = ta.getDimension(R.styleable.ProgressViewH_h_pro_radius, 8);
        lineBgWidth = (int) ta.getDimension(R.styleable.ProgressViewH_h_bg_width, 3f);
        bgColor = ta.getColor(R.styleable.ProgressViewH_h_bg_color, Color.parseColor("#CCCCCC"));
        lineProWidth = (int) ta.getDimension(R.styleable.ProgressViewH_h_pro_width, 2f);
        proColor = ta.getColor(R.styleable.ProgressViewH_h_pro_color, Color.parseColor("#029dd5"));
        textPadding = (int) ta.getDimension(R.styleable.ProgressViewH_h_text_padding, 30);
        maxStep = ta.getInt(R.styleable.ProgressViewH_h_max_step, 5);
        textSize = (int) ta.getDimension(R.styleable.ProgressViewH_h_text_size, 20);
        textColor = ta.getColor(R.styleable.ProgressViewH_h_text_color, Color.parseColor("#272718"));
        proStep = ta.getInt(R.styleable.ProgressViewH_h_pro_step, 1);
        ta.recycle();
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(lineBgWidth);
        bgPaint.setTextSize(textSize);
        bgPaint.setTextAlign(Paint.Align.CENTER);

        proPaint = new Paint();
        proPaint.setAntiAlias(true);
        proPaint.setStyle(Paint.Style.FILL);
        proPaint.setColor(proColor);
        proPaint.setStrokeWidth(lineProWidth);
        proPaint.setTextSize(textSize);
        proPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        float bgWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            bgWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        } else {
            bgWidth = dip2px(getContext(), 311);
        }

        float bgHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            bgHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        } else {
            bgHeight = dip2px(getContext(), 49);
        }
        float left = getPaddingLeft() + bgRadius;
        stopX = bgWidth - bgRadius;
        startX = left;
        bgCenterY = bgHeight / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        interval = ((stopX - startX) / (float) maxStep);
        drawBg(canvas);
        drawProgress(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        int index = 0;
        bgPaint.setColor(textColor);
        for (int i = 0; i <= maxStep; i++) {
            if (isContains(i)) {
                if (null != titles && index < titles.length) {
                    String title = titles[index++];
                    if (null == title) {
                        continue;
                    }
                    canvas.drawText(title, startX + (i * interval), bgCenterY + textPadding, bgPaint);
                }
            }
        }
    }

    private boolean isContains(int i) {
        boolean status = false;
        for (int value : nodes) {
            if (value == i) {
                status = true;
                break;
            }
        }
//        return status || i == maxStep;
        return status;
    }


    private void drawProgress(Canvas canvas) {
        float lastLeft = startX;
        if (proStep > maxStep) {
            proStep = maxStep;
        }
        canvas.drawLine(lastLeft, bgCenterY, lastLeft + interval * proStep, bgCenterY, proPaint);
        for (int i = 0; i <= proStep; i++) {
            if (isContains(i) && proStep != 0) {
                lastLeft = lastLeft + interval;
                canvas.drawCircle(startX + (i * interval), bgCenterY, proRadius, proPaint);
            }
        }
    }

    private void drawBg(Canvas canvas) {
        bgPaint.setColor(bgColor);
        canvas.drawLine(startX, bgCenterY, stopX, bgCenterY, bgPaint);
        for (int i = 0; i <= maxStep; i++) {
            if (isContains(i)) {
                canvas.drawCircle(startX + (i * interval), bgCenterY, bgRadius, proPaint);
            }
        }
    }

    /**
     * 进度设置
     *
     * @param progress 已完成到哪部
     * @param maxStep  总步骤
     * @param nodes    需要显示对应节
     * @param titles   步骤名称
     */
    public void initProgress(int progress, int maxStep, Integer[] nodes, String[] titles) {
        proStep = progress;
        this.maxStep = maxStep;
        this.nodes = nodes;
        this.titles = titles;
        invalidate();
    }

    /**
     * 进度设置
     *
     * @param progress 已完成到哪部
     */
    public void setProgress(int progress) {
        proStep = progress;
        invalidate();
    }

    /**
     * 把密度转换为像素
     */
    private int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    /**
     * 得到设备的密度
     */
    private float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
