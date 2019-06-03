package vn.mrlongg71.assignment.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.mrlongg71.assignment.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView txtUser;
    NavigationView navigationView;
    private int iduser;
    Button btnAddclass, btnSeeClass, btnManageClass , btnAddclass_dialog, btnDelete_Dialog, btnSeeListSV;
    EditText edtMaLop, edtTenLop;
    ImageView imgHuy_Dialog_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.nav_view);
        anhxa();
        setSupportActionBar(toolbar);
        //xử lí navgation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //xử lí get user từ Login
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        iduser = intent.getIntExtra("id" , -1);
        //xử lí addClass
        btnAddClass();
        //xử lí btnSeeClass
        btnSeeClass();
        //xử lí btnManageSV
        btnManageSV();
        //xử lí btn SeeListSV
        btnSeeListSV();
    }

    private void btnSeeListSV() {


        btnSeeListSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeeStudentsListActivity.class);
                intent.putExtra("iduser" , iduser);
                startActivity(intent);
            }
        });

    }

    private void btnManageSV() {
        btnManageClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ManageSVActivity.class);
                intent.putExtra("iduser" , iduser);
                startActivity(intent);
            }
        });
    }

    private void btnSeeClass() {
        btnSeeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SeeClassListActivity.class);
                intent.putExtra("iduser" ,  iduser);
                startActivity(intent);
            }
        });



    }

    //dialog addclass
    private void btnAddClass() {

        btnAddclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddclass();
            }
        });
    }

    public void DialogAddclass(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_addclass);
        //ánh xạ dialog
        edtMaLop = dialog.findViewById(R.id.edtMaLop);
        edtTenLop = dialog.findViewById(R.id.edtTenLop);
        btnAddclass_dialog = dialog.findViewById(R.id.btnaddclass_dialog);
        btnDelete_Dialog = dialog.findViewById(R.id.btndelete_dialog);
        imgHuy_Dialog_add = dialog.findViewById(R.id.imgHuy_dialogAdd);
        btnAddclass_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDataClass();
                dialog.dismiss();

            }
        });
        btnDelete_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenLop.setText("");
                edtMaLop.setText("");
            }
        });
        imgHuy_Dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        drawer  = findViewById(R.id.drawer_layout);
        txtUser = navigationView.findViewById(R.id.txtUser_nav);
        btnAddclass = findViewById(R.id.btnaddClass);
        btnSeeClass = findViewById(R.id.btnseeClass);
        btnManageClass = findViewById(R.id.btnmanageClass);
        btnSeeListSV = findViewById(R.id.btnSeeListSV);
       // txtUser.setText("ssss");

    }
    //insert data
    public void InsertDataClass(){
        String malop = edtMaLop.getText().toString().trim();
        String tenlop = edtTenLop.getText().toString().trim();
        MainActivity.database.INSERT_CLASS(malop,tenlop,iduser);
        Toast.makeText(this, "Đã thêm thành công lớp " + tenlop, Toast.LENGTH_SHORT).show();


    }

    //phím back
    @Override
    public void onBackPressed() {
         drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
           DialogLogOut();
        }
    }
    private  void DialogLogOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.error);
        builder.setMessage("Bạn có muốn đăng xuất tài khoản?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Toast.makeText(HomeActivity.this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    //clicked item trong navigation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addClass) {

            DialogAddclass();
        } else if (id == R.id.nav_seeClass) {
            Intent intent = new Intent(HomeActivity.this, SeeClassListActivity.class);
            intent.putExtra("iduser" ,  iduser);
            startActivity(intent);
        }else if(id == R.id.nav_manageClass){
            Intent intent = new Intent(HomeActivity.this, ManageSVActivity.class);
            intent.putExtra("iduser" , iduser);
            startActivity(intent);
        }else if (id == R.id.info_user) {
            Intent intent = new Intent(this, InfoUserActivity.class);
            intent.putExtra("id" , iduser);
            startActivity(intent);
        } else if (id == R.id.info_tacgia) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_info);
            dialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            },2000);


        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
