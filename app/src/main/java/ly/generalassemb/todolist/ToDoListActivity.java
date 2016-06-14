package ly.generalassemb.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class ToDoListActivity extends AppCompatActivity {
    // Declare our constants
    private static final String EXTRA_TODO_ID = "ly.generalassemb.todolist.todo_id";

    // Declare our local variables
    private FloatingActionButton addToDoFab;
    private ListView toDoListView;
    private Notebook notebookRepository;
    private ToDoList toDoList;
    private List<ToDo> toDos;
    private ToDoAdapter toDoAdapter;

    public static Intent newIntent(Context packageContext, UUID toDoId){
        Intent intent = new Intent(packageContext, ToDoListActivity.class);
        intent.putExtra(EXTRA_TODO_ID, toDoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        addToDoFab = (FloatingActionButton) findViewById(R.id.add_to_do_fab);
        toDoListView = (ListView) findViewById(R.id.to_dos_list_view);

        UUID toDoId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);

        Notebook notebook = Notebook.getInstance();
        toDoList = notebook.getToDoList(toDoId);
        toDos = toDoList.getmToDos();

        toolbar.setTitle(toDoList.getName());
        setSupportActionBar(toolbar);

        toDoAdapter = new ToDoAdapter(this, toDos);

        toDoListView.setAdapter(toDoAdapter);

        View.OnClickListener fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        };

        addToDoFab.setOnClickListener(fabListener);

        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ToDoListActivity.this, "finish me!", Toast.LENGTH_SHORT).show();
            }
        });

        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoList.getmToDos().remove(position);
                toDos = toDoList.getmToDos();
                toDoAdapter.notifyDataSetChanged();
                return true;
            }
        });;
    }

    public void showInputDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_todo_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText nameText = (EditText) dialogView.findViewById(R.id.todo_name);
        final EditText descriptionText = (EditText) dialogView.findViewById(R.id.todo_description);

        dialogBuilder.setTitle("Add ToDo");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(nameText.getText().toString().length() == 0) {
                    Toast.makeText(ToDoListActivity.this, "Please enter a ToDo name",
                            Toast.LENGTH_LONG).show();
                } else if(descriptionText.getText().toString().length() == 0) {
                    Toast.makeText(ToDoListActivity.this, "Please enter a ToDo description",
                            Toast.LENGTH_LONG).show();
                } else {
                    ToDo toDo = new ToDo(nameText.getText().toString(),
                            descriptionText.getText().toString());
                    toDoList.addToDo(toDo);
                    toDos = toDoList.getmToDos();
                    toDoAdapter.notifyDataSetChanged();
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
