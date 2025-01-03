package com.studentanger.safeheaven.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.studentanger.safeheaven.Frags.Home;
import com.studentanger.safeheaven.R;
import com.studentanger.safeheaven.databinding.ActivityLoginBinding;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;


    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseLogin();
            }
        });

        if (firebaseUser != null){

            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();




        }
    }

    private void FirebaseLogin() {
        String email = binding.emailLogin.getText().toString();
        String password = binding.passwordLogin.getText().toString();
//        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        if (email.isEmpty()){
            binding.emailLogin.setError("Email Is Required");
            binding.emailLogin.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.passwordLogin.setError("Password Is Required");
            binding.passwordLogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailLogin.setError("Please Enter A Valid Email");
            binding.emailLogin.requestFocus();
            return;
        }
        if (password.length() < 6){
            binding.passwordLogin.setError("Password Must Be Of More Than 6 Characters");
            binding.passwordLogin.requestFocus();
            return;
        }


        binding.progressLogin.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    binding.progressLogin.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    finish();

                }else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(Login.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

    }
}