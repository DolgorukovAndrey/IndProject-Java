package com.price.controller;

import com.price.entity.ServiceC;
import com.price.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Контроллер для управления услугами автомоек.
 * <p>
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для услуг.
 * Предоставляет веб-интерфейс для создания, просмотра, обновления и удаления
 * записей об услугах автомоек.
 *
 * <p><b>Примечание:</b> Класс сущности назван {@code ServiceC} во избежание
 * конфликта имен с зарезервированным словом "service" в некоторых контекстах.
 *
 * <p><b>Маршруты:</b>
 * <table border="1">
 *   <tr><th>Метод</th><th>URL</th><th>Описание</th></tr>
 *   <tr><td>GET</td><td>/services</td><td>Список всех услуг</td></tr>
 *   <tr><td>GET</td><td>/services/newService</td><td>Форма создания новой услуги</td></tr>
 *   <tr><td>POST</td><td>/services</td><td>Создание новой услуги</td></tr>
 *   <tr><td>GET</td><td>/services/editService/{id}</td><td>Форма редактирования услуги</td></tr>
 *   <tr><td>POST</td><td>/services/updateService/{id}</td><td>Обновление услуги</td></tr>
 *   <tr><td>GET</td><td>/services/deleteService/{id}</td><td>Удаление услуги</td></tr>
 * </table>
 *
 * @see Controller
 * @see RequestMapping
 * @see ServiceC
 * @see ServiceService
 */
@Controller
@RequestMapping("/services")
public class ServiceController {
    /**
     * Сервис для работы с услугами.
     */
    private final ServiceService serviceService;

    /**
     * Конструктор с инъекцией зависимости сервиса.
     *
     * @param serviceService сервис для работы с услугами; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code serviceService} равен {@code null}
     */
    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * Отображает список всех услуг автомоек.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления для отображения списка услуг
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping
    public String listService(Model model) {
        List<ServiceC> services = serviceService.getAllService();
        model.addAttribute("services", services);
        model.addAttribute("serviceCount", services.size());
        return "services/list";
    }

    /**
     * Отображает форму для создания новой услуги.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления формы создания услуги
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping("/newService")
    public String showCreateForm(Model model) {
        model.addAttribute("service", new ServiceC());
        model.addAttribute("action", "create");
        return "services/form";
    }

    /**
     * Обрабатывает создание новой услуги.
     * <p>
     * Валидирует введенные данные и сохраняет новую услугу в базе данных.
     * При успешном сохранении перенаправляет на список услуг с сообщением об успехе.
     *
     * @param service объект услуги с данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code service} равен {@code null}
     *
     * @see Valid
     * @see ModelAttribute
     * @see PostMapping
     * @see RedirectAttributes
     */
    @PostMapping
    public String createService(@Valid @ModelAttribute("service") ServiceC service,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "services/form";
        }
        serviceService.saveServiceC(service);
        redirectAttributes.addFlashAttribute("successMessage",
                "Услуга " + service.getServiceName() + " успешно добавлена");
        return "redirect:/services";
    }

    /**
     * Отображает форму для редактирования существующей услуги.
     *
     * @param id идентификатор редактируемой услуги; не должен быть {@code null}
     * @param model объект модели для передачи данных в представление
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return имя шаблона представления формы редактирования или команда перенаправления
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/editService/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        ServiceC service = serviceService.getServiceCById(id)
                .orElse(null);
        if (service == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Услуга не найдена");
            return "redirect:/services";
        }
        model.addAttribute("service", service);
        model.addAttribute("action", "edit");
        return "services/form";
    }

    /**
     * Обрабатывает обновление существующей услуги.
     * <p>
     * Валидирует введенные данные и обновляет услугу в базе данных.
     * При успешном обновлении перенаправляет на список услуг с сообщением об успехе.
     *
     * @param id идентификатор обновляемой услуги; не должен быть {@code null}
     * @param service объект услуги с обновленными данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code id} или {@code service} равны {@code null}
     * @throws RuntimeException если услуга с указанным ID не найдена
     *
     * @see PostMapping
     * @see PathVariable
     * @see Valid
     * @see ModelAttribute
     */
    @PostMapping("/updateService/{id}")
    public String updateService(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("service") ServiceC service,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            service.setIdService(id);
            return "services/form";
        }
        try {
            serviceService.updateServiceC(id,service);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Услуга " + service.getServiceName() + " успешно обновлена");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/services";
    }

    /**
     * Обрабатывает удаление услуги.
     * <p>
     * Удаляет услугу по указанному идентификатору.
     * При успешном удалении перенаправляет на список услуг с сообщением об успехе.
     *
     * @param id идентификатор удаляемой услуги; не должен быть {@code null}
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return команда перенаправления на список услуг
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/deleteService/{id}")
    public String deleteService(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            ServiceC service = serviceService.getServiceCById(id).orElse(null);
            if (service != null) {
                serviceService.deleteServiceC(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Услуга " + service.getServiceName() + " успешно удалена");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Услуга не найдена");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении услуги: " + e.getMessage());
        }
        return "redirect:/services";
    }
}
