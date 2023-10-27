/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.todoassignment.todoassignment;

import jakarta.annotation.PostConstruct;
import org.springframework.ui.Model;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

/**
 *
 * @author Dev
 */
@Controller
@RequestMapping("/todos")
public class ToDoController {
    @Autowired
    private ToDoService todoService;
    
    @PostConstruct
    public void load() {
        System.out.println("Loading json db file!");
        todoService.load();
    }
   
    @GetMapping("/all")
    public String getAllTodos(Model model) {
        model.addAttribute("todosList", todoService.getAllTodos());
        return "todos";
    }
    
    @GetMapping("/create")
    public String create(@RequestParam("title") String title, @RequestParam("content") String content) {
        todoService.create(new ToDo(UUID.randomUUID().toString(), title, content));
        
        return "redirect:/todos/all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, @RequestParam("content") String content) {
        todoService.update(id, content);
        return "redirect:/todos/all";
    }

    @GetMapping("/delete/id={id}")
    public String delete(@PathVariable String id) {
        todoService.deleteNote(id);
        return "redirect:/todos/all";
    }

}
