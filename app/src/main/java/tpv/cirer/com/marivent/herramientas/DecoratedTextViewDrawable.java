package tpv.cirer.com.marivent.herramientas;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.TextView;

/**
 * Created by JUAN on 27/02/2017.
 */

public class DecoratedTextViewDrawable extends LayerDrawable {
    private int mCnt = 0;
    private Paint mPaint;
    private TextView mParent;
    private ColorStateList mColors;
    private Rect mBounds;
    private String mModelo;

    public DecoratedTextViewDrawable(TextView tv, Drawable[] layers, int cnt, String modelo) {
        super(layers);
        mParent = tv;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(tv.getTextSize());
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int[][] states = {
                {android.R.attr.state_pressed}, {android.R.attr.state_focused}, {}
        };
        int[] colors = {
                0xff0000aa, 0xff880000, 0xff00aa00
        };
        mColors = new ColorStateList(states, colors);
        mBounds = new Rect();
        setCnt(cnt);

        mModelo=modelo;
    }

    public void setCnt(int cnt) {
        mCnt = cnt;
        String s = Integer.toString(cnt);
        mPaint.getTextBounds(s, 0, s.length(), mBounds);
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        invalidateSelf();
        return super.onStateChange(state);
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float x = mPaint.getTextSize() * 1.5f;
        float r = mPaint.getTextSize() * 0.9f;
        int base = mParent.getBaseline();
        int[] stateSet = getState();
//        Log.d(TAG, "draw " + StateSet.dump(stateSet));
        int color = mColors.getColorForState(stateSet, 0xff000000);
        if (mModelo.equals("PEDIDOS")) {
            mPaint.setColor(color);
        }else{
            mPaint.setColor(Filtro.getColorItem());
        }

        canvas.drawCircle(x, base + mBounds.top + mBounds.height() / 2, r, mPaint);
        mPaint.setColor(0xffeeeeee);
        canvas.drawText(Integer.toString(mCnt), x, base, mPaint);

    }
}