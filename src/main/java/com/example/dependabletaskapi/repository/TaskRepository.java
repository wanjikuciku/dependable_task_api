package com.example.dependabletaskapi.repository;

import com.example.dependabletaskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
