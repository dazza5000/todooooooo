package ly.generalassemb.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    // Declare our local variables
    private FloatingActionButton addCategoryFloatingActionButton;
    private RecyclerView categoryRecylerView;
    private Notebook notebookRepository;
    private List<ToDoList> categoryList;
    ToDoListAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // assign our local variables that are widgets
        addCategoryFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_to_do_fab);
        categoryRecylerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        categoryRecylerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

        notebookRepository = Notebook.getInstance();
        categoryList = notebookRepository.getToDoLists();

        categoryAdapter = new ToDoListAdapter(categoryList);

        categoryRecylerView.setAdapter(categoryAdapter);

        View.OnClickListener fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        };

        addCategoryFloatingActionButton.setOnClickListener(fabListener);


    }

    public void showInputDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_category_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.title_text);

        dialogBuilder.setTitle("List Title");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(editText.getText().toString().length() == 0) {
                    Toast.makeText(CategoryActivity.this, "Please enter a list title",
                            Toast.LENGTH_LONG).show();
                } else {
                    ToDoList toDoList = new ToDoList(editText.getText().toString());
                    notebookRepository.addToDoList(toDoList);
                    categoryAdapter.setToDoLists(notebookRepository.getToDoLists());
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private class ToDoListHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        private ToDoList mToDoList;

        private TextView mNameTextView;

        public ToDoListHolder(View itemView) {
            super(itemView);
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
            Intent intent = ToDoListActivity.newIntent(CategoryActivity.this, mToDoList.getId());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            notebookRepository.removeToDoList(mToDoList.getId());
            categoryList = notebookRepository.getToDoLists();
            categoryAdapter.notifyDataSetChanged();
            return true;
        }
    }

    private class ToDoListAdapter extends RecyclerView.Adapter<ToDoListHolder> {

        private List<ToDoList> toDoLists;

        public ToDoListAdapter(List<ToDoList> toDoLists) {
            this.toDoLists = toDoLists;
        }

        @Override
        public ToDoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(CategoryActivity.this);
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ToDoListHolder(view);
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



}
