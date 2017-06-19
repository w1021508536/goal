package com.pi.small.goal.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.pi.small.goal.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JS on 2017-06-19.
 * 分销多曲线
 */

public class CurveMuchChartView extends View {

    Paint linePaint;
    Paint textPaint;
    Paint chartLinePaint;
    Paint chartJianbianPaint;


    public CurveMuchChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CurveMuchChartView(Context context) {
        this(context, null);
    }

    public CurveMuchChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    PathEffect effect;
    Path path;

    private void init() {
        linePaint = new Paint();
        textPaint = new Paint();
        chartLinePaint = new Paint();
        chartJianbianPaint = new Paint();

        //设置绘制模式为-虚线作为背景线。
        effect = new DashPathEffect(new float[]{6, 6, 6, 6, 6}, 2);
        //背景虚线路径.
        path = new Path();
        //只是绘制的XY轴
        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setStrokeWidth((float) 0.7);  //#F5F5F5
        linePaint.setStrokeWidth((float) 1.0);             //设置线宽
        linePaint.setColor(getResources().getColor(R.color.basefundchatview_line));
        linePaint.setAntiAlias(true);// 锯齿不显示

        //XY刻度上的字
        textPaint.setStyle(Paint.Style.FILL);// 设置非填充
        textPaint.setStrokeWidth(1);// 笔宽10像素
        textPaint.setColor(getResources().getColor(R.color.black));// 设置yanse
        textPaint.setAntiAlias(true);// 锯齿不显示
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(35);


    }

    /**
     * 重要参数，两点之间分为几段描画，数字愈大分段越多，描画的曲线就越精细.
     */
    private static final int STEPS = 12;

    float gridX, gridY, xSpace = 0, ySpace = 0, spaceYT = 0, yStart = 0;
    List<String> dataX = null;
    List<Float> dataY = null;

    List<List<Float>> data = null;

    List<Integer> colors = null;

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public List<Float> getDateY() {
        return dataY;
    }

    public void setDateY(List<Float> dataY) {
        this.dataY = dataY;
    }

    public List<List<Float>> getData() {
        return data;
    }

    public void setData(List<List<Float>> data) {
        this.data = data;
    }

    public List<String> getDateX() {
        return dataX;
    }

    public void setDateX(List<String> dataX) {
        this.dataX = dataX;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //基准点。
        gridX = 0;
        gridY = getHeight();
//        gridY = getHeight() - 50;
        //XY间隔。
        if (dataX != null && dataX.size() > 0) {
            xSpace = (getWidth() - gridX - 30) / (dataX.size() - 1);
        }

        if (dataY != null && dataY.size() > 0) {
            ySpace = (gridY - 70) / (dataY.size() - 1);
            yStart = dataY.get(0);
            if (dataY.size() > 2) {
                spaceYT = Math.abs(dataY.get(1) - dataY.get(0));
            }
        }


        //画Y轴(带箭头)。
        canvas.drawLine(gridX, gridY - 20 - 10, gridX, 30 + 10, linePaint);
//        canvas.drawLine(gridX, 30 + 10, gridX - 6, 30 + 14 + 10, linePaint);//Y轴箭头。
//        canvas.drawLine(gridX, 30 + 10, gridX + 6, 30 + 14 + 10, linePaint);

        float y = 0;
        //画X轴。
        y = gridY ;
        canvas.drawLine(gridX, y - 10, getWidth() - 30, y - 10, linePaint);//X轴.


        //绘制X刻度坐标。
        float x = 0;
        if (dataX != null) {
            for (int n = 1; n < dataX.size(); n++) {
                //取X刻度坐标.
                x = gridX + (n) * xSpace;//在原点(0,0)处也画刻度（不画的话就是n+1）,向右移动一个跨度。
                //画X轴具体刻度值。
                if (dataX.get(n) != null) {
                    canvas.drawText(dataX.get(n), (float) (x-xSpace*0.5), 65, textPaint);//X具体刻度值。
                    canvas.drawLine(x, gridY, x, 65 + 10, linePaint);
//                    if (x != gridX) {
//                        //       linePaint.setPathEffect(effect);//设法虚线间隔样式。
//                        //画除X轴之外的------背景虚线一条-------
//                        path.moveTo(gridX, gridY - 20 - 10);//背景【虚线起点】。
//                        path.lineTo(gridX, getHeight());//背景【虚线终点】。
//                        canvas.drawPath(path, linePaint);
//                    }
                }
            }
        }

        if (data != null && data.size() > 0) {

            float currentPointX = 0;//当前点
            float currentPointY = 0;
            for (int n = 0; n < data.size(); n++) {

                if (colors.get(n) != null) {
                    //绘制的折线
                    chartLinePaint.setStyle(Paint.Style.STROKE);
                    chartLinePaint.setStrokeWidth(5);
                    chartLinePaint.setColor(colors.get(n));// 设置yanse
                    chartLinePaint.setAntiAlias(true);

                    //绘制的折线
                    chartJianbianPaint.setStyle(Paint.Style.FILL);
                    chartJianbianPaint.setStrokeWidth(5);
                    chartJianbianPaint.setColor(colors.get(n));

                    chartJianbianPaint.setAntiAlias(true);
                }

                List<Float> da = data.get(n);
                List<Float> da_x = new ArrayList<>();
                List<Float> da_y = new ArrayList<>();
                /**
                 * 曲线路劲
                 */
                Path curvePath = new Path();
                /**
                 * 渐变色路径
                 */
                Path jianBianPath = new Path();

                for (int m = 0; m < da.size(); m++) {
                    currentPointX = m * xSpace + gridX;
//                    currentPointY = gridY - 30 - ((da.get(m) - yStart) * (ySpace / spaceYT));
                    currentPointY = gridY  - ((da.get(m) - yStart) * (ySpace / spaceYT));
                    da_x.add(currentPointX);
                    da_y.add(currentPointY);
                }
                List<Cubic> calculate_y = calculate(da_y);
                List<Cubic> calculate_x = calculate(da_x);
                curvePath.moveTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));
                jianBianPath.moveTo(gridX, gridY );
                jianBianPath.lineTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));

                float lastx = 0;
                for (int i = 0; i < calculate_x.size(); i++) {
                    for (int j = 1; j <= STEPS; j++) {
                        float u = j / (float) STEPS;
                        curvePath.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i)
                                .eval(u));
                        jianBianPath.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i)
                                .eval(u));
                        lastx = calculate_x.get(i).eval(u);
                    }
                }
                jianBianPath.lineTo(lastx, gridY );
                canvas.drawPath(curvePath, chartLinePaint);
                Shader mShader = new LinearGradient(0, 30 + 10, 0, gridY, new int[]{colors.get(n), colors.get(n)}, null, Shader.TileMode.CLAMP);
