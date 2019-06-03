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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.mrlongg71.assignment.Model.AddClass;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.Activity.SeeClassListActivity;

public class ClassAdapter extends BaseAdapter {
    private SeeClassListActivity context;
    private  int layout;
    public static List<AddClass> addClassList;
    public ArrayList<AddClass> arrayListCopy;

    public ClassAdapter(SeeClassListActivity context, int layout, List<AddClass> addClassList) {
        this.context = context;
        this.layout = layout;
        this.addClassList = addClassList;
        this.arrayListCopy = new ArrayList<>();
        this.arrayListCopy.addAll(addClassList);

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
    private class ViewHolder{
        TextView txtId, txtMalop, txtTenlop;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtId = view.findViewById(R.id.txtId);
            holder.txtMalop = view.findViewById(R.id.txtMalop);
            holder.txtTenlop = view.findViewById(R.id.txtTenlop);
            view.setTag(holder);


        }else{
            holder = (ViewHolder) view.getTag();
        }
        final AddClass addClass = addClassList.get(i);

        holder.txtId.setText(i + 1 +"");
        holder.txtMalop.setText(addClass.getMalop());
        holder.txtTenlop.setText(addClass.getTenlop());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_listview);
        view.startAnimation(animation);
        return view;
    }

    //searchclass
    public void search(String text){

        text = text.toLowerCase();
        addClassList.clear();
        //nếu ô tìm kiếm không có -> add lại mảng  :
        if(text.length() == 0){
            addClassList.addAll(arrayListCopy);
        }else{
            for(AddClass addclass : arrayListCopy){
                if(addclass.getTenlop().toLowerCase().contains(text)){
                    addClassList.add(addclass);
                }
                notifyDataSetChanged();
            }
        }
    }
}
