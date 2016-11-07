package ejercicios.com.renderapp;

import android.animation.ValueAnimator;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.media.Image;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity{
//        AppCompatActivity  implements
//        GestureOverlayView.OnGesturePerformedListener{
    private Rendered rendered;
    private LoadObject loadObject;
//    private Movimiento movimiento;
    private GestureLibrary gesture;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

//        setAnimation();
         setObject();
//         setBack();
        image1 =(ImageView) findViewById(R.id.back1);
        image2 =(ImageView) findViewById(R.id.back2);
        image3 = (ImageView) findViewById(R.id.back3);
        image4 = (ImageView) findViewById(R.id.back4);

        animator(image1, image2, image3, image4);

    }



    public void  setBack(){
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(100);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_CONTINUOUSLY);
        surface.setTransparent(true);

        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        surface.setSurfaceRenderer(new AnimatedBackground(this));


    }
    public void setAnimation(){
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(100);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_CONTINUOUSLY);
        surface.setTransparent(true);

        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        surface.setSurfaceRenderer(new AnimationClass(this));


    }

    public void setObject(){
        float oldx, oldy;

        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(50);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);
        surface.setTransparent(true);
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Log.i("Mode",  "x: " + motionEvent.getX());
                //Log.i("Mode",  "y: " + motionEvent.getY());
                //Log.i("Mode",  "z: " + motionEvent.getY());
                //  rendered.movimientoEjes((int)motionEvent.getX(), (int)motionEvent.getY(), 0);
                if(motionEvent.getAction() ==MotionEvent.ACTION_DOWN){

                    loadObject.setInitialMove(motionEvent.getX(), motionEvent.getY());

                }
                else
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    loadObject.movimientoEjes(motionEvent.getX(),
                            motionEvent.getY());

                    Log.i("coordenada",  "x: " + motionEvent.getX());
                    Log.i("coordenada",  "y: " + motionEvent.getY());

//                    loadObject.getScaled();

                    loadObject.getScaledByDistance(motionEvent);

                    loadObject.setInitialMove(motionEvent.getX(), motionEvent.getY());
                }

                return true;

            }
        });

        surface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadObject.setInitialMove(0, 0);
            }
        });
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        loadObject = new LoadObject(this);

        surface.setSurfaceRenderer(loadObject);
        surface.bringToFront();


    }


    public void setObjectAwd(){
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);
        surface.setTransparent(true);
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Log.i("Mode",  "x: " + motionEvent.getX());
                //Log.i("Mode",  "y: " + motionEvent.getY());
                //Log.i("Mode",  "z: " + motionEvent.getY());
                //  rendered.movimientoEjes((int)motionEvent.getX(), (int)motionEvent.getY(), 0);
               if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    rendered.movimientoEjes((int)motionEvent.getX(),
                            (int)motionEvent.getY(), 0);

                    Log.i("Mode",  "x: " + motionEvent.getX());
                }

                return true;

            }
        });
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        rendered = new Rendered(this);
        surface.setSurfaceRenderer(rendered);

    }

//
//    public void setGestures(){
//
//        GestureOverlayView _gesture = new GestureOverlayView(this);
//        View inflate = getLayoutInflater().inflate(R.layout.activity_main, null);
//        _gesture.addView(inflate);
//        _gesture.addOnGesturePerformedListener(this);
//        gesture = GestureLibraries.fromRawResource(this, R.raw.gestures);
//        if(!gesture.load()){
//            finish();
//        }
//        //setContentView(_gesture);
//    }



//    @Override
//    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
//        Log.i("Movement", "Movement");
//    }
//
//
//    public interface Movimiento{
//        void movimientoEjes(int x, int y, int z);
//    }


    public void animator(final ImageView back1, final ImageView back2,
    final ImageView back3, final ImageView back4){
        final ValueAnimator animator = ValueAnimator.ofFloat (0.0f, 1.0f, 2.0f,3.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(40000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final float progress = (float) valueAnimator.getAnimatedValue();
                final float width = back1.getWidth();
                final float translation = width * progress;
                back1.setTranslationX(translation);
                back2.setTranslationX(translation - width);
                back3.setTranslationX(translation - (width*2));
                back4.setTranslationX(translation - (width*3));
            }
        });
        animator.start();
    }


}
