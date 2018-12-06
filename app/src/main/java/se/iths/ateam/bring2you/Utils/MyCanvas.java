package se.iths.ateam.bring2you.Utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import se.iths.ateam.bring2you.R;


/**
 * A canvas to sign recieved.
 */
public class MyCanvas extends View {

    Canvas myCanvas;
    Paint myPaint = new Paint();
    Path path = new Path();




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
