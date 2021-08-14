package com.example.loginotpsamplewithfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private TextView tv_create_account, tv_userName, tv_password;
    private ImageView img_phone, img_googleMail, img_facebook;
    private Button btn_login;



    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth mAuth;

    private static final String TAG = "GOOGLE_SIGN_IN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_phone = findViewById(R.id.image_phone);

        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OTPAuthActivity.class);
                startActivity(intent);
            }
        });

        img_facebook = findViewById(R.id.image_facebook);

        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Begin Facebook SignIn");
                Intent intent = new Intent(MainActivity.this, FBLoginActivity.class);
               startActivity(intent);
            }
        });



        img_googleMail = findViewById(R.id.image_gmail);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //intent for google registration
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mAuth = FirebaseAuth.getInstance();


        img_googleMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Begin Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        //balikan mooooooooooooooooooooooo
        //Stream.builder().

        btn_login = findViewById(R.id.btn_login);
        tv_userName = findViewById(R.id.username);
        tv_password = findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = tv_userName.getText().toString();
                String passWord = tv_password.getText().toString();

                FirebaseFirestore firebaseFirestore = null;

                firebaseFirestore.getNamedQuery("f");

            }
        });



    }

    private void checkUser(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Log.d(TAG,"Google SignIn intent result");
            Task<GoogleSignInAccount>accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                //Google SignIn success

                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            }catch (Exception e){

                //failed Google SignIn
                Log.d(TAG,e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "Begin firebase auth with google account");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.d(TAG,"Logged In");

                        //getting the logged in current user and its information
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String eMail = firebaseUser.getEmail();

                        Log.d(TAG, "UID: "+uid);
                        Log.d(TAG, "Email: "+eMail);

                        //check if the user is new or existing

                        if (authResult.getAdditionalUserInfo().isNewUser()){

                            Log.d(TAG,"Account Created..."+eMail);
                            Toast.makeText(MainActivity.this, "Account created: "+eMail, Toast.LENGTH_LONG);

                            //new
                            SignUpActivity signUpActivity = new SignUpActivity();
                            signUpActivity.registerGoogleUser(uid);
                            //
                            Intent intent = new Intent( MainActivity.this, SignUpActivity.class);
                            startActivity(intent);
                        }
                        else {

                            Log.d(TAG, "Existing user "+eMail);
                            Toast.makeText(MainActivity.this, "Existing user: "+eMail, Toast.LENGTH_LONG);
                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "Login Failed "+e.getMessage());
                    }
                });
    }

}