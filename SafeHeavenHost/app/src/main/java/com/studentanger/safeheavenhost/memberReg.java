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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.studentanger.safeheavenhost.databinding.ActivityMemberRegBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class memberReg extends AppCompatActivity {

    ActivityMemberRegBinding binding;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMemberRegBinding.inflate(getLayoutInflater());
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
                    binding.name.setError("Name Is Required");
                    binding.name.requestFocus();
                    return;
                }
                if (username.isEmpty()){
                    binding.phone.setError("Phone Number Is Required");
                    binding.phone.requestFocus();
                    return;
                }
                if (username.isEmpty()){
                    binding.pass.setError("Password Is Required");
                    binding.pass.requestFocus();
                    return;
                }
                if (username.isEmpty()){
                    binding.email.setError("Email Is Required");
                    binding.email.requestFocus();
                    return;
                }
                if (fullname.isEmpty()){
                    binding.code.setError("Society Code Is Required");
                    binding.code.requestFocus();
                    return;
                }
                if (fullname.isEmpty()){
                    binding.flat.setError("Flat Number Is Required");
                    binding.flat.requestFocus();
                    return;
                }

                checkDetails();
            }
        });

    }

    private void checkDetails() {

        String username = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String phone = binding.phone.getText().toString();
        String password = binding.pass.getText().toString();
        String code = binding.code.getText().toString();
        String flat = binding.flat.getText().toString();

        binding.progress.setVisibility(View.VISIBLE);
        binding.reg.setVisibility(View.GONE);
        HashMap map = new HashMap();
        map.put("Name",username);
        map.put("Email",email);
        map.put("Phone Number",phone);
        map.put("Society Code",code);
        map.put("Flat Number",flat);

        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                database.getReference().child("Owner's Details").child(auth.getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {

                        binding.progress.setVisibility(View.GONE);
                        binding.reg.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(memberReg.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                auth.signOut();

            }
        });


    }
}