package vn.mrlongg71.assignment.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import vn.mrlongg71.assignment.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnSignUp,btnSignInAgain;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        // đăng ký mới
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load dữ liệu
                if (edtUser.length() == 0 || edtPass.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.database.INSERT_USER(
                                    edtUser.getText().toString().trim(),
                                    edtPass.getText().toString().trim(),
                                    "null", "null", "null"
                            );
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                }
            }

        });
        //xử lí đăng nhập lại
        btnSignInAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void anhxa() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignInAgain = findViewById(R.id.btnSignInAgain);
        progressBar = findViewById(R.id.progressbar);
    }
}
