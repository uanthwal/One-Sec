package com.mobilecomputing.one_sec.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.one_sec.R;
import com.mobilecomputing.one_sec.activities.AlbumDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HashMap<String, String>> mDataset;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView albumName;
        public TextView imgCount;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            albumName = v.findViewById(R.id.albumNameTxtVw);
            imgCount = v.findViewById(R.id.imageCountTxtVw);
            imageView = v.findViewById(R.id.imageView3);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlbumListAdapter(Context pContext, ArrayList<HashMap<String, String>> myDataset, OnItemClickListener onItemClickListener) {
        this.context = pContext;
        this.mDataset = myDataset;
        this.mOnItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (null != mDataset && mDataset.size() > 0) {
            HashMap<String, String> currentItem = mDataset.get(position);
            holder.albumName.setText(currentItem.get("albumName"));
            holder.imgCount.setText("Images (" + currentItem.get("imageCount") + ")");
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:  albumName" + position);
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra("albumName", mDataset.get(position).get("albumName"));
                intent.putExtra("imageCount", mDataset.get(position).get("imageCount"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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