package com.price.controller;

import com.price.entity.Carwash;
import com.price.service.CarwashService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Контроллер для управления автомойками.
 * <p>
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для автомоек.
 * Предоставляет веб-интерфейс для создания, просмотра, обновления и удаления
 * записей об автомойках.
 *
 * <p><b>Маршруты:</b>
 * <table border="1">
 *   <tr><th>Метод</th><th>URL</th><th>Описание</th></tr>
 *   <tr><td>GET</td><td>/carwashs</td><td>Список всех автомоек</td></tr>
 *   <tr><td>GET</td><td>/carwashs/newCarwash</td><td>Форма создания новой автомойки</td></tr>
 *   <tr><td>POST</td><td>/carwashs</td><td>Создание новой автомойки</td></tr>
 *   <tr><td>GET</td><td>/carwashs/editCarwash/{id}</td><td>Форма редактирования автомойки</td></tr>
 *   <tr><td>POST</td><td>/carwashs/updateCarwash/{id}</td><td>Обновление автомойки</td></tr>
 *   <tr><td>GET</td><td>/carwashs/deleteCarwash/{id}</td><td>Удаление автомойки</td></tr>
 * </table>
 *
 * @see Controller
 * @see RequestMapping
 * @see Carwash
 * @see CarwashService
 */
@Controller
@RequestMapping("/carwashs")
public class CarwashController {
    /**
     * Сервис для работы с автомойками.
     */
    private final CarwashService carwashService;

    /**
     * Конструктор с инъекцией зависимости сервиса.
     *
     * @param carwashService сервис для работы с автомойками; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code carwashService} равен {@code null}
     */
    @Autowired
    public CarwashController(CarwashService carwashService) {
        this.carwashService = carwashService;
    }

    /**
     * Отображает список всех автомоек.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления для отображения списка автомоек
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping
    public String listCarwash(Model model) {
        List<Carwash> carwashs = carwashService.getAllCarwash();
        model.addAttribute("carwashs", carwashs);
        model.addAttribute("carwashCount", carwashs.size());
        return "carwashs/list";
    }

    /**
     * Отображает форму для создания новой автомойки.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления формы создания автомойки
     *
     * @see Model
     * @see GetMapping
     */
    @GetMapping("/newCarwash")
    public String showCreateForm(Model model) {
        model.addAttribute("carwash", new Carwash());
        model.addAttribute("action", "create");
        return "carwashs/form";
    }

    /**
     * Обрабатывает создание новой автомойки.
     * <p>
     * Валидирует введенные данные и сохраняет новую автомойку в базе данных.
     * При успешном сохранении перенаправляет на список автомоек с сообщением об успехе.
     *
     * @param carwash объект автомойки с данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code carwash} равен {@code null}
     *
     * @see Valid
     * @see ModelAttribute
     * @see PostMapping
     * @see RedirectAttributes
     */
    @PostMapping
    public String createCarwash(@Valid @ModelAttribute("carwash") Carwash carwash,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "carwashs/form";
        }
        carwashService.saveCarwash(carwash);
        redirectAttributes.addFlashAttribute("successMessage",
                "Автомойка " + carwash.getCarwashName() + " успешно добавлена");
        return "redirect:/carwashs";
    }

    /**
     * Отображает форму для редактирования существующей автомойки.
     *
     * @param id идентификатор редактируемой автомойки; не должен быть {@code null}
     * @param model объект модели для передачи данных в представление
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return имя шаблона представления формы редактирования или команда перенаправления
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/editCarwash/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Carwash carwash = carwashService.getCarwashById(id)
                .orElse(null);
        if (carwash == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Автомойка не найдена");
            return "redirect:/carwashs";
        }
        model.addAttribute("carwash", carwash);
        model.addAttribute("action", "edit");
        return "carwashs/form";
    }

    /**
     * Обрабатывает обновление существующей автомойки.
     * <p>
     * <b>Внимание:</b> В аннотации {@code @ModelAttribute} используется некорректное имя
     * "bodytype" вместо "carwash", что может вызвать проблемы с биндингом данных.
     *
     * <p>Валидирует введенные данные и обновляет автомойку в базе данных.
     * При успешном обновлении перенаправляет на список автомоек с сообщением об успехе.
     *
     * @param id идентификатор обновляемой автомойки; не должен быть {@code null}
     * @param carwash объект автомойки с обновленными данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code id} или {@code carwash} равны {@code null}
     * @throws RuntimeException если автомойка с указанным ID не найдена
     *
     * @see PostMapping
     * @see PathVariable
     * @see Valid
     * @see ModelAttribute
     */
    @PostMapping("/updateCarwash/{id}")
    public String updateCarwash(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("carwash") Carwash carwash,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            carwash.setIdCarwash(id);
            return "carwashs/form";
        }
        try {
            carwashService.updateCarwash(id,carwash);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Автомойка " + carwash.getCarwashName() + " успешно обновлена");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/carwashs";
    }

    /**
     * Обрабатывает удаление автомойки.
     * <p>
     * Удаляет автомойку по указанному идентификатору.
     * При успешном удалении перенаправляет на список автомоек с сообщением об успехе.
     *
     * @param id идентификатор удаляемой автомойки; не должен быть {@code null}
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return команда перенаправления на список автомоек
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     */
    @GetMapping("/deleteCarwash/{id}")
    public String deleteCarwash(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Carwash carwash = carwashService.getCarwashById(id).orElse(null);
            if (carwash != null) {
                carwashService.deleteCarwash(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Автомойка " + carwash.getCarwashName() + " успешно удалена");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Автомойка не найдена");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении автомойки: " + e.getMessage());
        }
        return "redirect:/carwashs";
    }
}
