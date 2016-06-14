package ly.generalassemb.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by darrankelinske on 6/14/16.
 */
public class ToDoAdapter extends BaseAdapter {
    private List<ToDo> mToDos;
    private Context context;

    public void setList(List<ToDo> toDos) {
        this.mToDos = toDos;
    }

    public ToDoAdapter(Context context, List<ToDo> toDos) {
        this.context = context;
        this.mToDos = toDos;
    }

    @Override
    public int getCount() {
        return mToDos.size();
    }

    @Override
    public Object getItem(int position) {
        return mToDos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.to_do_list_item, null);
        }

        TextView textView1 = (TextView)v.findViewById(R.id.text1);
        TextView textView2 = (TextView)v.findViewById(R.id.text2);

        textView1.setText("Name: "+ mToDos.get(position).getName());
        textView2.setText("Description: "+ mToDos.get(position).getDescription());
        return v;
    }

}
