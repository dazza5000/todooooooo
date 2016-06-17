package ly.generalassemb.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import static ly.generalassemb.todolist.CategoryActivity.*;

/**
 * Created by darrankelinske on 6/17/16.
 */
public class ToDoListHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener {

    private ToDoList mToDoList;

    private TextView mNameTextView;

    private TaskListListener mListener;

    public ToDoListHolder(View itemView, TaskListListener listListener) {
        super(itemView);
        mListener = listListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        mNameTextView = (TextView) itemView;

    }

    public void bindToDoList(ToDoList toDo) {
        mToDoList = toDo;
        mNameTextView.setText(mToDoList.getName());

    }

    @Override
    public void onClick(View v) {
        mListener.onTaskListClick(mToDoList.getId());
    }

    @Override
    public boolean onLongClick(View v) {
        mListener.onTaskListLongClick(mToDoList.getId());
        return true;
    }
}