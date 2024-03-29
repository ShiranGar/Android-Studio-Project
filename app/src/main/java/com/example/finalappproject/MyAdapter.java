package com.example.finalappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<Student> list;

    public MyAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.studententry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
        holder.institudeIsrael.setText(student.getInstitudeIsrael());
        holder.institudeAbroad.setText(student.getInstitudeAbroad());
        holder.city.setText(student.getCity());
        holder.degree.setText(student.getDegree());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,institudeIsrael,institudeAbroad,degree,city;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            email = itemView.findViewById(R.id.studentEmail);
            institudeIsrael = itemView.findViewById(R.id.studentISRInstitude);
            institudeAbroad = itemView.findViewById(R.id.studentInstitudeAbroad);
            city = itemView.findViewById(R.id.studentCity);
            degree = itemView.findViewById(R.id.studentDegree);
        }
    }
}
