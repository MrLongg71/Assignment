package vn.mrlongg71.assignment.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

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
    Button btnAddSV;
    int iduser,idclass;
    Spinner spinner;
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
        //xử lí chọn ngày tháng năm sinh
        edtDate();
        //xử lí đổ dữ liệu lên spiner
        Spiner();
        //xử lí btnAddSV
        btnAddSV();
        GetDataClass_SV();
        Log.d("userid123" , String.valueOf(iduser));
    }
    //xử lí btnAddSV
    private void btnAddSV() {
        btnAddSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("calsss", String.valueOf(idclass));
                insertDataSV();
                GetDataClass_SV();

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
    edtTenSV = findViewById(R.id.edtTenSV);
    edtDate = findViewById(R.id.edtDate);
    spinner = findViewById(R.id.spinnerClass);

    btnAddSV = findViewById(R.id.btnAddSV);
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
    public void insertDataSV(){
        String tenSV = edtTenSV.getText().toString().trim();
        String date  = edtDate.getText().toString().trim();
        MainActivity.database.INSERT_SV(tenSV,date,idclass,iduser);
        Toast.makeText(this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
    }
    private void GetDataClass_SV(){
            Cursor dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE iduser = -1");
            while (dataSV.moveToNext()){
                int id = dataSV.getInt(0);
                String tenSV = dataSV.getString(1);
                Log.d("tensv" , tenSV);

                String date = dataSV.getString(2);

                addClassArrayListSV.add(new Students(id,tenSV,date,idclass,iduser));
            }
            studentsAdapter.notifyDataSetChanged();
    }
}

