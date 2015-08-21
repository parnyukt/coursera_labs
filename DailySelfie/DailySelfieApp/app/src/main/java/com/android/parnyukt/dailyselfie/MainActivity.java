package com.android.parnyukt.dailyselfie;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.parnyukt.dailyselfie.model.Selfie;
import com.android.parnyukt.dailyselfie.utils.CameraUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private Uri fileUri;
    private RecyclerView mPhotoRecycleView;
    private RecyclerView.Adapter mPhotoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotoRecycleView = (RecyclerView)findViewById(R.id.photo_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mPhotoRecycleView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mPhotoRecycleView.setLayoutManager(mLayoutManager);

        //todo: get images from storage
        List<Uri> fileUriList = CameraUtils.getInputMediaFiles(getString(R.string.app_name));
        List<Selfie> selfies = getSelfieImages(fileUriList);



        mPhotoAdapter = new PhotoAdapter(selfies);
        mPhotoRecycleView.setAdapter(mPhotoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Image cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Image haven't saved. Try again later.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            takePhoto();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void takePhoto(){
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = CameraUtils.getOutputMediaFileUri(CameraUtils.MEDIA_TYPE_IMAGE, getString(R.string.app_name)); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private List<Selfie> getSelfieImages(List<Uri> fileUriList){
        List<Selfie> selfieList = new ArrayList<>();
        for (Uri fileUri : fileUriList){
            selfieList.add(getSelfieByUri(fileUri, R.dimen.photo_width, R.dimen.photo_height));
        }
        return selfieList;
    }

    private Selfie getSelfieByUri(Uri fileUri, int widthId, int heightId){
        getContentResolver().notifyChange(fileUri, null);

        ContentResolver cr = getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, fileUri);
            bitmap = CameraUtils.getResizedBitmap(bitmap, getResources().getDimensionPixelOffset(widthId), getResources().getDimensionPixelOffset(heightId));
            return new Selfie("", "", bitmap);
//                iv_foto.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("Camera", e.toString());
        }
        return null;
    }

}
