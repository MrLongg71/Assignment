package vn.mrlongg71.assignment.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import vn.mrlongg71.assignment.Database.Database;
import vn.mrlongg71.assignment.R;

public class MainActivity extends AppCompatActivity {

    public static Database database;
    ImageView imglogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imglogo = (ImageView) findViewById(R.id.splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000L );
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.trans_start);
        imglogo.setAnimation(animation);

        //xóa database
        //this.deleteDatabase("assignment.sqlite");
        //create database assignment
        database = new Database(this, "assignment.sqlite", null, 1);
        //create table user
        database.QueryData("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR(20), password VARCHAR(20), name VARCHAR(30), place VARCHAR(40), phone VARCHAR(10))");
        //create table Class
        database.QueryData("CREATE TABLE IF NOT EXISTS Class(id INTEGER PRIMARY KEY , malop VARCHAR(20), tenlop VARCHAR(20), iduser INTEGER(20))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY , tensv VARCHAR(20), ngaysinh VARCHAR(20), idclass INTEGER(20),iduser INTEGER(20))");
        //database.QueryData("INSERT INTO Students VALUES(null,'A','01' , 1, 1)");

        //duyệt get database
        Cursor dataUser = database.GetData("SELECT * FROM Students");
        while (dataUser.moveToNext()){
            String user = dataUser.getString(1);
            }


    }
}
