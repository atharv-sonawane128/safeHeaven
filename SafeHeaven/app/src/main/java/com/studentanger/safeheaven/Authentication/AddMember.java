package com.studentanger.safeheaven.Authentication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.studentanger.safeheaven.Frags.Home;
import com.studentanger.safeheaven.Model.Members;
import com.studentanger.safeheaven.R;
import com.studentanger.safeheaven.databinding.ActivityAddMemberBinding;
import com.studentanger.safeheaven.databinding.ActivityLoginBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddMember extends AppCompatActivity {

    ActivityAddMemberBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    Uri selectedImage;
    DatabaseReference datareference;
    static int PReqCode = 1 ;
    static int REQUESTCode = 1 ;
    String flat,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMemberBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseStorage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
//        datareference = FirebaseDatabase.getInstance().getReference().child("OwnerInfo").child(firebaseAuth.getUid());



        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveProfile();
            }
        });
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

//        datareference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                Members user = snapshot.getValue(Members.class);
//
//                binding.flat.setText(user.getFlat());
//                binding.code.setText(user.getSocietyCode());
//
//                Glide.with(AddMember.this).load(user.getImageUrl()).placeholder(R.drawable.user).into(binding.profile);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


    }

    private void SaveProfile() {

        String username = binding.name.getText().toString();
        String fullname = binding.email.getText().toString();
        String phone = binding.number.getText().toString();

        if (username.isEmpty()){
            binding.name.setError("Member Name Is Required");
            binding.name.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            binding.number.setError("Mobile Number Is Required");
            binding.number.requestFocus();
            return;
        }
        if (fullname.isEmpty()){
            binding.email.setError("Email Is Required");
            binding.email.requestFocus();
            return;
        }
        binding.progress.setVisibility(View.VISIBLE);
        binding.save.setVisibility(View.GONE);
        if (selectedImage != null){
            StorageReference reference = firebaseStorage.getReference().child("ProfileImages").child(firebaseAuth.getUid());
            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                binding.progress.setVisibility(View.INVISIBLE);
                                binding.save.setVisibility(View.VISIBLE);

                                String imageUrl = uri.toString();
                                String uid = firebaseAuth.getUid();
                                String email = binding.email.getText().toString();
                                String name = binding.name.getText().toString();
                                String phone = binding.number.getText().toString();
                                String flat = binding.flat.getText().toString();
                                String code = binding.code.getText().toString();
                                Members members = new Members(name,phone,email,flat,code,imageUrl,uid);

                                database.getReference().child("Members").child(uid).child("Person").setValue(members)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                String email = binding.email.getText().toString();
                                                String flat = binding.number.getText().toString();
                                                String name = binding.name.getText().toString();
                                                String password = flat+name;
                                                firebaseAuth.createUserWithEmailAndPassword(email,password);
                                                firebaseAuth.signOut();

                                                Intent intent = new Intent(AddMember.this,Login.class);
                                                startActivity(intent);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                finish();


                                            }
                                        });












                            }
                        });
                    }else {

                        Toast.makeText(AddMember.this, "error", Toast.LENGTH_SHORT).show();
                        binding.progress.setVisibility(View.GONE);
                        binding.save.setVisibility(View.VISIBLE);
                    }
                }
            });
        }else {
            Toast.makeText(this, "Member Photo ID Is Required", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,25);
    }
    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(AddMember.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddMember.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Please Accept All The Permissions To Perform This Action", Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(AddMember.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }else {
            openGallery();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25){
            if(data != null){

                if (data.getData() !=null){
                    binding.profile.setImageURI(data.getData());
                    selectedImage = data.getData();
                }
            }

        }

    }



}