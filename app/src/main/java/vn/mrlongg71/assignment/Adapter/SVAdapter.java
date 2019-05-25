package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.util.List;

import vn.mrlongg71.assignment.Model.AddClass;
import vn.mrlongg71.assignment.Model.Students;
import vn.mrlongg71.assignment.R;

public class SVAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Students> studentsList;

    public SVAdapter(Context context, int layout, List<Students> studentsList) {
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
    public class ViewHolder{

        ImageView imgAnhSV;
        TextView txtTenSv, txtLop;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.imgAnhSV = convertView.findViewById(R.id.imgAnhSV);
            viewHolder.txtTenSv = convertView.findViewById(R.id.txtTensv_SeeSV);
            viewHolder.txtLop = convertView.findViewById(R.id.txtLopsv_SeeSV);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Students students = studentsList.get(position);
        viewHolder.txtTenSv.setText(students.getTenSV());
        viewHolder.txtLop.setText(students.getDate());

        //gán animotion
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview);
        convertView.startAnimation(animation);


        return convertView;
    }
}
