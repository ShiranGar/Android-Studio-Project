package com.example.finalappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalappproject.R;
import com.example.finalappproject.Classes.Student;

import java.util.ArrayList;

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.MyViewHolder>{
    Context context;
    ArrayList<Student> list;

    public MyStudentAdapter(Context context, ArrayList<Student> list) {
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
    public void onBindViewHolder(@NonNull MyStudentAdapter.MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
        holder.institudeIsrael.setText(student.getInstituteIsrael());
        holder.institudeAbroad.setText(student.getInstituteAbroad());
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
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            institudeIsrael = itemView.findViewById(R.id.studentISRInstitute);
            institudeAbroad = itemView.findViewById(R.id.studentInstituteAbroad);
            city = itemView.findViewById(R.id.city);
            degree = itemView.findViewById(R.id.degree);
        }
    }
}
