package com.example.th2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.th2.dal.SQLHelper;
import com.example.th2.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button btnLogin, googleLoginButton;

    private TextView txtSigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        txtSigup = findViewById(R.id.txtSignup);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();

                User u = new User(mUsername,mPassword);
                SQLHelper db = new SQLHelper(getApplicationContext());
                if(db.checkUser(u)){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("user", db.getUserByUserName(mUsername));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Sai thông tin đăng nhập",Toast.LENGTH_SHORT).show();
                }

            }
        });

        txtSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}