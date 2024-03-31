package com.example.finalappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalappproject.R;
import com.example.finalappproject.Student;

import java.util.ArrayList;

public class MyAdminAdapter extends RecyclerView.Adapter<MyAdminAdapter.MyViewHolder>{
    Context context;
    ArrayList<Student> list;

    public MyAdminAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adminentry,parent,false);
        return new MyAdminAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
        holder.country.setText(student.getCountryAbroad());
        holder.instituteAbroad.setText(student.getInstituteAbroad());
        holder.city.setText(student.getCity());
        holder.degree.setText(student.getDegree());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email, country, instituteAbroad,degree,city;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            country = itemView.findViewById(R.id.country);
            instituteAbroad = itemView.findViewById(R.id.studentAbroad);
            city = itemView.findViewById(R.id.city);
            degree = itemView.findViewById(R.id.degree);
        }
    }
}
