package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class StudentsAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Students> studentsList;

    public StudentsAdapter(Context context, int layout, List<Students> studentsList) {
        this.context = context;
        this.layout = layout;
        this.studentsList = studentsList;
    }

    @Override
    public int getCount() {
        return studentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHodler{
        TextView txtSTT, txtTenSV, txtDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView == null){
            viewHodler = new ViewHodler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHodler.txtSTT = convertView.findViewById(R.id.txtSTT);
            viewHodler.txtTenSV = convertView.findViewById(R.id.txtTenSV);
            viewHodler.txtDate = convertView.findViewById(R.id.txtDate);

            convertView.setTag(viewHodler);

        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }
        Students students = studentsList.get(position);
        viewHodler.txtSTT.setText("" +(position+1));
        viewHodler.txtTenSV.setText(students.getTenSV());
        viewHodler.txtDate.setText(students.getDate());
        return convertView;
    }
}
