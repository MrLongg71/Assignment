package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class DetailSVAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Students> detailSvList;

    public DetailSVAdapter(Context context, int layout, List<Students> detailSvList) {
        this.context = context;
        this.layout = layout;
        this.detailSvList = detailSvList;
    }

    @Override
    public int getCount() {
        return detailSvList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout,null);
        TextView txtTenSvDetail,txtNgaySinhSVDetail, txtTenLopDetail, txtSDTDetailSV, txtEmailDetailSV, txtPlaceDetailSV;
        ImageView imgAnhSV;

        txtTenSvDetail = convertView.findViewById(R.id.txtTenSVDetail);
        txtNgaySinhSVDetail = convertView.findViewById(R.id.txtNgaySinhSVDetail);
        txtTenLopDetail = convertView.findViewById(R.id.txtTenLopDetail);
        txtSDTDetailSV = convertView.findViewById(R.id.txtSDTSVDetail);
        txtEmailDetailSV = convertView.findViewById(R.id.txtEmailSVDetail);
        txtPlaceDetailSV = convertView.findViewById(R.id.txtPlaceSVDetail);
        imgAnhSV = convertView.findViewById(R.id.imgAnhSVdetail);


        Students students = detailSvList.get(position);
        txtTenSvDetail.setText("Tên SV: " + students.getTenSV());
        txtNgaySinhSVDetail.setText("Ngày sinh: " + students.getDate());
        txtEmailDetailSV.setText("Email: " + students.getEmail());
        txtTenLopDetail.setText("Tên Lớp: " + students.getTenlop());
        txtSDTDetailSV.setText("SĐT: " + students.getSdt());
        txtPlaceDetailSV.setText("Địa chỉ: " + students.getPlace());

        //chuyển byte -. bitmap
        byte[] imgSV = students.getImages();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgSV , 0, imgSV.length);
        imgAnhSV.setImageBitmap(bitmap);




        return convertView;
    }
}
