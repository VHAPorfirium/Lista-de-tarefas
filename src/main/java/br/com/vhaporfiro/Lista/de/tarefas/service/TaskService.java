package br.com.vhaporfiro.Lista.de.tarefas.service;

import br.com.vhaporfiro.Lista.de.tarefas.model.Task;
import br.com.vhaporfiro.Lista.de.tarefas.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna todas as tarefas cadastradas.
     */
    public List<Task> findAll() {
        return repository.findAll();
    }

    /**
     * Busca uma tarefa pelo ID. Lança exception se não encontrar.
     */
    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID " + id));
    }

    /**
     * Cria uma nova tarefa após validar seus campos.
     */
    public Task create(Task task) {
        validate(task);
        return repository.save(task);
    }

    /**
     * Atualiza uma tarefa existente.
     */
    public Task update(Long id, Task updated) {
        Task existing = findById(id);

        validate(updated);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDueDate(updated.getDueDate());

        return repository.save(existing);
    }

    /**
     * Exclui uma tarefa pelo ID.
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não existe tarefa com ID " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Valida regras básicas de negócio antes de persistir.
     */
    private void validate(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("O título da tarefa não pode ficar vazio.");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("A data de conclusão deve ser informada.");
        }
        // aqui você pode adicionar outras validações, como data futura, tamanho máximo de descrição etc.
    }
}
