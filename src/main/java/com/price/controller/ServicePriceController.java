package com.price.controller;

import com.price.entity.ServicePrice;
import com.price.service.ServicePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Контроллер для отображения информации о ценах на услуги.
 * <p>
 * Обрабатывает HTTP-запросы для просмотра списка цен на услуги и детальной информации
 * о конкретной цене. Предоставляет только операции чтения (read-only), без возможности
 * создания, обновления или удаления записей через этот контроллер.
 *
 * <p><b>Примечание:</b> В сообщениях об ошибках используется некорректное слово "альбом"
 * вместо "цена услуги" - это опечатка, которую стоит исправить.
 *
 * <p><b>Маршруты:</b>
 * <table border="1">
 *   <tr><th>Метод</th><th>URL</th><th>Описание</th></tr>
 *   <tr><td>GET</td><td>/servicePrices</td><td>Список всех цен на услуги</td></tr>
 *   <tr><td>GET</td><td>/servicePrices/view/{id}</td><td>Детальная информация о конкретной цене услуги</td></tr>
 * </table>
 *
 * @see Controller
 * @see RequestMapping
 * @see ServicePrice
 * @see ServicePriceService
 */
@Controller
@RequestMapping("/servicePrices")
public class ServicePriceController {
    /**
     * Сервис для работы с ценами на услуги.
     */
    private final ServicePriceService servicePriceService;

    /**
     * Конструктор с инъекцией зависимости сервиса.
     *
     * @param servicePriceService сервис для работы с ценами на услуги; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code servicePriceService} равен {@code null}
     */
    @Autowired
    public ServicePriceController(ServicePriceService servicePriceService) {
        this.servicePriceService = servicePriceService;
    }

    /**
     * Отображает список всех цен на услуги.
     * <p>
     * Получает все записи о ценах на услуги из базы данных и передает их в представление
     * вместе с общим количеством записей.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления для отображения списка цен на услуги
     *
     * @see Model
     * @see GetMapping
     * @see ServicePriceService#getAllServicePrice()
     */
    @GetMapping
    public String listServicePrice(Model model) {
        List<ServicePrice> servicePrices = servicePriceService.getAllServicePrice();
        model.addAttribute("servicePrices", servicePrices);
        model.addAttribute("servicePriceCount", servicePrices.size());
        return "servicePrices/list";
    }

    /**
     * Отображает детальную информацию о конкретной цене услуги.
     * <p>
     * Находит цену услуги по идентификатору и отображает ее детальную информацию.
     * Если цена с указанным ID не найдена, перенаправляет на список с сообщением об ошибке.
     *
     * @param id идентификатор цены услуги; не должен быть {@code null}
     * @param model объект модели для передачи данных в представление
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return имя шаблона представления для детального просмотра или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see RedirectAttributes
     * @see ServicePriceService#getServicePriceById(Long)
     */
    @GetMapping("/view/{id}")
    public String viewServicePrice(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        ServicePrice servicePrice = servicePriceService.getServicePriceById(id).orElse(null);
        if (servicePrice == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Информация не найдена");
            return "redirect:/servicePrices";
        }
        model.addAttribute("servicePrice", servicePrice);
        return "servicePrices/view";
    }
}