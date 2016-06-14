package ly.generalassemb.todolist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by darrankelinske on 6/13/16.
 */
public class ToDoList {

    private UUID id;
    private String name;
    private List<ToDo> mToDos;

    public ToDoList(String name) {
        // Using UUID's is one way to get random id's for objects
        id = UUID.randomUUID();
        this.name = name;
        mToDos = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public List<ToDo> getmToDos() {
        return mToDos;
    }

    public void setmToDos(List<ToDo> mToDos) {
        this.mToDos = mToDos;
    }

    public void addToDo(ToDo toDoToAdd) {
        mToDos.add(toDoToAdd);
    }

    public void removeToDo(int toDoToRemove) {
        mToDos.remove(toDoToRemove);
    }
}
