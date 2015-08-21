package com.android.parnyukt.dailyselfie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.parnyukt.dailyselfie.model.Selfie;

import java.util.List;

/**
 * Created by tparnyuk on 20.08.15.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Selfie> records;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mPhotoImageView;
        public TextView mPhotoTextView;


        public ViewHolder(View view) {
            super(view);
            mPhotoImageView = (ImageView) view.findViewById(R.id.photo_image);
            mPhotoTextView = (TextView) view.findViewById(R.id.photo_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotoAdapter(List<Selfie> dataset) {
        records = dataset;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_photo, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Selfie selfie = records.get(position);

//        viewHolder.mPhotoImageView.setImageURI();
        viewHolder.mPhotoTextView.setText(selfie.getSelfieName());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
