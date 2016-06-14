package ly.generalassemb.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    // Declare our local variables
    private FloatingActionButton addCategoryFloatingActionButton;
    private ListView categoryListView;
    private Notebook notebookRepository;
    private List<ToDoList> categoryList;
    ArrayAdapter<ToDoList> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // assign our local variables that are widgets
        addCategoryFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_to_do_fab);
        categoryListView = (ListView) findViewById(R.id.category_list_view);

        notebookRepository = Notebook.getInstance();
        categoryList = notebookRepository.getToDoLists();

        categoryAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);

        categoryListView.setAdapter(categoryAdapter);

        View.OnClickListener fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        };

        addCategoryFloatingActionButton.setOnClickListener(fabListener);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDoListIntent = ToDoListActivity.newIntent(CategoryActivity.this,
                        categoryList.get(position).getId());
                startActivity(toDoListIntent);
            }
        });

        categoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                notebookRepository.removeToDoList(categoryList.get(position).getId());
                categoryList = notebookRepository.getToDoLists();
                categoryAdapter.notifyDataSetChanged();
                return true;
            }
        });

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
                    categoryList = notebookRepository.getToDoLists();
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



}
