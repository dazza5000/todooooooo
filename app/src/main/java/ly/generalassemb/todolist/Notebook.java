package ly.generalassemb.todolist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by darrankelinske on 6/13/16.
 */
public class Notebook {
    private static Notebook sNotebook;
    private List<ToDoList> mToDoLists;

    // Public method to get static instance of Notebook
    public static Notebook getInstance() {
        if (sNotebook == null) {
            sNotebook = new Notebook();
        }
        return sNotebook;
    }

    private Notebook(){
        mToDoLists = new ArrayList<>();
    }

    public List<ToDoList> getToDoLists() {
        return mToDoLists;
    }

    public ToDoList getToDoList(UUID id) {
        for(ToDoList todolist : mToDoLists) {
            if (todolist.getId().equals(id)) {
                return todolist;
            }
        }
        return null;
    }

    public void addToDoList(ToDoList toDoList){
        mToDoLists.add(toDoList);
    }

    public void removeToDoList(UUID toDoList){

        Iterator<ToDoList> it = mToDoLists.iterator();
        while (it.hasNext()) {
            ToDoList toDoListToCheck = it.next();
            if (toDoListToCheck.getId().equals(toDoList)) {
                it.remove();
            }
        }
    }

}

