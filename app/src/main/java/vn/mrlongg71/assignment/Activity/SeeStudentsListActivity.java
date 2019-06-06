package vn.mrlongg71.assignment.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import vn.mrlongg71.assignment.Adapter.SVAdapter;
import vn.mrlongg71.assignment.Adapter.SpinerClassAdapter;
import vn.mrlongg71.assignment.Model.AddClass;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class SeeStudentsListActivity extends AppCompatActivity {

    ListView listSV;
    ArrayList<Students> studentsArrayList;
    ArrayList<AddClass> arr_listSpiner;
    SVAdapter svAdapter;
    SpinerClassAdapter spinnerAdapter;
    Spinner spinnerClass;
    Toolbar toolbar;
    public int iduser, idclass, idsv;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_students_list);

        anhxa();
        //xử lí đổ dữ liệu listview

        listview_show();
        //xử lí đổ dữ liệu ra Spiner
        spiner_Show();

        GetDataSV();
        GetDataSVSearch();
        //xử lí clicked listview
        listview_clicked();
        //xử lí Toolbar
        Toolbar();

    }

    private void listview_clicked() {
        listSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailSVActivity.class);
                idclass = studentsArrayList.get(position).getIdclass();
                intent.putExtra("iduser", iduser);
                intent.putExtra("idclass", idclass);
                idsv = studentsArrayList.get(position).getId();

                intent.putExtra("idsv", idsv);
//
                startActivity(intent);
                finish();
            }
        });


    }

    private void spiner_Show() {
        arr_listSpiner = new ArrayList<>();
        arr_listSpiner.add(new AddClass(0, "null", "Chọn lớp", iduser));
        spinnerAdapter = new SpinerClassAdapter(this, R.layout.custom_list_spiner_class, arr_listSpiner);
        spinnerClass.setAdapter(spinnerAdapter);


        //click spinner
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idclass = arr_listSpiner.get(position).getId();
                if (idclass == 0) {
                    Toast.makeText(SeeStudentsListActivity.this, "Vui lòng chọn một lớp!", Toast.LENGTH_SHORT).show();
                     GetDataSV();
                } else {
                        Toast.makeText(SeeStudentsListActivity.this, "Đã cập nhật danh sách!", Toast.LENGTH_SHORT).show();
                        GetDataSV();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetDataSpinerClass();
    }

    private void listview_show() {
        studentsArrayList = new ArrayList<>();
        svAdapter = new SVAdapter(this, R.layout.custom_list_seesv, studentsArrayList);
        listSV.setAdapter(svAdapter);
    }

    private void anhxa() {
        listSV = findViewById(R.id.list_SeeSV);
        spinnerClass = findViewById(R.id.SpinerSeeSV);
        toolbar = findViewById(R.id.toolbar_SeeSV);
        //nhận iduser từ home
        Intent intent = getIntent();
        iduser = intent.getIntExtra("iduser", -1);

    }

    private void GetDataSV() {
        Cursor dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE idclass = '" + idclass + "' AND  iduser = '" + iduser + "'");
        studentsArrayList.clear();
        while (dataSV.moveToNext()) {
            int id = dataSV.getInt(0);
            String tenSV = dataSV.getString(1);
            String date = dataSV.getString(2);
            int idclass = dataSV.getInt(3);
            iduser = dataSV.getInt(4);
            String tenlop = dataSV.getString(5);
            String sdt = dataSV.getString(6);
            String email = dataSV.getString(7);
            String place = dataSV.getString(8);

            studentsArrayList.add(new Students(id, tenSV, date, idclass, iduser, tenlop, sdt, email, place, dataSV.getBlob(9)));

        }
        svAdapter.notifyDataSetChanged();
    }

    public void GetDataSVSearch() {
        Cursor dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE iduser = '" + iduser + "'");
        studentsArrayList.clear();
        while (dataSV.moveToNext()) {
            idsv = dataSV.getInt(0);
            String tenSV = dataSV.getString(1);
            String date = dataSV.getString(2);
            int idclass = dataSV.getInt(3);
            iduser = dataSV.getInt(4);
            String tenlop = dataSV.getString(5);
            String sdt = dataSV.getString(6);
            String email = dataSV.getString(7);
            String place = dataSV.getString(8);

            studentsArrayList.add(new Students(idsv, tenSV, date, idclass, iduser, tenlop, sdt, email, place, dataSV.getBlob(9)));
        }
        svAdapter = new SVAdapter(SeeStudentsListActivity.this, R.layout.custom_list_seesv, studentsArrayList);
        listSV.setAdapter(svAdapter);

        svAdapter.notifyDataSetChanged();
    }

    private void GetDataSpinerClass() {
        Cursor dataClass = MainActivity.database.GetData("SELECT * FROM Class WHERE iduser = '" + iduser + "'");
        while (dataClass.moveToNext()) {
            int id = dataClass.getInt(0);
            String tenlop = dataClass.getString(1);
            String malop = dataClass.getString(2);
            iduser = dataClass.getInt(3);
            arr_listSpiner.add(new AddClass(id, tenlop, malop, iduser));
        }
        spinnerAdapter.notifyDataSetChanged();

    }

    private void Toolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        GetDataSV();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                studentsArrayList.clear();
                svAdapter.search(s);
                svAdapter.notifyDataSetChanged();


                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }


}
