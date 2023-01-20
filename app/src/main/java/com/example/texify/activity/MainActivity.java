 package com.example.texify.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.texify.R;
import com.example.texify.utils.AppPermissions;
import com.google.android.material.card.MaterialCardView;

 public class MainActivity extends AppCompatActivity {

     MaterialCardView cvPhoto;
     ActivityResultLauncher<Intent> activityResultLauncher;
     private static final int VERIFY_PERMISSIONS_REQUEST=1;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListener();

    }

    public void init(){
        cvPhoto = findViewById(R.id.cvPhoto);

        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK && result.getData()!=null ) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Intent intent = new Intent(MainActivity.this,TextFromImage.class);
                    intent.putExtra("BitmapImage", bitmap);
                    startActivity(intent);
                }
        });
    }

    public void setListener(){
        cvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions(AppPermissions.CAMERA_PERMISSION[0])){
                    Log.d(TAG, "onClick: starting camera");
                    Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultLauncher.launch(cameraIntent);
                }else{
                    verifyPermission(AppPermissions.CAMERA_PERMISSION);
                }
            }
        });
    }


     /*-----------------------Permissions-------------------------*/
     public void verifyPermission(String[] permissions) {
         Log.d(TAG, "verifyPermission: verifying permissions");
         ActivityCompat.requestPermissions(
                 MainActivity.this,
                 permissions,
                 VERIFY_PERMISSIONS_REQUEST
         );
     }
     public boolean checkPermissions(String permission){
         Log.d(TAG, "checkPermissions: Checking permissions");
         int permissionRequest= ActivityCompat.checkSelfPermission(MainActivity.this,permission);
         if(permissionRequest!= PackageManager.PERMISSION_GRANTED){
             Log.d(TAG, "checkPermissions: \n permission was not granted for: "+permission);
             return false;
         }else{
             Log.d(TAG, "checkPermissions: \n permission was granted for: "+permission);
             return true;
         }
     }

 }