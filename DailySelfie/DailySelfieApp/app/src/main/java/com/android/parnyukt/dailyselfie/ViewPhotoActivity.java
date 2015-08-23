package com.android.parnyukt.dailyselfie;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.parnyukt.dailyselfie.utils.Dialogs;

import java.io.File;

public class ViewPhotoActivity extends AppCompatActivity {

    Context mcContext;
    ImageView mPhotoImageView;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        mcContext = this;

        mPhotoImageView = (ImageView) findViewById(R.id.photo_image);

        filePath =  getIntent().getStringExtra("PATH");
        Bitmap bitmap = getSelfieThumbnail(new File(filePath));

        mPhotoImageView.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deletePhoto();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void deletePhoto(){
        Dialogs.confirmDialogShow(mcContext, "Delete photo",
                "Are you sure you want to delete this photo?",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        File file = new File(filePath);
                        boolean deleted = file.delete();
                        if (deleted){
                            Toast.makeText(mcContext, "Photo is deleted.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mcContext, "Can't delete this photo.", Toast.LENGTH_LONG).show();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);
                    }
                }
        );
    }
}
