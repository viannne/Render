package ejercicios.com.renderapp;

import android.content.Context;
import android.view.MotionEvent;
import android.view.animation.Animation;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.EllipticalOrbitAnimation3D;
import org.rajawali3d.animation.RotateAnimation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * Created by USUARIO on 02/11/2016.
 */

public class AnimationClass extends RajawaliRenderer {
    private Object3D house;
    private PointLight mLight;
    private Animation3D cameraAnim,mLightAnim ;

    public AnimationClass(Context context) {
        super(context);
//        setFrameRate(60);
    }

    @Override
    protected void initScene() {
        PointLight light = new PointLight();
        light.setPosition(5,5,4);
        light.setPower(10);
//
        getCurrentCamera().setZ(80);
        getCurrentScene().addLight(light);


        try {
            LoaderOBJ parser = new LoaderOBJ(mContext.getResources(),
                    mTextureManager, R.raw.house_obj);
            parser.parse();

            house = parser.getParsedObject();
            getCurrentScene().addChild(house);

            cameraAnim = new RotateOnAxisAnimation(
                    Vector3.Axis.Y, 80);
//            cameraAnim = new RotateAnimation3D(10,10,10);
            cameraAnim.setDurationMilliseconds(10000);
            cameraAnim.setRepeatCount(Animation.INFINITE);
            cameraAnim.setTransformable3D(house);


            mLightAnim = new EllipticalOrbitAnimation3D(new Vector3(),
                    new Vector3(0, 10, 0),
                    Vector3.getAxisVector(Vector3.Axis.Z),
                    0,
                    360,
                    EllipticalOrbitAnimation3D.OrbitDirection.CLOCKWISE);

            mLightAnim.setDurationMilliseconds(6000);
            mLightAnim.setRepeatMode(org.rajawali3d.animation.Animation.RepeatMode.INFINITE);
            mLightAnim.setTransformable3D(mLight);

            getCurrentScene().registerAnimation(mLightAnim);
            getCurrentScene().registerAnimation(cameraAnim);
            cameraAnim.play();
            mLightAnim.play();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    sed when your renderer is attached to a wallpaper.
    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }
//    sed when your renderer is attached to a wallpaper.
    @Override
    public void onTouchEvent(MotionEvent event) {

    }

//    public void onSurfaceCreated(GL10 gl, EGLConfig config){
//        ((MainActivity) mContext).showLoader();
//        super.onRenderSurfaceCreated(gl, config);
//        ((MainActivity)mContext).hideLoader();
//        cameraAnim.start();
//        mPointLight.start();
//    }
//    public void onDrawFrame(GL10 glUnused){
//        super.onDrawFrame();
//    }

}
