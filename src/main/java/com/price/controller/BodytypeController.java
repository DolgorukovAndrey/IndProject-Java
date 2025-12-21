package com.price.controller;

import com.price.entity.Bodytype;
import com.price.service.BodytypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Контроллер для управления типами кузовов автомобилей.
 * <p>
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для типов кузовов.
 * Предоставляет веб-интерфейс для создания, просмотра, обновления и удаления
 * записей о типах кузовов.
 *
 * <p><b>Маршруты:</b>
 * <table border="1">
 *   <tr><th>Метод</th><th>URL</th><th>Описание</th></tr>
 *   <tr><td>GET</td><td>/bodytypes</td><td>Список всех типов кузовов</td></tr>
 *   <tr><td>GET</td><td>/bodytypes/newBodytype</td><td>Форма создания нового типа кузова</td></tr>
 *   <tr><td>POST</td><td>/bodytypes</td><td>Создание нового типа кузова</td></tr>
 *   <tr><td>GET</td><td>/bodytypes/editBodytype/{id}</td><td>Форма редактирования типа кузова</td></tr>
 *   <tr><td>POST</td><td>/bodytypes/updateBodytype/{id}</td><td>Обновление типа кузова</td></tr>
 *   <tr><td>GET</td><td>/bodytypes/deleteBodytype/{id}</td><td>Удаление типа кузова</td></tr>
 * </table>
 *
 * @see Controller
 * @see RequestMapping
 * @see Bodytype
 * @see BodytypeService
 */
@Controller
@RequestMapping("/bodytypes")
public class BodytypeController {
    /**
     * Сервис для работы с типами кузовов.
     */
    private final BodytypeService bodytypeService;

    /**
     * Конструктор с инъекцией зависимости сервиса.
     *
     * @param bodytypeService сервис для работы с типами кузовов; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code bodytypeService} равен {@code null}
     */
    @Autowired
    public BodytypeController(BodytypeService bodytypeService) {
        this.bodytypeService = bodytypeService;
    }

    /**
     * Отображает список всех типов кузовов.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления для отображения списка типов кузовов
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping
    public String listBodytype(Model model) {
        List<Bodytype> bodytypes = bodytypeService.getAllBodytype();
        model.addAttribute("bodytypes", bodytypes);
        model.addAttribute("bodytypeCount", bodytypes.size());
        return "bodytypes/list";
    }

    /**
     * Отображает форму для создания нового типа кузова.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления формы создания типа кузова
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping("/newBodytype")
    public String showCreateForm(Model model) {
        model.addAttribute("bodytype", new Bodytype());
        model.addAttribute("action", "create");
        return "bodytypes/form";
    }

    /**
     * Обрабатывает создание нового типа кузова.
     * <p>
     * Валидирует введенные данные и сохраняет новый тип кузова в базе данных.
     * При успешном сохранении перенаправляет на список типов кузовов с сообщением об успехе.
     *
     * @param bodytype объект типа кузова с данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code bodytype} равен {@code null}
     *
     * @see Valid
     * @see ModelAttribute
     * @see PostMapping
     * @see RedirectAttributes
     */
    @PostMapping
    public String createBodytype(@Valid @ModelAttribute("bodytype") Bodytype bodytype,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "bodytypes/form";
        }
        bodytypeService.saveBodytype(bodytype);
        redirectAttributes.addFlashAttribute("successMessage",
                "Тип кузова " + bodytype.getBodytypeName() + " успешно добавлен");
        return "redirect:/bodytypes";
    }

    /**
     * Отображает форму для редактирования существующего типа кузова.
     *
     * @param id идентификатор редактируемого типа кузова; не должен быть {@code null}
     * @param model объект модели для передачи данных в представление
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return имя шаблона представления формы редактирования или команда перенаправления
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/editBodytype/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Bodytype bodytype = bodytypeService.getBodytypeById(id)
                .orElse(null);
        if (bodytype == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Тип кузова не найден");
            return "redirect:/bodytypes";
        }
        model.addAttribute("bodytype", bodytype);
        model.addAttribute("action", "edit");
        return "bodytypes/form";
    }

    /**
     * Обрабатывает обновление существующего типа кузова.
     * <p>
     * Валидирует введенные данные и обновляет тип кузова в базе данных.
     * При успешном обновлении перенаправляет на список типов кузовов с сообщением об успехе.
     *
     * @param id идентификатор обновляемого типа кузова; не должен быть {@code null}
     * @param bodytype объект типа кузова с обновленными данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code id} или {@code bodytype} равны {@code null}
     * @throws RuntimeException если тип кузова с указанным ID не найден
     *
     * @see PostMapping
     * @see PathVariable
     * @see Valid
     * @see ModelAttribute
     */
    @PostMapping("/updateBodytype/{id}")
    public String updateBodytype(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("bodytype") Bodytype bodytype,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            bodytype.setIdBodytype(id);
            return "bodytypes/form";
        }
        try {
            bodytypeService.updateBodytype(id,bodytype);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Тип кузова " + bodytype.getBodytypeName() + " успешно обновлен");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bodytypes";
    }

    /**
     * Обрабатывает удаление типа кузова.
     * <p>
     * Удаляет тип кузова по указанному идентификатору.
     * При успешном удалении перенаправляет на список типов кузовов с сообщением об успехе.
     *
     * @param id идентификатор удаляемого типа кузова; не должен быть {@code null}
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return команда перенаправления на список типов кузовов
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/deleteBodytype/{id}")
    public String deleteBodytype(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Bodytype bodytype = bodytypeService.getBodytypeById(id).orElse(null);
            if (bodytype != null) {
                bodytypeService.deleteBodytype(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Тип кузова " + bodytype.getBodytypeName() + " успешно удален");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Тип кузова не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении типа кузова: " + e.getMessage());
        }

        return "redirect:/bodytypes";
    }
}
