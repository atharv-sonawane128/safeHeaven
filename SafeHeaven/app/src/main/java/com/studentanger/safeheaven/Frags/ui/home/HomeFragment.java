package com.studentanger.safeheaven.Frags.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentanger.safeheaven.Adapter.MemAdapter;
import com.studentanger.safeheaven.Authentication.AddMember;
import com.studentanger.safeheaven.Model.MemberHome;
import com.studentanger.safeheaven.Model.Members;
import com.studentanger.safeheaven.R;
import com.studentanger.safeheaven.databinding.FragmentHomeBinding;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayList<Members> users;
    MemAdapter usersAdapter;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database;
    private DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        users = new ArrayList<>();
        usersAdapter = new MemAdapter(getActivity(),users);

//        reference = FirebaseDatabase.getInstance().getReference("Owner's Details").child(firebaseUser.getUid());

        binding.mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMember.class);
                startActivity(intent);
            }
        });

//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//
//                Members user = snapshot.getValue(Members.class);
//                binding.name.setText(user.getName());
//                binding.phone.setText(user.getPhone());
//
//                Glide.with(getActivity()).load(user.getImageUrl()).placeholder(R.drawable.user).into(binding.photo);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}