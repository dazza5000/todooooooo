package ly.generalassemb.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static ly.generalassemb.todolist.CategoryActivity.*;

/**
 * Created by darrankelinske on 6/17/16.
 */
public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListHolder> {

    private List<ToDoList> toDoLists;

    private TaskListListener listener;

    public ToDoListAdapter(List<ToDoList> toDoLists, TaskListListener listener) {
        this.toDoLists = toDoLists;
        this.listener = listener;
    }

    @Override
    public ToDoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ToDoListHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ToDoListHolder holder, int position) {
        ToDoList toDoList = toDoLists.get(position);
        holder.bindToDoList(toDoList);
    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
        notifyDataSetChanged();
    }
}