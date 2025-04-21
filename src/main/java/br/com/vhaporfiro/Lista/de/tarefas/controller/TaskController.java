package br.com.vhaporfiro.Lista.de.tarefas.controller;

import br.com.vhaporfiro.Lista.de.tarefas.model.Task;
import br.com.vhaporfiro.Lista.de.tarefas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciar as rotas de Task.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    // Construtor para injeção de TaskService
    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * Lista todas as tarefas
     * GET /tasks
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";       // Thymeleaf: tasks.html
    }

    /**
     * Exibe o formulário para criar uma nova tarefa
     * GET /tasks/new
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task_form";   // Thymeleaf: task_form.html
    }

    /**
     * Recebe o POST do formulário de criação
     * POST /tasks
     */
    @PostMapping
    public String create(@ModelAttribute Task task) {
        service.create(task);
        return "redirect:/tasks";
    }

    /**
     * Exibe o formulário para editar uma tarefa existente
     * GET /tasks/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task existing = service.findById(id);
        model.addAttribute("task", existing);
        return "task_form";   // reutiliza task_form.html para criação/edição
    }

    /**
     * Recebe o POST do formulário de edição
     * POST /tasks/{id}
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Task task) {
        service.update(id, task);
        return "redirect:/tasks";
    }

    /**
     * Exclui uma tarefa
     * GET /tasks/delete/{id}
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/tasks";
    }

}
