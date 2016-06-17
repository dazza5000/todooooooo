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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

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

        // Assign our local variables that are widgets
        addCategoryFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_to_do_fab);
        categoryRecylerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        categoryRecylerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

        // Get Singleton and then retrieve the ToDoLists from the Singleton
        notebookRepository = Notebook.getInstance();
        categoryList = notebookRepository.getToDoLists();

        TaskListListener myListener = new TaskListListener() {
            @Override
            public void onTaskListClick(UUID taskId) {
                Intent intent = ToDoListActivity.newIntent(CategoryActivity.this, taskId);
                startActivity(intent);

            }

            @Override
            public void onTaskListLongClick(UUID taskId) {
                notebookRepository.removeToDoList(taskId);
                categoryList = notebookRepository.getToDoLists();
                categoryAdapter.setToDoLists(categoryList);
                categoryAdapter.notifyDataSetChanged();
            }
        };

        categoryAdapter = new ToDoListAdapter(categoryList, myListener);

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

    public interface TaskListListener {

        void onTaskListClick(UUID taskId);

        void onTaskListLongClick(UUID taskId);
    }

}
