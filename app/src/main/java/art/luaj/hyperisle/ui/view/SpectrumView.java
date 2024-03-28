package art.luaj.hyperisle.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SpectrumView extends View {
    private Visualizer mVisualizer;
    private byte[] mBytes;
    private Paint mPaint;

    public SpectrumView(Context context) {
        super(context);
        init();
    }

    public SpectrumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpectrumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPlayerId(int sessionID) {
        try {
            if (mVisualizer != null) {
                release();
                mVisualizer = null;
            }
            mVisualizer = new Visualizer(sessionID);
            mVisualizer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
            mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                    setByte(bytes);
                    //invalidate();
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                }
            }, Visualizer.getMaxCaptureRate() / 2, true, false);
            mVisualizer.setEnabled(true);
        } catch (Exception e) {

        }
    }

    public void release() {
        if (mVisualizer == null) {
            return;
        }
        mVisualizer.release();
        mBytes = null;
        invalidate();
    }

    private void setByte(byte[] bytes) {
        this.mBytes = bytes;
    }

    private void init() {
        mPaint = new Paint();
        setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int Color) {
        mPaint.setColor(Color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float density = 8;

        if (mBytes != null) {
            float barWidth = getWidth() / density;
            float div = mBytes.length / density;
            mPaint.setStrokeWidth(barWidth - 4);
            for (int i = 0; i < density; i++) {
                int bytePosition = (int) Math.ceil(i * div);
                float barX = (i * barWidth) + (barWidth / 2);
                if (mBytes[bytePosition] == 0 || mBytes[bytePosition] + 128 == 0) {
                    canvas.drawLine(barX, (getHeight() / 2f), barX, (getHeight() / 2f), mPaint);
                } else {
                    int top = (getHeight() - 20) + ((byte) (Math.abs(mBytes[bytePosition]) + 128)) * (getHeight() - 20) / 128;
                    canvas.drawLine(barX, ((getHeight() + 20) - top) / 2f, barX, top, mPaint);
                }
            }
            super.onDraw(canvas);
        }
    }

}
