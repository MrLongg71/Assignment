package vn.mrlongg71.assignment.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import vn.mrlongg71.assignment.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser;
    EditText edtPass;
    Button btnSignIn,btnSignUp;
    CheckBox checkUser;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        //xử lí lưu đăng nhập
        sharedPreferences = getSharedPreferences("dataLogin" , MODE_PRIVATE);
        //lấy giá trị từ dataLogin
        edtUser.setText(sharedPreferences.getString("user" , ""));

        edtPass.setText(sharedPreferences.getString("pass" , ""));

        checkUser.setChecked(sharedPreferences.getBoolean("checked" , false));
        //xử lí đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUser.length() == 0 || edtPass.length() == 0 ){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        //load xử lí dữ liệu
                        @Override
                        public void run() {
                            String user = edtUser.getText().toString();
                            String pass = edtPass.getText().toString();
                            Cursor dataUser = MainActivity.database.GetData("SELECT * FROM User");
                            while (dataUser.moveToNext()) {
                                int iddata = dataUser.getInt(0);
                                String userdata = dataUser.getString(1);
                                String passdata = dataUser.getString(2);
                                if (user.equals(userdata) && pass.equals(passdata)) {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    //user checkbox

                                    if(checkUser.isChecked()){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user" , user);
                                        editor.putString("pass" , pass);
                                        editor.putBoolean("checked" , true);
                                        editor.commit();
                                    }else {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("user");
                                        editor.remove("pass");
                                        editor.remove("checked");
                                        editor.commit();
                                    }

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("user", user);
                                    intent.putExtra("id", iddata);
                                    startActivity(intent);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, 1000);
                }
                }


        });
        //chuyển màn hình sang đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void anhxa() {
    edtUser = findViewById(R.id.edtUser);
    edtPass = findViewById(R.id.edtPass);
    btnSignIn = findViewById(R.id.btnSignIn);
    btnSignUp = findViewById(R.id.btnSignUp_New);
    progressBar = findViewById(R.id.progressbar);
    checkUser = findViewById(R.id.checkbox_User);

    }
}
