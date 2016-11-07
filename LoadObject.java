package ejercicios.com.renderapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.EllipticalOrbitAnimation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.awd.BlockSkybox;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.MathUtil;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

import java.nio.FloatBuffer;

/**
 * Created by USUARIO on 02/11/2016.
 */

public class LoadObject extends RajawaliRenderer {
    private PointLight mLight;
    private Object3D mObjectGroup;
    private Animation3D mCameraAnim, mLightAnim;
    private double mAccValuesx;
    private double mAccValuesy;
    private double mAccValuesz;

    private double mAccValuesxOld;
    private double mAccValuesyOld;
    private double mAccValueszOld;

    private Object3D spike;
    private int ancho;
    private int alto;
    private float planePosition = 1.0f;
    private double distance = 1.0f;
    private double distance2 = 0;



    public LoadObject(Context context) {
        super(context);
        getScreenSize();

    }


    @Override
    protected void render(long ellapsedRealtime, double deltaTime) {
        super.render(ellapsedRealtime, deltaTime);


//        mObjectGroup.setRotZ(mObjectGroup.getRotZ() + mAccValuesz);
//        mObjectGroup.setRotX(mObjectGroup.getRotX() + mAccValuesx);


   }

    @Override
    protected void initScene() {


        mLight = new PointLight();
        mLight.setPosition(5, 5, 5);
        mLight.setPower(40);

        getCurrentScene().addLight(mLight);
        getCurrentCamera().setZ(50);

        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
                mTextureManager, R.raw.house_obj);
        try {

            objParser.parse();
            mObjectGroup = objParser.getParsedObject();
            getCurrentScene().addChild(mObjectGroup);

            mCameraAnim = new RotateOnAxisAnimation(
                    Vector3.Axis.Y, 80);
            mCameraAnim.setDurationMilliseconds(10000);
            mCameraAnim.setRepeatMode(Animation.RepeatMode.INFINITE);
            mCameraAnim.setTransformable3D(mObjectGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLightAnim = new EllipticalOrbitAnimation3D(new Vector3(),
                new Vector3(0, 10, 0),
                Vector3.getAxisVector(Vector3.Axis.Z),
                0,
                360, EllipticalOrbitAnimation3D.OrbitDirection.CLOCKWISE);

        mLightAnim.setDurationMilliseconds(10000);
        mLightAnim.setRepeatMode(Animation.RepeatMode.INFINITE);
        mLightAnim.setTransformable3D(mLight);

        getCurrentScene().registerAnimation(mCameraAnim);
        getCurrentScene().registerAnimation(mLightAnim);

       // mCameraAnim.play();
        mLightAnim.play();



    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    private void getScreenSize(){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        alto =(int) dm.heightPixels;
        ancho = (int) dm.widthPixels;
    }

    public void movimientoEjes(double x, double y) {

        double rotatex = 0;
        double rotatey = 0;

        mAccValuesx = x;
        mAccValuesy = y;
        rotatex = (mAccValuesxOld - x) / 2f;
        rotatey = (mAccValuesyOld - y) / 2f;

        rotatey =

        Log.i("Rotation", "--- Inicio de calculo ---");
        Log.i("Rotation", "grades:" + getHypotenusa(mAccValuesx,mAccValuesy) * (Math.PI / 180));

        getGrades(mAccValuesx, mAccValuesy);
//        mAccValuesz = Math.hypot(x,y);

        double gradosZ = mObjectGroup.getRotZ();
        double gradosX = mObjectGroup.getRotX();

//        mObjectGroup.setRotZ(gradosZ + mAccValuesx);
//        mObjectGroup.setRotY(mObjectGroup.getRotY() + rotatey);

        mObjectGroup.rotateAround(new Vector3(0f,.5f,0f), -( x - mAccValuesxOld));

        mObjectGroup.rotateAround(new Vector3(.5f,0f,0f), (y - mAccValuesyOld));


//        mObjectGroup.setRotation(Vector3.Axis.X, getHypotenusa(mAccValuesx,mAccValuesy) * (Math.PI / 180));
//        rotateObject();
    }

    //Obtencion del radio
    private double getHypotenusa(double pointX, double pointY){
        double radius = 0.0;

        radius = Math.sqrt(Math.pow(pointX,2) + Math.pow(pointY, 2));
        return radius;
    }
    //con la tangente inversa se obtienen los grados
    private double getGrades(double pointX, double pointY){
        double grades = 0.0;

        grades = MathUtil.radiansToDegrees(Math.atan2(pointY,pointX));
//        El valor que se obtiene con atan2  regresa el angulo teta  de una conversiond de coordenadas rectangulares a coordenadas polares
//        EL valor es retornado en radianes por lo que es necesaria una conversion a grados
        Log.i("Valor en grados 3" , "" + MathUtil.radiansToDegrees(Math.atan2(pointY,pointX)));
        return grades;
    }

    private float getGrades(){
        return (float)MathUtil.radiansToDegrees(Math.atan2(mAccValuesx, mAccValuesy));
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);

    }


    public void rotateObject(){
        spike = mObjectGroup.clone(true, false);
//    1. Creacion de la esfera
//        * @param radius
//                *            The radius of the sphere
//        * @param segmentsW
//                *            The number of vertical segments
//        * @param segmentsH
//                *            The number of horizontal segments
        Object3D sphere = new Sphere(50, 6, 8);
//        2. Se obtiene el vertex buffer
        FloatBuffer vertbuffer = sphere.getGeometry().getVertices();
//        3. Se obtiene el normal buffer
        FloatBuffer normBuffer = sphere.getGeometry().getNormals();
        Vector3 upAxis = new Vector3(0,1,0);
//        4. Se inicia un ciclo  y se obtienen datos
        int numVertices = vertbuffer.limit();
        for(int i= 0; i<numVertices; i+=3){
            spike.setPosition(vertbuffer.get(i), vertbuffer.get(i+1), vertbuffer.get(i+2));
            Vector3 normal = new Vector3(normBuffer.get(i), normBuffer.get(i+1), normBuffer.get(i+2));
            Vector3 axis = Vector3.crossAndCreate(upAxis, normal);
            double angle = MathUtil.radiansToDegrees(Math.acos(Vector3.dot(upAxis,normal)));

            Log.i("Vector", "Angulo: " + angle);
            Quaternion q = new Quaternion();
            q.fromAngleAxis(axis,angle);
//            spike.setOrientation(q);
//            mObjectGroup.addChild(spike);

        }

        try{
//            * @param x double The x component.
//            * @param y double The y component.
//            * @param z double The z component.
//            Vector3 vector = new Vector3(.3f, .9f, .15f);
            Log.i("Vector", "Nrmalizacion del vector");
            Vector3 vector = new Vector3(mAccValuesx,  mAccValuesy, mAccValuesz);
            vector.normalize();
            RotateOnAxisAnimation rotate =  new RotateOnAxisAnimation( vector,360);
            rotate.setDurationMilliseconds(8000);
            rotate.setRepeatMode(Animation.RepeatMode.NONE);
            rotate.setTransformable3D(mObjectGroup);
//            getCurrentScene().registerAnimation(rotate);
//            rotate.play();

            mObjectGroup.setRotX(mObjectGroup.getRotX() + (mAccValuesx * 180)/ ancho);
            mObjectGroup.setRotY(mObjectGroup.getRotY() + (mAccValuesy * 180)/ alto);
//            mObjectGroup.setScale(0.5);
//            mObjectGroup.setRotZ(mObjectGroup.getRotZ() + getGrades() );

        }catch (Exception e){

        }
    }

    public void setInitialMove(double initx, double inity){
        mAccValuesxOld = initx;
        mAccValuesyOld = inity;

    }


    public void getScaled(){

        try{
           planePosition += (mAccValuesy - mAccValuesyOld)  * 0.01f;
            if(planePosition <= 1.0f){
                mObjectGroup.setScale(planePosition);
            }else{
                mObjectGroup.setScale(2.0f);
            }

            if(planePosition >= .05f){
                mObjectGroup.setScale(planePosition);
            }else{
                mObjectGroup.setScale(.5f);
            }
        }catch(Exception e){

        }


    }

    public void getScaledByDistance(MotionEvent event){
        float escalaEstimada = 0f;
        float escalaMaxima   = 3f;
        try{

            distance = Math.sqrt((Math.pow((event.getX(0)- event.getX(1)),2)
                    + (Math.pow((event.getY(0)- event.getY(1)),2))));

            escalaEstimada = (float)((distance * 3) /alto);
            mObjectGroup.setScale(escalaEstimada);

        }catch (Exception e){

        }
    }



}
