package com.studentanger.safeheaven.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentanger.safeheaven.Model.MemberHome;
import com.studentanger.safeheaven.Model.Members;
import com.studentanger.safeheaven.R;
import com.studentanger.safeheaven.databinding.MembersBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MemAdapter extends RecyclerView.Adapter<MemAdapter.MemViewHolder>{


    Context context;
    ArrayList<Members> users;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    public MemAdapter(Context context, ArrayList<Members> users) {
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public MemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.members,parent,false);

        return new MemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Members").child(firebaseUser.getUid()).child("Person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                Members user = users.get(position);
                holder.binding.name.setText(user.getName());
                holder.binding.phone.setText(user.getPhone());


                Glide.with(context).load(user.getImageUrl())
                        .placeholder(R.drawable.user)
                        .into(holder.binding.profile);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MemViewHolder extends RecyclerView.ViewHolder{

        MembersBinding binding;
        public MemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = MembersBinding.bind(itemView);



        }
    }
}
