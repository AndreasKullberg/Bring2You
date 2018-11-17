package com.example.ateam.bring2you;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A canvas to sign recieved.
 */
public class CanvasFragment extends Fragment {
    //Create a Canvas member variable mCanvas
    private Canvas myCanvas;

    private Paint myPaint;

    private Paint myPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    private Bitmap myBitmap;

    private ImageView myImageView;

    private Rect myRect = new Rect();
    private Rect myBounds = new Rect();

    private static final int OFFSET = 120;
    private int myOffset = OFFSET;

    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;


    public CanvasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canvas, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);

        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);

        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);
    }
}
