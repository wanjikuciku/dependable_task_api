package com.example.dependabletaskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.dependabletaskapi.entity.Task;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired private TaskRepository repo;

    @Test
    void saveAndFindAll_works() {
        repo.save(new Task("RepoTask"));
        assertThat(repo.findAll()).isNotEmpty();
    }
}
