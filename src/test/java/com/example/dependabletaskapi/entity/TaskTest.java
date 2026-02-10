package com.example.dependabletaskapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void testNoArgsConstructor() {
        Task task = new Task();
        assertNotNull(task);
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    void testConstructorWithTitle() {
        Task task = new Task("My Task");
        assertNotNull(task);
        assertEquals("My Task", task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    void testSetAndGetId() {
        Task task = new Task();
        task.setId(42L);
        assertEquals(42L, task.getId());
    }

    @Test
    void testSetAndGetTitle() {
        Task task = new Task();
        task.setTitle("Updated Title");
        assertEquals("Updated Title", task.getTitle());
    }

    @Test
    void testSetAndGetCompleted() {
        Task task = new Task("Task");
        assertFalse(task.isCompleted());
        
        task.setCompleted(true);
        assertTrue(task.isCompleted());
        
        task.setCompleted(false);
        assertFalse(task.isCompleted());
    }

    @Test
    void testTaskInitialStateFromConstructor() {
        Task task = new Task("New Task");
        assertEquals("New Task", task.getTitle());
        assertFalse(task.isCompleted());
        assertNull(task.getId());
    }

    @Test
    void testCompletedStateAfterUpdate() {
        Task task = new Task("Test Task");
        task.setId(1L);
        task.setCompleted(true);
        
        assertTrue(task.isCompleted());
        assertEquals(1L, task.getId());
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    void testMultipleFieldUpdates() {
        Task task = new Task();
        
        task.setId(100L);
        task.setTitle("First Title");
        task.setCompleted(false);
        
        assertEquals(100L, task.getId());
        assertEquals("First Title", task.getTitle());
        assertFalse(task.isCompleted());
        
        task.setTitle("Second Title");
        task.setCompleted(true);
        
        assertEquals(100L, task.getId());
        assertEquals("Second Title", task.getTitle());
        assertTrue(task.isCompleted());
    }
}
