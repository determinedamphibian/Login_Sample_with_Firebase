package com.example.loginotpsamplewithfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_f_name, et_l_name, et_num, et_address, et_password, et_re_password;
    TextInputLayout prefix_num;
    TextView tv_already_reg;
    Button btn_register;
    FirebaseFirestore db;
    private static final String TAG = "MAIN_TAG";

    User registered_user = new User();

    OTPAuthActivity otpAuthActivity = new OTPAuthActivity();

    //when text view named already registered was pressed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize variables
        tv_already_reg = findViewById(R.id.tv_already_registered);
        et_f_name = findViewById(R.id.et_f_name);
        et_l_name = findViewById(R.id.et_l_name);
        et_num = findViewById(R.id.et_number);
        prefix_num = findViewById(R.id.tl_prefix_num);
        et_address = findViewById(R.id.et_address);
        et_password = findViewById(R.id.et_password);
        et_re_password = findViewById(R.id.et_re_password);
        btn_register = findViewById(R.id.btn_register_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();




        //it will call a method goToOTP which will redirect the user to OTP Authentication when the button was pressed
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        //it will call a method goBackLogin which goes back to Login Activity
        tv_already_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackLogin();
            }
        });

    }

    //method for calling OTP Auth Activity
    public void registerUser() {

        String first_name = et_f_name.getText().toString().trim();
        String last_name = et_l_name.getText().toString().trim();
        String number = prefix_num.getPrefixText()+et_num.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String re_password = et_re_password.getText().toString().trim();

        if (first_name.isEmpty()){
            et_f_name.setError("First name is required");
            et_f_name.requestFocus();

        }
        if (last_name.isEmpty()){
            et_l_name.setError("Last name is required");
            et_l_name.requestFocus();

        }
        if (number.isEmpty()){
            et_num.setError("Number is required");
            et_num.requestFocus();

        }

        if (address.isEmpty()){
            et_address.setError("Address is required");
            et_address.requestFocus();

        }
        if (password.isEmpty()){
            et_password.setError("Password is required");
            et_password.requestFocus();

        }
        if (re_password.isEmpty()){
            et_re_password.setError("Enter the password again");
            et_re_password.requestFocus();

        }
        if (!re_password.equals(password)) {
            et_re_password.setError("Password does not match");
            et_re_password.requestFocus();

        }

//babalikan mo
// Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();


        registered_user.setUser(first_name,last_name,number,address,password);

        String firstName1 = registered_user.get_f_Name();
        String lastName1 = registered_user.get_l_name();
        String address1 = registered_user.getAddress();
        String number1 =registered_user.getNumber();
        String password1 = registered_user.getPassword();

        user.put("first name", firstName1);
        user.put("last name",lastName1);
        user.put("phone number", number1);
        user.put("address", address1);
        user.put("password", password1);


        otpAuthActivity.getUserInformation((HashMap) user);


        Intent intent = new Intent(SignUpActivity.this, OTPAuthActivity.class);
        startActivity(intent);


    }

    //method for calling Login Activity
    private void goBackLogin() {
        Intent intent = new Intent( SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}