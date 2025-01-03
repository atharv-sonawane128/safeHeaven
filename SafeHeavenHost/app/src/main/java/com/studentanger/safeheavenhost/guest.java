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
import com.studentanger.safeheavenhost.databinding.ActivityGuestBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class guest extends AppCompatActivity {


    ActivityGuestBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGuestBinding.inflate(getLayoutInflater());
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

                String name = binding.name.getText().toString();
                String phone = binding.phone.getText().toString();
                String address = binding.address.getText().toString();
                String reason = binding.reason.getText().toString();
                String person = binding.personVisit.getText().toString();


                if (name.isEmpty()){
                    binding.name.setError("Society Name Is Required");
                    binding.name.requestFocus();
                    return;
                }
                if (phone.isEmpty()){
                    binding.phone.setError("Society Code Is Required");
                    binding.phone.requestFocus();
                    return;
                }
                if (address.isEmpty()){
                    binding.address.setError("Address Is Required");
                    binding.address.requestFocus();
                    return;
                }if (reason.isEmpty()){
                    binding.reason.setError("Reason Is Required");
                    binding.reason.requestFocus();
                    return;
                }if (person.isEmpty()){
                    binding.personVisit.setError("Owner name Is Required");
                    binding.personVisit.requestFocus();
                    return;
                }

                checkDetails();
            }
        });
    }

    private void checkDetails() {

        binding.progress.setVisibility(View.VISIBLE);
        binding.reg.setVisibility(View.GONE);
        String name = binding.name.getText().toString() ;
        String phone ="+91"+ binding.phone.getText().toString();
        String address = binding.address.getText().toString();
        String reason = binding.reason.getText().toString();
        String person = binding.personVisit.getText().toString();

        HashMap map = new HashMap();
        map.put("Guest Name",name);
        map.put("Phone Number",phone);
        map.put("Address",address);
        map.put("Reason of visit",reason);
        map.put("Person to visit",person);

        database.getReference().child("Guest Details").child(name).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull @NotNull Void unused) {

                binding.progress.setVisibility(View.GONE);
                binding.reg.setVisibility(View.VISIBLE);
                Intent intent = new Intent(guest.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}