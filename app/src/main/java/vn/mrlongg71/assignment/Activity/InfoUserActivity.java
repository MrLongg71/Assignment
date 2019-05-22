package vn.mrlongg71.assignment.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.mrlongg71.assignment.Adapter.UserAdapter;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.Model.User;

import static vn.mrlongg71.assignment.Adapter.UserAdapter.user;

public class InfoUserActivity extends AppCompatActivity {

    Button btnEditUser, btnDeleteUser, btnUpdate, btnHuy;
    EditText edtUserUpdate, edtNameUpdate, edtPlaceUpdate, edtPhoneUpdate;
    Cursor dataUser;
    ListView listViewInfo;
    ArrayList<User> userArrayList;
    UserAdapter userArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        anhxa();
        btnEditUser();
        btnDeleteUser();
        //select data
        GetDataUser();
    }

    private void btnDeleteUser() {
        //xóa user
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(user.getId(), user.getUser());
            }
        });
    }

    public void GetDataUser(){
        Intent intent = getIntent();
        int idput = intent.getIntExtra("id",-1);
        dataUser = MainActivity.database.GetData("SELECT * FROM User WHERE id = '"+ idput +"'");
       //clear mảng trước khi thêm
        userArrayList.clear();
        while (dataUser.moveToNext()){
            int id = dataUser.getInt(0);
            String user = dataUser.getString(1);
            String name = dataUser.getString(3);
            String place = dataUser.getString(4);
            String phone = dataUser.getString(5);
            userArrayList.add(new User(id, user, name, place, phone));
        }
        userArrayAdapter.notifyDataSetChanged();
    }

    //cập nhật user
    private void btnEditUser() {
    btnEditUser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialogUpdate(user.getId(), user.getUser() , user.getName(),
                    user.getPlace() , user.getPhone());
       }
    });

    }
    //dialog delete user
    private  void dialogDelete(final int id, final String user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.name);
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có muốn xóa tài khoản " + user );
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.database.QueryData("DELETE FROM User WHERE id = '" + id+"'");
                Toast.makeText(InfoUserActivity.this, "Đã xóa tài khoản " + user, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InfoUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }
    //dialog update
    private void dialogUpdate(final int id, String user, String name, String place, String phone){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_editinfo);
        dialog.show();
        //ánh xạ view custom dialog
        edtUserUpdate  = dialog.findViewById(R.id.edtUserEdit);
        edtNameUpdate  = dialog.findViewById(R.id.edtNameEdit);
        edtPlaceUpdate = dialog.findViewById(R.id.edtPlaceEdit);
        edtPhoneUpdate = dialog.findViewById(R.id.edtPhoneEdit);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);

//        //settext cho dialog
        edtUserUpdate.setText(user);
        edtNameUpdate.setText(name);
        edtPlaceUpdate.setText(place);
        edtPhoneUpdate.setText(phone);
        //btn dismiss
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //btn update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy dữ liệu mới
                String userupdate = edtUserUpdate.getText().toString().trim();
                String nameupdate = edtNameUpdate.getText().toString().trim();
                String placeupdate = edtPlaceUpdate.getText().toString().trim();
                String phoneupdate = edtPhoneUpdate.getText().toString().trim();
                MainActivity.database.QueryData("UPDATE User SET user = '" + userupdate+"', name = '" + nameupdate+"', place = '"+ placeupdate+"' ,  phone = '"+phoneupdate+"'  WHERE id = '"+ id+"'");
                Toast.makeText(InfoUserActivity.this, "Đã Cập nhật!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataUser();
            }
        });

    }

    private void anhxa() {
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        btnEditUser = findViewById(R.id.btnEditUser);
        //list view
        listViewInfo = findViewById(R.id.listInfoUser);
        userArrayList = new ArrayList<>();
        userArrayAdapter = new UserAdapter(this, R.layout.custom_listview_userinfo, userArrayList);
        listViewInfo.setAdapter(userArrayAdapter);
    }
}
