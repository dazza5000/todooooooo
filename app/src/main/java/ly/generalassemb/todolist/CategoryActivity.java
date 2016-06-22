package ly.generalassemb.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";

    // Declare our local variables
    private FloatingActionButton addCategoryFloatingActionButton;
    private RecyclerView categoryRecylerView;
    private Notebook notebookRepository;
    private List<ToDoList> categoryList;
    ToDoListAdapter categoryAdapter;

    String taskListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Log.d(TAG, "in onCreate()");

        if (null != savedInstanceState) {

            taskListName = savedInstanceState.getString("task_list");
        }

        Log.d(TAG, "our task list name is " +taskListName);

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
                    taskListName = editText.getText().toString();
                    ToDoList toDoList = new ToDoList(editText.getText().toString());
                    notebookRepository.addToDoList(toDoList);
                    categoryAdapter.setToDoLists(notebookRepository.getToDoLists());
                    categoryAdapter.notifyDataSetChanged();
                    Log.d(TAG, "This is our taskListName variable" + taskListName);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "in onResume");
        Log.d(TAG, "our task list name is " +taskListName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "in onPause()");
        Log.d(TAG, "our task list name is " +taskListName);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "in onStop()");
        Log.d(TAG, "our task list name is " +taskListName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("task_list", taskListName);
    }

    public interface TaskListListener {

        void onTaskListClick(UUID taskId);

        void onTaskListLongClick(UUID taskId);
    }

}
