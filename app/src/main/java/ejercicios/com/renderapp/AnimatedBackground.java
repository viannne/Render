package ejercicios.com.renderapp;

import android.content.Context;
import android.view.MotionEvent;

import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;

import java.util.Vector;

/**
 * Created by Gigio on 03/11/16.
 */
public class AnimatedBackground extends RajawaliRenderer {
    public AnimatedBackground(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {
        getCurrentCamera().setFarPlane(100);
        try{
            getCurrentScene().setSkybox(R.drawable.posx, R.drawable.negx,
                    R.drawable.posy, R.drawable.negy, R.drawable.posz,
                    R.drawable.negz);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        getCurrentCamera().rotate(Vector3.Axis.Y, -0.2);
    }
}
