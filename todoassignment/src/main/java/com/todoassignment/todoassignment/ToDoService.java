package com.todoassignment.todoassignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Dev
 */

@Service
public class ToDoService {
    Map<String, ToDo> todos = new HashMap<>();
    String fileName = "todos.json";
    
    void load() {
        try {
            // we will load all the todos from the json
            File file = new File(fileName);
            ObjectMapper objectMapper = new ObjectMapper();
            
            if (file.exists()) {
                // read
                Collection<ToDo> loadedTodos = objectMapper.readValue(file, new TypeReference<Collection<ToDo>>() {});
                for (ToDo loadedTodo: loadedTodos) {
                    todos.put(loadedTodo.getId(), loadedTodo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void sync() {
        // we will sync json and in memory data
        try {
            File file = new File(fileName);
            ObjectMapper objectMapper = new ObjectMapper();
            
            if (file.exists()) {
                file.delete();
            }
            
            Collection<ToDo> todoToWrite = new ArrayList<ToDo>();
            for (Map.Entry<String, ToDo> entry: todos.entrySet()) {
                todoToWrite.add(entry.getValue());
            }
            
            objectMapper.writeValue(file, todoToWrite);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Collection<ToDo> getAllTodos() {
        return todos.values();
    }
    
    public ToDo create(ToDo note) {
        todos.put(note.getId(), note);
        this.sync();
        return note;
    }
    
    public ToDo update(String id, String content) {
        ToDo currentTodo = todos.getOrDefault(id, null);
        if (currentTodo != null) {
            currentTodo.setContent(content);
            todos.put(id, currentTodo);
        }
        this.sync();
        return currentTodo;
    }

    public ToDo getNoteById(String id) {
        return todos.getOrDefault(id, null);
    }
    
    public void deleteNote(String id) {
        todos.remove(id);
        this.sync();
    }
}
