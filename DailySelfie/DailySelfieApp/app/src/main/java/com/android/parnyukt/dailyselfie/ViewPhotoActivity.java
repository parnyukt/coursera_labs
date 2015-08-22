package com.android.parnyukt.dailyselfie;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.parnyukt.dailyselfie.model.Selfie;
import com.android.parnyukt.dailyselfie.utils.CameraUtils;

import java.io.File;

public class ViewPhotoActivity extends AppCompatActivity {

    ImageView mPhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        mPhotoImageView = (ImageView) findViewById(R.id.photo_image);

        String filePath =  getIntent().getStringExtra("PATH");
        Bitmap bitmap = getSelfieThumbnail(new File(filePath));

        mPhotoImageView.setImageBitmap(bitmap);

    }

    public Bitmap getSelfieThumbnail(File file){
        Uri fileUri = Uri.fromFile(file);
        getContentResolver().notifyChange(fileUri, null);

        ContentResolver cr = getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, fileUri);
            return bitmap;
        } catch (Exception e) {
            Log.e("Camera", e.toString());
        }
        return null;
    }
}
