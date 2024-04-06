package com.example.finalappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalappproject.Classes.Model;
import com.example.finalappproject.R;

import java.util.ArrayList;

public class MyAdapterImages extends RecyclerView.Adapter<MyAdapterImages.MyViewHolder> {
    ArrayList<Model> mList;
    Context context;

    public MyAdapterImages(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyAdapterImages.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageslayout,parent,false);
        return new MyAdapterImages.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterImages.MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
        holder.textView.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View parent) {
            super(parent);
            imageView = parent.findViewById(R.id.m_image);
            textView = parent.findViewById(R.id.tvDescription);
        }
    }
}
