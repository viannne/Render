package ejercicios.com.renderapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * Created by Gigio on 01/11/16.
 */
public class Rendered extends RajawaliRenderer {
    private Sphere mSphere;
    private DirectionalLight mDirectionalLight;
    private Context context;
    private Material material;
    private Object3D monkey;
    private int movimiento;
    private int mAccValuesx;
    private int mAccValuesy;
    private int mAccValuesz;



    public Rendered(Context context) {
        super(context);
        this.context = context;
        setFrameRate(100);
    }


    public Rendered(Context context, boolean registerForResources) {
        super(context, registerForResources);
        this.context = context;
    }

    /**
     * Escena inicial
     */
    @Override
    protected void initScene() {
        //direcciones x, y y z
        mDirectionalLight = new DirectionalLight(1f, .2f, -1f);
        mDirectionalLight.setColor(1f, 1f, 1f);
        mDirectionalLight.setPower(2);
        getCurrentScene().addLight(mDirectionalLight);
        initImage();
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }
//    Solo funciona con wallpapper
    @Override
    public void onTouchEvent(MotionEvent event) {


    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
//        monkey.rotate(Vector3.Axis.Z, mAccValuesx);
        monkey.setRotation(mAccValuesx, mAccValuesy, 0);
    }



    //Añade material a la esfera
    public void initImage(){
        material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColorInfluence(0);
        Texture mTexture = new Texture("Capa", R.drawable.earthtruecolor_nasa_big);
        try{
            material.addTexture(mTexture);
//            initSphere();
            initModel();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void initModel(){
        try {
            final LoaderAWD parser =
             new LoaderAWD(mContext.getResources(),
             mTextureManager, R.raw.awd_suzanne);
            parser.parse();

            monkey = parser.getParsedObject();
            monkey.setMaterial(material);

            getCurrentScene().addChild(monkey);
            getCurrentCamera().setZ(4.2f);

        }catch (Exception e){

        }

    }

    public void initSphere(){
        this.mSphere = new Sphere(1,24,24);
        this.mSphere.setMaterial(material);
        getCurrentScene().addChild(mSphere);
        getCurrentCamera().setZ(4.2f);
    }


    public void movimientoEjes(int x, int y, int z) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int alto =(int) dm.heightPixels;
        int ancho = (int) dm.widthPixels;

        Log.i("Rotation", "alto: " + alto + "y" + y);

        mAccValuesx = (int) ((x * 360 )/ ancho);
        mAccValuesy = (int) ((y * 360 )/ alto);


        double m =(y * 360) / alto;

        Log.i("Rotation", "x: " + mAccValuesx);
//        uesx);
        Log.i("Rotation", "y: " + mAccValuesy +  m);
        mAccValuesz =z;


    }
}
