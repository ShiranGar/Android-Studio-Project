package com.example.finalappproject.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalappproject.Classes.putPDF;
import com.example.finalappproject.R;

import java.util.ArrayList;

public class MyStudiesFilesAdapter extends RecyclerView.Adapter<MyStudiesFilesAdapter.MyViewHolder> {
    Context context;
    ArrayList<putPDF> list;

    public MyStudiesFilesAdapter(Context context, ArrayList<putPDF> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyStudiesFilesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.studentstudiesfiles,parent,false);
        return new MyStudiesFilesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStudiesFilesAdapter.MyViewHolder holder, int position) {
        putPDF putPDF = list.get(position);
        holder.fileName.setText(putPDF.getName());
        holder.courseName.setText(putPDF.getCourseName());
        holder.instituteAbroad.setText(putPDF.getInstituteAbroad());
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(putPDF.getUrl()));
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fileName, courseName, instituteAbroad;
        Button btnDownload;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            courseName = itemView.findViewById(R.id.courseName);
            instituteAbroad = itemView.findViewById(R.id.instituteAbroad);
            btnDownload = itemView.findViewById(R.id.btnDownload);
        }
    }
}
