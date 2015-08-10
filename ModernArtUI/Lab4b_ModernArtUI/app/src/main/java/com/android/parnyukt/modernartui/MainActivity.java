package com.android.parnyukt.modernartui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.android.parnyukt.modernartui.utils.Screens;


public class MainActivity extends AppCompatActivity {

    private static Integer MAX_PROGRESS = 100;
    SeekBar mSeekBar;
    LinearLayout mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = (LinearLayout)findViewById(R.id.rectanglesView);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for ( int i = 0; i < mView.getChildCount(); i++ ) {
                    LinearLayout parent = (LinearLayout) mView.getChildAt(i);
                    for ( int j = 0; j < parent.getChildCount(); j++ ) {
                        View child = parent.getChildAt(j);
                        int color = ((ColorDrawable) child.getBackground()).getColor();

                        if (getResources().getColor(R.color.White) != color &&
                                getResources().getColor(R.color.LightGray) != color) {

                            float[] hsvColor = {0, 1, 1};
                            Color.colorToHSV(color, hsvColor);

                            hsvColor[0] = hsvColor[0] + 10f * progress / MAX_PROGRESS;
                            hsvColor[1] = hsvColor[1] + 10f * progress / MAX_PROGRESS;
                            hsvColor[2] = hsvColor[2] + 10f * progress / MAX_PROGRESS;
                            child.setBackgroundColor(Color.HSVToColor(hsvColor));

                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Screens.showConfirmationDialog(MainActivity.this, getString(R.string.alert_title), getString(R.string.alert_text), new DialogInterface.OnClickListener[]{
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ok
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.moma.org"));
                        startActivity(intent);
                    }
                }}, R.string.btn_cancel, R.string.btn_ok);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
