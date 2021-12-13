package co.com.cesardiaz.misiontic.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.cesardiaz.misiontic.mytask.view.MainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        DB = new DBHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();



                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else{
                    if (Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                        Toast.makeText(LoginActivity.this, "Email Verified !", Toast.LENGTH_SHORT).show();
                        Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                        if(checkuserpass==false){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert==true){
                                Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this,"Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}