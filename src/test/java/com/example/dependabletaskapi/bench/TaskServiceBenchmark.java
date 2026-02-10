package com.example.dependabletaskapi.bench;

import static java.util.Collections.emptyList;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.example.dependabletaskapi.repository.TaskRepository;
import com.example.dependabletaskapi.service.TaskService;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class TaskServiceBenchmark {

    private TaskService service;

    @Setup
    public void setup() {
        TaskRepository repo = mock(TaskRepository.class);
        when(repo.findAll()).thenReturn(emptyList());
        service = new TaskService(repo);
    }

    @Benchmark
    public void benchmarkGetAllTasks() {
        service.getAllTasks();
    }
}
