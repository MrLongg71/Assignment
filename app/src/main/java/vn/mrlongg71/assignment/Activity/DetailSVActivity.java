package vn.mrlongg71.assignment.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import vn.mrlongg71.assignment.Adapter.DetailSVAdapter;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class DetailSVActivity extends AppCompatActivity {

    ListView listSVDetail;
    Button btnUpdate_DetailSv, btnDelete_DetailSv;
    ArrayList<Students> arr_DetailSv;
    DetailSVAdapter detailSVAdapter;
    int iduser, idclass,idsv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sv);

        anhxa();
        listview_show();

    }

    private void listview_show() {
        arr_DetailSv = new ArrayList<>();
        detailSVAdapter = new DetailSVAdapter(getApplicationContext(), R.layout.custom_list_detail_sv, arr_DetailSv);
        listSVDetail.setAdapter(detailSVAdapter);
        GetDataSV_Detail();
    }
    private void GetDataSV_Detail() {
        Cursor dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE id = '"+idsv+"' AND idclass = '"+idclass+"' AND iduser = '"+iduser+"'");
        arr_DetailSv.clear();
        while (dataSV.moveToNext()){
            int id = dataSV.getInt(0);
            String tenSV = dataSV.getString(1);
            String date = dataSV.getString(2);
            int idclass = dataSV.getInt(3);
            int iduser = dataSV.getInt(4);
            String tenlop = dataSV.getString(5);
            String sdt = dataSV.getString(6);
            String email = dataSV.getString(7);
            String place = dataSV.getString(8);

            arr_DetailSv.add(new Students(id, tenSV, date , idclass, iduser, tenlop, sdt,email,place, dataSV.getBlob(9) ));
        }
        detailSVAdapter.notifyDataSetChanged();
    }

    private void anhxa() {

        listSVDetail = findViewById(R.id.list_DetailSV);
        btnUpdate_DetailSv = findViewById(R.id.btnUpdateDetailSV);
        btnDelete_DetailSv = findViewById(R.id.btnUpdateDetailSV);
        //nhận dữ liệu từ intent màn hình SeétudentsActivity

        Intent intent = getIntent();
        iduser = intent.getIntExtra("iduser" , -1);
        idclass = intent.getIntExtra("idclass" , -1);
        idsv = intent.getIntExtra("idsv" ,  -1);

    }
}
