package com.mobilecomputing.one_sec.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.one_sec.R;

import java.io.File;
import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyViewHolder> {
    private ArrayList<File> mDataset;
    private OnItemClickListener mOnItemClickListener;
    private Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imgThumbnail;

        public MyViewHolder(View v) {
            super(v);
            imgThumbnail = v.findViewById(R.id.img_thumbnail);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlbumDetailsAdapter(Context context, ArrayList<File> myDataset, OnItemClickListener onItemClickListener) {
        mDataset = myDataset;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thumbnail_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (null != mDataset && mDataset.size() > 0) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(mDataset.get(position).getAbsolutePath());
//            holder.imgThumbnail.setImageBitmap(myBitmap);
            final int THUMBNAIL_SIZE = 256;
            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(
                    BitmapFactory.decodeFile(mDataset.get(position).getAbsolutePath()),
                    THUMBNAIL_SIZE ,
                    THUMBNAIL_SIZE );
            holder.imgThumbnail.setImageBitmap(thumbImage);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (null != mDataset && mDataset.size() > 0)
            return mDataset.size();
        else
            return 0;
    }
}