//新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变

                chartJianbianPaint.setShader(mShader);
                canvas.drawPath(jianBianPath, chartJianbianPaint);


            }
        }


    }

    /**
     * 计算曲线.
     *
     * @param x
     * @return
     */
    private List<Cubic> calculate(List<Float> x) {
        int n = x.size() - 1;
        float[] gamma = new float[n + 1];
        float[] delta = new float[n + 1];
        float[] D = new float[n + 1];
        int i;
        /*
         * We solve the equation [2 1 ] [D[0]] [3(x[1] - x[0]) ] |1 4 1 | |D[1]|
         * |3(x[2] - x[0]) | | 1 4 1 | | . | = | . | | ..... | | . | | . | | 1 4
         * 1| | . | |3(x[n] - x[n-2])| [ 1 2] [D[n]] [3(x[n] - x[n-1])]
         *
         * by using row operations to convert the matrix to upper triangular and
         * then back sustitution. The D[i] are the derivatives at the knots.
         */

        gamma[0] = 1.0f / 2.0f;
        for (i = 1; i < n; i++) {
            gamma[i] = 1 / (4 - gamma[i - 1]);
        }
        gamma[n] = 1 / (2 - gamma[n - 1]);

        delta[0] = 3 * (x.get(1) - x.get(0)) * gamma[0];
        for (i = 1; i < n; i++) {
            delta[i] = (3 * (x.get(i + 1) - x.get(i - 1)) - delta[i - 1])
                    * gamma[i];
        }
        delta[n] = (3 * (x.get(n) - x.get(n - 1)) - delta[n - 1]) * gamma[n];

        D[n] = delta[n];
        for (i = n - 1; i >= 0; i--) {
            D[i] = delta[i] - gamma[i] * D[i + 1];
        }

        /* now compute the coefficients of the cubics */
        List<Cubic> cubics = new LinkedList<Cubic>();
        for (i = 0; i < n; i++) {
            Cubic c = new Cubic(x.get(i), D[i], 3 * (x.get(i + 1) - x.get(i))
                    - 2 * D[i] - D[i + 1], 2 * (x.get(i) - x.get(i + 1)) + D[i]
                    + D[i + 1]);
            cubics.add(c);
        }
        return cubics;
    }

    class Cubic {
        float a, b, c, d;         /* a + b*u + c*u^2 +d*u^3 */

        public Cubic(float a, float b, float c, float d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }


        /**
         * evaluate cubic
         */
        public float eval(float u) {
            return (((d * u) + c) * u + b) * u + a;
        }
    }

}
