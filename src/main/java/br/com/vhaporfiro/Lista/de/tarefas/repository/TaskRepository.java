package br.com.vhaporfiro.Lista.de.tarefas.repository;

import br.com.vhaporfiro.Lista.de.tarefas.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
