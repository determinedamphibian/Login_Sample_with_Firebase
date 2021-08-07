package com.example.loginotpsamplewithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginotpsamplewithfirebase.databinding.ActivityMainBinding;
import com.example.loginotpsamplewithfirebase.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.stream.Stream;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private @NonNull ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            firebaseAuth = FirebaseAuth.getInstance();
            checkUserStatus();

            binding.btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    checkUserStatus();
                }
            });
    }

    private void checkUserStatus() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){

            String phone = firebaseUser.getPhoneNumber();
            binding.phoneTv.setText(phone);



        }else{
            finish();
        }
    }
}