package vn.mrlongg71.assignment.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.mrlongg71.assignment.Adapter.SpinerClassAdapter;
import vn.mrlongg71.assignment.Adapter.StudentsAdapter;
import vn.mrlongg71.assignment.Model.AddClass;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class ManageSVActivity extends AppCompatActivity {
    EditText edtTenSV, edtDate;
    Button btnAddSV,btnAddAnhSVManage;
    Toolbar toolbar;
    int iduser,idclass;
    String tenlop;
    final int REQUSE_CODE_CAMERA = 123;
    final int REQUES_CODE_FILE = 456;
    Spinner spinner;
    ImageView imgAnhSVTam;
    ArrayList<AddClass> addClassArrayListSpiner;
    ArrayList<Students> addClassArrayListSV;
    SpinnerAdapter adapterSpiner;
    StudentsAdapter studentsAdapter;
    ListView listViewSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sv);
        anhxa();
        Toolbar();
        //xử lí chọn ngày tháng năm sinh
        edtDate();
        //xử lí đổ dữ liệu lên spiner
        Spiner();
        //xử lí btnAddSV
        btnAddSV();
        //xử lí btn chọn ảnh
        btnAddAnhSVManage();
        GetDataClass_SV();

    }
    //xử lí btnAddSV
    private void btnAddSV() {
        btnAddSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //idclass = 0 -> "Chọn lớp" -> null
              if(idclass == 0){
                  Toast.makeText(ManageSVActivity.this, "Vui lòng chọn một lớp!", Toast.LENGTH_SHORT).show();
              }else if(edtTenSV.length() == 0){
                  Toast.makeText(ManageSVActivity.this, "Vui lòng nhập Tên", Toast.LENGTH_SHORT).show();
              }else if(edtDate.length() == 0){
                  Toast.makeText(ManageSVActivity.this, "Vui lòng nhập Ngày sinh", Toast.LENGTH_SHORT).show();
              }else{
                  insertDataSV();
                  GetDataClass_SV();
              }


            }
        });


    }

    private void Spiner() {
        addClassArrayListSpiner = new ArrayList<>();
        addClassArrayListSpiner.add(new AddClass(0, "null" , "Chọn lớp" , iduser ));
        adapterSpiner = new SpinerClassAdapter(this, R.layout.custom_list_spiner_class, addClassArrayListSpiner);
        spinner.setAdapter(adapterSpiner);
        GetDataClass_Spiner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idclass = addClassArrayListSpiner.get(position).getId();
                tenlop = addClassArrayListSpiner.get(position).getTenlop();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void edtDate() {
    edtDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogDate();
        }
    });
    }

    private void btnAddAnhSVManage(){
        btnAddAnhSVManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 dialog_ChooseAddAnhSV();

            }
        });

    }
    private void dialog_ChooseAddAnhSV(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_chooseanhsv);
        Button btnChooseAnhFile = dialog.findViewById(R.id.btnchooseanhFile);
        Button btnChooseAnhCamera = dialog.findViewById(R.id.btnchooseanhCamera);


        btnChooseAnhCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ManageSVActivity.this, new String[]{Manifest.permission.CAMERA}, REQUSE_CODE_CAMERA);
                dialog.dismiss();
            }
        });
        btnChooseAnhFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ManageSVActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUES_CODE_FILE);
                dialog.dismiss();
            }
        });


        dialog.show();

    }
    //xử lí xin quyền camera và file

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUSE_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUSE_CODE_CAMERA);
                    }else {

                    Toast.makeText(this, "Bạn chưa cấp quyền Camera!", Toast.LENGTH_SHORT).show();
                    }
                break;
            case REQUES_CODE_FILE:

                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUES_CODE_FILE);
                }else {
                    Toast.makeText(this, "Bạn chưa cấp quyền truy cập thư viện!", Toast.LENGTH_SHORT).show();
                }
                break;

                }







        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // đổ ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUSE_CODE_CAMERA && resultCode == RESULT_OK && data !=  null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhSVTam.setImageBitmap(bitmap);
        }
        if(requestCode == REQUES_CODE_FILE && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhSVTam.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    //dialog date
    private void DialogDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // i i1 i2 -> năm tháng ngày
                calendar.set(i, i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year,month,day);
        datePickerDialog.show();
    }
    private void anhxa() {
    toolbar = findViewById(R.id.toolbar_Manage);
    edtTenSV = findViewById(R.id.edtTenSV);
    edtDate = findViewById(R.id.edtDate);
    spinner = findViewById(R.id.spinnerClass);
    btnAddSV = findViewById(R.id.btnAddSV);
    imgAnhSVTam = findViewById(R.id.imgAnhSVTam);
    btnAddAnhSVManage = findViewById(R.id.btnAddAnhSVManage);
    //nhận iduser từ home
        Intent intent = getIntent();
        iduser = intent.getIntExtra("iduser" , -1);
    //listview
        listViewSV = findViewById(R.id.list_SV);
        addClassArrayListSV = new ArrayList<>();
        studentsAdapter = new StudentsAdapter(ManageSVActivity.this, R.layout.custom_listsv, addClassArrayListSV);
        listViewSV.setAdapter(studentsAdapter);
    }
    //GetData
    private void GetDataClass_Spiner() {
        Cursor dataSpiner = MainActivity.database.GetData("SELECT * FROM Class WHERE iduser = '" + iduser + "'");
        while (dataSpiner.moveToNext()) {
            int id = dataSpiner.getInt(0);
            String malop = dataSpiner.getString(1);
            String tenlop = dataSpiner.getString(2);
            iduser = dataSpiner.getInt(3);
            addClassArrayListSpiner.add(new AddClass(id, malop, tenlop, iduser));
        }
    }
    public void insertDataSV() {
        String tenSV = edtTenSV.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        //chuyeern img ->byte
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnhSVTam.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream arr_byte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, arr_byte);
        byte[] imgAnhSV = arr_byte.toByteArray();

            MainActivity.database.INSERT_SV(tenSV, date, idclass, iduser, tenlop, "null", "null", "null", imgAnhSV);
            Toast.makeText(this, "Đã thêm thành công Sinh viên " + tenSV, Toast.LENGTH_SHORT).show();

    }
    private void GetDataClass_SV(){
            Cursor dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE iduser = '"+ iduser+"'");
            addClassArrayListSV.clear();
            while (dataSV.moveToNext()){
                int id = dataSV.getInt(0);
                String tenSV = dataSV.getString(1);
                String date = dataSV.getString(2);
                String tenlop = dataSV.getString(5);
                String sdt = dataSV.getString(6);
                String email = dataSV.getString(7);
                String place = dataSV.getString(8);
                addClassArrayListSV.add(new Students(id,tenSV,date,idclass,iduser,tenlop,sdt,email,place, dataSV.getBlob(9)));
            }
            studentsAdapter.notifyDataSetChanged();
    }
    private void Toolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}

