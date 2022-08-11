package ultramodern.activity.rxcameratest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.TextureView;

import com.ragnarok.rxcamera.RxCamera;
import com.ragnarok.rxcamera.config.RxCameraConfig;

import rx.Observable;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraActivity();
    }
    public void CameraActivity(){
        RxCameraConfig config = new RxCameraConfig.Builder()
                .useFrontCamera()
                .setAutoFocus(true)
                .setPreferPreviewFrameRate(15,30)
                .setPreferPreviewSize(new Point(640,480),false)
                .setHandleSurfaceEvent(true)
                .build();

        RxCamera.open(MainActivity.this,config);
        final TextureView textureView = findViewById(R.id.textureView);
        RxCamera.open(MainActivity.this, config).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {
                return rxCamera.bindTexture(textureView);
                // or bind a SurfaceView:
                // rxCamera.bindSurface(SurfaceView)
            }
        }).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {
                return rxCamera.startPreview();
            }
        });
    }
}