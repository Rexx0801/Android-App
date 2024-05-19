package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.th2.dal.SQLHelper;
import com.example.th2.model.User;

public class SignupActivity extends AppCompatActivity {

    private EditText userName,email,password,rePassword;
    private Button btSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.edUserName);
        password = findViewById(R.id.edPassword);
        email = findViewById(R.id.edEmail);
        rePassword = findViewById(R.id.edRePassword);
        btSignup = findViewById(R.id.btnSignup);


        btSignup.setOnClickListener(new View.OnClickListener() {
            private boolean isPasswordValid(String password) {
                String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
                return password.matches(regex);
            }
            @Override
            public void onClick(View v) {
                SQLHelper db = new SQLHelper(getApplicationContext());
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();
                String mEmail = email.getText().toString();
                String mRepassword = rePassword.getText().toString();

                if(!mPassword.equalsIgnoreCase(mRepassword)){
                    Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                }else if (!isPasswordValid(mPassword)) {
                    Toast.makeText(getApplicationContext(),"Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!db.checkUserSigup(mUsername)){
                        Toast.makeText(getApplicationContext(),"Tài khoản đã tồn tại",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.addUser(new User(mUsername,mPassword,mEmail));
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}