package com.example.dependabletaskapi.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.dependabletaskapi.entity.Task;
import com.example.dependabletaskapi.exception.TaskNotFoundException;
import com.example.dependabletaskapi.repository.TaskRepository;

class TaskServiceTest {

    private TaskRepository repository;
    private TaskService service;

    @BeforeEach
    void setUp() {
        repository = mock(TaskRepository.class);
        service = new TaskService(repository);
    }

    @Test
    void createTask_createsTaskAndSavesIt() {
        Task savedTask = new Task("Test task");
        when(repository.save(any(Task.class))).thenReturn(savedTask);

        Task result = service.createTask("Test task");

        assertNotNull(result);
        assertFalse(result.isCompleted());
        verify(repository).save(any(Task.class));
    }

    @Test
    void createTask_withEmptyTitle() {
        Task savedTask = new Task("");
        when(repository.save(any(Task.class))).thenReturn(savedTask);

        Task result = service.createTask("");

        assertNotNull(result);
        assertEquals("", result.getTitle());
        verify(repository).save(any(Task.class));
    }

    @Test
    void createTask_withLongTitle() {
        String longTitle = "A".repeat(500);
        Task savedTask = new Task(longTitle);
        when(repository.save(any(Task.class))).thenReturn(savedTask);

        Task result = service.createTask(longTitle);

        assertNotNull(result);
        assertEquals(longTitle, result.getTitle());
        verify(repository).save(any(Task.class));
    }

    @Test
    void getAllTasks_returnsAllTasks() {
        when(repository.findAll()).thenReturn(
                List.of(new Task("Task A"), new Task("Task B"))
        );

        List<Task> tasks = service.getAllTasks();

        assertEquals(2, tasks.size());
        verify(repository).findAll();
    }

    @Test
    void getAllTasks_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Task> tasks = service.getAllTasks();

        assertTrue(tasks.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void getAllTasks_returnsSingleTask() {
        when(repository.findAll()).thenReturn(List.of(new Task("Single Task")));

        List<Task> tasks = service.getAllTasks();

        assertEquals(1, tasks.size());
        verify(repository).findAll();
    }

    @Test
    void markTaskCompleted_marksTaskAsCompleted() {
        Task task = new Task("Task");
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.markTaskCompleted(1L);

        assertTrue(result.isCompleted());
        verify(repository).save(task);
    }

    @Test
    void markTaskCompleted_throwsExceptionIfTaskNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> service.markTaskCompleted(1L)
        );
    }

    @Test
    void markTaskCompleted_withDifferentIds() {
        Task task = new Task("Task");
        when(repository.findById(999L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.markTaskCompleted(999L);

        assertTrue(result.isCompleted());
        verify(repository).findById(999L);
        verify(repository).save(task);
    }

    @Test
    void deleteTask_deletesTaskIfExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteTask(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteTask_throwsExceptionIfTaskDoesNotExist() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(
                TaskNotFoundException.class,
                () -> service.deleteTask(1L)
        );
    }

    @Test
    void deleteTask_withDifferentIds() {
        when(repository.existsById(555L)).thenReturn(true);

        service.deleteTask(555L);

        verify(repository).deleteById(555L);
    }

    @Test
    void deleteTask_nonExistentWithDifferentId() {
        when(repository.existsById(777L)).thenReturn(false);

        assertThrows(
                TaskNotFoundException.class,
                () -> service.deleteTask(777L)
        );
        verify(repository, never()).deleteById(777L);
    }

    @Test
    void constructor_initializesRepository() {
        TaskService taskService = new TaskService(repository);
        assertNotNull(taskService);
    }
}
