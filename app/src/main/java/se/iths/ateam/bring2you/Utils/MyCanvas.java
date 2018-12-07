package se.iths.ateam.bring2you.Utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import se.iths.ateam.bring2you.Activities.MainActivity;
import se.iths.ateam.bring2you.R;


/**
 * A canvas to sign recieved.
 */
public class MyCanvas extends View {

    Canvas myCanvas;

    public Canvas getMyCanvas() {
        return myCanvas;
    }

    public Paint getMyPaint() {
        return myPaint;
    }

    public Path getPath() {
        return path;
    }

    Paint myPaint = new Paint();
    Path path = new Path();
    private RecyclerView mRecyclerView;


    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.BLACK);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(5f);

    }



    @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Rect r = new Rect(0,0,this.getWidth(), this.getHeight());
                canvas.drawRect(r, myPaint);
                canvas.drawPath(path ,myPaint);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                mRecyclerView = getRootView().findViewById(R.id.recyclerView);
                mRecyclerView.requestDisallowInterceptTouchEvent(true);
                float xPos = event.getX();
                float yPos = event.getY();

                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(xPos,yPos);
                        return true;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;

            case MotionEvent.ACTION_UP:
                mRecyclerView.requestDisallowInterceptTouchEvent(false);
                break;

            default:
                    return false;

        }
        invalidate();
        return true;
    }

    public void clear(){
        path.rewind();
        invalidate();
    }

}
