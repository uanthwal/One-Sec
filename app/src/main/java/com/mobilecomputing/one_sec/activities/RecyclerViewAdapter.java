package com.mobilecomputing.one_sec.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobilecomputing.one_sec.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
    private DatabaseHelper myDB;

    public RecyclerViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext, DatabaseHelper myDB) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try{
            Glide.with(mContext)
                    .asBitmap()
                    .load(mImages.get(position))
                    .into(holder.image);
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
//        Picasso.get().load(mImages.get(position)).into(holder.image);
        holder.imageName.setText(mImageNames.get(position));

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Cursor data = myDB.getListValue(mImageNames.get(position));
                if(data.getCount() == 0){
                    Toast.makeText(mContext, "Database is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    while(data.moveToNext()) {
                        String name = data.getString(1);
                        String username = data.getString(2);
                        String password = data.getString(3);
                        Intent intent = new Intent();
                        intent.setClass( mContext, LoginCredentialDetail.class);
                        intent.putExtra("NAME", name);
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("PASSWORD", password);
                        intent.putExtra("URL", name);
                        mContext.startActivity(intent);

                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image ;
        TextView imageName;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.imageName);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
