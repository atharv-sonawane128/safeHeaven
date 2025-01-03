package com.studentanger.safeheavenhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.studentanger.safeheavenhost.databinding.ActivityMemberRegBinding;
import com.studentanger.safeheavenhost.databinding.ActivitySocietyRegBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class societyReg extends AppCompatActivity {

    ActivitySocietyRegBinding binding;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySocietyRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = binding.name.getText().toString();
                String fullname = binding.code.getText().toString();


                if (username.isEmpty()){
                    binding.name.setError("Society Name Is Required");
                    binding.name.requestFocus();
                    return;
                }
                if (fullname.isEmpty()){
                    binding.code.setError("Society Code Is Required");
                    binding.code.requestFocus();
                    return;
                }
                if (fullname.isEmpty()){
                    binding.area.setError("Area Is Required");
                    binding.area.requestFocus();
                    return;
                }if (fullname.isEmpty()){
                    binding.city.setError("Society Code Is Required");
                    binding.city.requestFocus();
                    return;
                }

                checkDetails();
            }
        });
    }

    private void checkDetails() {

        binding.progress.setVisibility(View.VISIBLE);
        binding.reg.setVisibility(View.GONE);
        String name = binding.name.getText().toString();
        String code = binding.code.getText().toString();
        String city = binding.city.getText().toString();

        HashMap map = new HashMap();
        map.put("Society Name",name);
        map.put("Society",code);
        map.put("City",city);

        database.getReference().child("Society Details").child(name).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull @NotNull Void unused) {

                binding.progress.setVisibility(View.GONE);
                binding.reg.setVisibility(View.VISIBLE);
                Intent intent = new Intent(societyReg.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

}