package vn.mrlongg71.assignment.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.mrlongg71.assignment.Adapter.DetailSVAdapter;
import vn.mrlongg71.assignment.Adapter.SVAdapter;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class DetailSVActivity extends AppCompatActivity {

    ListView listSVDetail;
    Button btnUpdate_DetailSv, btnDelete_DetailSv;
    ArrayList<Students> arr_DetailSv;
    DetailSVAdapter detailSVAdapter;
    EditText edtTenSVUpdate, edtNgaysinhSVupdate, edtTenlopSVUpdate, edtSDTSvUpdate, edtEmailSVUpdate, edtDiachiSVUpdate;
    Toolbar toolbar;
    int iduser, idclass,idsv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sv);

        anhxa();
        listview_show();
        //back về
        Toolbar();
        //xử lí xóa sv
        deleteSV();
        //btnupdate
        btnupdate();


    }

    private void btnupdate() {


        btnUpdate_DetailSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateSV(SVAdapter.students.getId(), SVAdapter.students.getTenSV(),SVAdapter.students.getDate(),SVAdapter.students.getTenlop(),SVAdapter.students.getSdt(),SVAdapter.students.getEmail(),SVAdapter.students.getPlace());
            }
        });


    }

    //updateSV
    private void dialogUpdateSV(final int id, final String tenSV, String ngaysinhSV, String tenLop, String sdt, String email, String diachi) {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_updatesv);
        Button btnHuyUpdateSv = dialog.findViewById(R.id.btnDialogHuyUpdateSV);
        Button btnUpdateSV = dialog.findViewById(R.id.btnDialogUpdateSV);

        edtTenSVUpdate = dialog.findViewById(R.id.edtTenSVUpdate);
        edtNgaysinhSVupdate = dialog.findViewById(R.id.edtNgaySinhSVUpdate);
        edtTenlopSVUpdate = dialog.findViewById(R.id.edtTenlopSVUpdate);
        edtSDTSvUpdate = dialog.findViewById(R.id.edtSDTSVUpdate);
        edtEmailSVUpdate = dialog.findViewById(R.id.edtEmailSVUpdate);
        edtDiachiSVUpdate = dialog.findViewById(R.id.edtDiaChiSVUpdate);
        edtTenSVUpdate.setText(tenSV);
        edtNgaysinhSVupdate.setText(ngaysinhSV);
        edtTenlopSVUpdate.setText(tenLop);
        edtSDTSvUpdate.setText(sdt);
        edtEmailSVUpdate.setText(email);
        edtDiachiSVUpdate.setText(diachi);

        btnHuyUpdateSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpdateSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSVnew  = edtTenSVUpdate.getText().toString().trim();
                String ngaysinhSVnew = edtNgaysinhSVupdate.getText().toString().trim();
                String tenLopnew = edtTenlopSVUpdate.getText().toString().trim();
                String sdtnew = edtSDTSvUpdate.getText().toString().trim();
                String emailnew = edtEmailSVUpdate.getText().toString().trim();
                String diachinew = edtDiachiSVUpdate.getText().toString().trim();

                MainActivity.database.QueryData("UPDATE Students SET id = '"+id+"' ,tensv = '"+tenSVnew+"',ngaysinh = '"+ngaysinhSVnew+"', idclass = '"+idclass+"', iduser = '"+iduser+"', tenlop = '"+tenLopnew+"',sdt = '"+sdtnew+"', email = '"+emailnew+"', place = '"+diachinew+"' WHERE id = '"+idsv+"' AND idclass = '"+idclass+"' AND iduser = '"+iduser+"'");

                GetDataSV_Detail();
                Toast.makeText(DetailSVActivity.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();



    }

    //xử lí xóa sv
    private void deleteSV() {
        btnDelete_DetailSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder aBuilder = new AlertDialog.Builder(DetailSVActivity.this);
                aBuilder.setIcon(R.drawable.error);
                aBuilder.setTitle("Thông báo");
                aBuilder.setMessage("Bạn có muốn xóa Sinh viên " );

                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.database.QueryData("DELETE FROM Students WHERE id = '"+idsv+"' AND idclass = '"+idclass+"' AND iduser = '"+iduser+"'");
                        Toast.makeText(getApplicationContext(), "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailSVActivity.this, HomeActivity.class));
                        finishActivity(123);

                    }
                });
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                aBuilder.show();
            }
        });



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



    private void anhxa() {

        listSVDetail = findViewById(R.id.list_DetailSV);
        btnUpdate_DetailSv = findViewById(R.id.btnUpdateDetailSV);
        btnDelete_DetailSv = findViewById(R.id.btnDeleteDetailSV);
        toolbar = findViewById(R.id.toolbar_Detail);
        //nhận dữ liệu từ intent màn hình SeétudentsActivity

        Intent intent = getIntent();
        iduser = intent.getIntExtra("iduser" , -1);
        idclass = intent.getIntExtra("idclass" , -1);
        idsv = intent.getIntExtra("idsv" ,  -1);

    }
}
