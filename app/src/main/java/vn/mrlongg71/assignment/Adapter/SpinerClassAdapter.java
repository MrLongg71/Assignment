package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.mrlongg71.assignment.Activity.ManageSVActivity;
import vn.mrlongg71.assignment.Model.AddClass;
import vn.mrlongg71.assignment.R;

public class SpinerClassAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<AddClass> addClassList;

    public SpinerClassAdapter(Context context, int layout, List<AddClass> addClassList) {
        this.context = context;
        this.layout = layout;
        this.addClassList = addClassList;
    }

    @Override
    public int getCount() {
        return addClassList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtSpinerClass = view.findViewById(R.id.txtSpinerClass);
        AddClass addClass = addClassList.get(i);
        txtSpinerClass.setText("" + addClass.getTenlop());

        return view;
    }
}
