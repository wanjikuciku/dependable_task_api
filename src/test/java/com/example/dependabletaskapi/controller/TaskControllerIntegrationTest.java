package com.example.dependabletaskapi.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.dependabletaskapi.entity.Task;
import com.example.dependabletaskapi.exception.TaskNotFoundException;
import com.example.dependabletaskapi.repository.TaskRepository;
import com.example.dependabletaskapi.service.TaskService;

@DataJpaTest
@ActiveProfiles("test")
class TaskControllerIntegrationTest {

    @Autowired
    private TaskRepository repository;
    
    private TaskService service;
    private TaskController controller;

    @BeforeEach
    void setUp() {
        service = new TaskService(repository);
        controller = new TaskController(service);
    }

    @Test
    void testGetAllTasks_empty() {
        List<Task> result = controller.getAllTasks();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllTasks_withTasks() {
        controller.createTask(new Task("Task 1"));
        controller.createTask(new Task("Task 2"));

        List<Task> result = controller.getAllTasks();

        assertEquals(2, result.size());
    }

    @Test
    void testCreateTask() {
        Task newTask = new Task("New Task");

        Task result = controller.createTask(newTask);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        assertFalse(result.isCompleted());
    }

    @Test
    void testCompleteTask_success() {
        Task created = controller.createTask(new Task("To Complete"));
        Long taskId = created.getId();

        Task result = controller.completeTask(taskId);

        assertTrue(result.isCompleted());
        assertEquals(taskId, result.getId());
    }

    @Test
    void testCompleteTask_notFound() {
        assertThrows(TaskNotFoundException.class, () -> controller.completeTask(999L));
    }

    @Test
    void testDeleteTask_success() {
        Task created = controller.createTask(new Task("To Delete"));
        Long taskId = created.getId();

        controller.deleteTask(taskId);

        assertThrows(TaskNotFoundException.class, () -> controller.completeTask(taskId));
    }

    @Test
    void testDeleteTask_notFound() {
        assertThrows(TaskNotFoundException.class, () -> controller.deleteTask(999L));
    }

    @Test
    void testCompleteTask_alreadyCompleted() {
        Task created = controller.createTask(new Task("Test"));
        Long taskId = created.getId();
        
        controller.completeTask(taskId);
        Task result = controller.completeTask(taskId);
        
        assertTrue(result.isCompleted());
    }
}
