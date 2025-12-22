package com.price.controller;

import com.price.entity.AllPrice;
import com.price.entity.AllPriceId;
import com.price.repository.BodytypeRepository;
import com.price.repository.CarwashRepository;
import com.price.repository.ServiceRepository;
import com.price.service.AllPriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления всеми ценами на услуги автомоек.
 * <p>
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для агрегированных данных о ценах.
 * Работает с составными идентификаторами в строковом формате и предоставляет веб-интерфейс
 * для создания, просмотра, обновления и удаления записей о ценах.
 *
 * <p><b>Особенности:</b>
 * <ul>
 *   <li>Использует составные идентификаторы ({@link AllPriceId}) в формате строки</li>
 *   <li>Отображает только актуальные цены (последние для каждой комбинации)</li>
 *   <li>Поддерживает сложное обновление с изменением составного ключа</li>
 *   <li>Обрабатывает строковые представления идентификаторов через {@link AllPriceId#fromString(String)}</li>
 * </ul>
 *
 * <p><b>Маршруты:</b>
 * <table border="1">
 *   <tr><th>Метод</th><th>URL</th><th>Описание</th></tr>
 *   <tr><td>GET</td><td>/allPrices</td><td>Список актуальных цен</td></tr>
 *   <tr><td>GET</td><td>/allPrices/newAllPrice</td><td>Форма создания новой записи о цене</td></tr>
 *   <tr><td>POST</td><td>/allPrices</td><td>Создание новой записи о цене</td></tr>
 *   <tr><td>GET</td><td>/allPrices/editAllPrice/{id}</td><td>Форма редактирования записи о цене</td></tr>
 *   <tr><td>POST</td><td>/allPrices/updateAllPrice/{id}</td><td>Обновление записи о цене</td></tr>
 *   <tr><td>GET</td><td>/allPrices/deleteAllPrice/{id}</td><td>Удаление записи о цене</td></tr>
 * </table>
 *
 * <p><b>Формат идентификатора:</b>
 * Строка в формате: {@code "carwashName,carwashAddress,service,bodytype,priceDate"}
 * где {@code priceDate} в формате ISO-8601 (YYYY-MM-DD).
 *
 * @see Controller
 * @see RequestMapping
 * @see AllPrice
 * @see AllPriceId
 * @see AllPriceService
 */
@Controller
@RequestMapping("/allPrices")
public class AllPriceController {
    /**
     * Сервис для работы с агрегированными данными о ценах.
     */
    private final AllPriceService allPriceService;

    /**
     * Репозиторий для работы с сущностью {@link com.price.entity.Carwash}.
     * <p>
     * Предоставляет доступ к данным об автомойках, включая
     * их названия и адреса. Используется для заполнения
     * выпадающих списков в формах создания и редактирования цен.
     *
     * @see com.price.entity.Carwash
     */
    private final CarwashRepository carwashRepository;

    /**
     * Репозиторий для работы с сущностью {@link com.price.entity.ServiceC}.
     * <p>
     * Обеспечивает доступ к данным об услугах автомоек,
     * включая названия услуг и их описания. Используется
     * для предварительного заполнения информации об услугах
     * в формах ввода данных.
     *
     * @see com.price.entity.ServiceC
     */
    private final ServiceRepository serviceRepository;

    /**
     * Репозиторий для работы с сущностью {@link com.price.entity.Bodytype}.
     * <p>
     * Предоставляет методы доступа к данным о типах кузова
     * автомобилей. Используется для ограничения выбора типов
     * кузова только существующими значениями из базы данных.
     *
     * @see com.price.entity.Bodytype
     */
    private final BodytypeRepository bodytypeRepository;

    /**
     * Конструктор с инъекцией зависимостей.
     * <p>
     * Инициализирует все необходимые компоненты для работы контроллера:
     * сервис для работы с ценами и репозитории для доступа к справочным данным.
     *
     * @param allPriceService сервис для работы с агрегированными данными о ценах; не должен быть {@code null}
     * @param carwashRepository репозиторий для доступа к данным об автомойках; не должен быть {@code null}
     * @param serviceRepository репозиторий для доступа к данным об услугах; не должен быть {@code null}
     * @param bodytypeRepository репозиторий для доступа к данным о типах кузова; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если любой из параметров равен {@code null}
     *
     * @see AllPriceService
     * @see CarwashRepository
     * @see ServiceRepository
     * @see BodytypeRepository
     */
    @Autowired
    public AllPriceController(AllPriceService allPriceService,
                              CarwashRepository carwashRepository,
                              ServiceRepository serviceRepository,
                              BodytypeRepository bodytypeRepository) {
        this.allPriceService = allPriceService;
        this.carwashRepository = carwashRepository;
        this.serviceRepository = serviceRepository;
        this.bodytypeRepository = bodytypeRepository;
    }

    /**
     * Отображает список актуальных цен на услуги автомоек.
     * <p>
     * Получает только самые последние (актуальные) цены для каждой комбинации
     * услуги, автомойки и типа кузова.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления для отображения списка актуальных цен
     *
     * @see Model
     * @see GetMapping
     * @see AllPriceService#getActualPrices()
     */
    @GetMapping
    public String listAllPrice(Model model) {
        List<AllPrice> allPrices = allPriceService.getActualPrices();
        model.addAttribute("allPrices", allPrices);
        model.addAttribute("allPriceCount", allPrices.size());
        return "allPrices/list";
    }

    /**
     * Отображает форму для создания новой записи о цене.
     * <p>
     * Загружает все справочные данные (автомойки, услуги, типы кузова) из базы данных
     * и передает их в представление для заполнения выпадающих списков.
     * Также инициализирует пустой объект {@link AllPrice} с составным идентификатором.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона представления формы создания записи о цене
     *
     * @see Model
     * @see GetMapping
     * @see CarwashRepository#findAll()
     * @see ServiceRepository#findAll()
     * @see BodytypeRepository#findAll()
     */
    @GetMapping("/newAllPrice")
    public String showCreateForm(Model model) {
        model.addAttribute("carwashes", carwashRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("bodytypes", bodytypeRepository.findAll());
        AllPrice allPrice = new AllPrice();
        allPrice.setId(new AllPriceId());
        model.addAttribute("allPrice", allPrice);
        model.addAttribute("action", "create");
        return "allPrices/form";
    }

    /**
     * Обрабатывает создание новой записи о цене.
     * <p>
     * Валидирует введенные данные и сохраняет новую запись о цене в базе данных.
     * При успешном сохранении перенаправляет на список цен с сообщением об успехе.
     *
     * @param allPrice объект записи о цене с данными из формы; проходит валидацию
     * @param result результат валидации данных формы
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @param model объект модели для передачи данных в представление при ошибках валидации
     * @return имя шаблона представления или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code allPrice} равен {@code null}
     *
     * @see Valid
     * @see ModelAttribute
     * @see PostMapping
     * @see RedirectAttributes
     */
    @PostMapping
    public String createAllPrice(@Valid @ModelAttribute("allPrice") AllPrice allPrice,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "allPrices/form";
        }
        allPriceService.saveAllPrice(allPrice);
        redirectAttributes.addFlashAttribute("successMessage",
                "Данные " + " успешно добавлены");
        return "redirect:/allPrices";
    }

    /**
     * Отображает форму для редактирования существующей записи о цене.
     * <p>
     * Декодирует строковый идентификатор, находит соответствующую запись,
     * загружает все справочные данные и отображает форму для редактирования
     * с предзаполненными значениями.
     *
     * @param idStr строковое представление составного идентификатора; не должно быть {@code null}
     * @param model объект модели для передачи данных в представление
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return имя шаблона представления формы редактирования или команда перенаправления
     *
     * @throws IllegalArgumentException если {@code idStr} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see AllPriceId#fromString(String)
     * @see AllPriceService#getAllPriceById(AllPriceId)
     * @see CarwashRepository#findAll()
     * @see ServiceRepository#findAll()
     * @see BodytypeRepository#findAll()
     * @see RedirectAttributes
     */
    @GetMapping("/editAllPrice/{id}")
    public String showEditForm(@PathVariable("id") String idStr,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            AllPriceId id = AllPriceId.fromString(idStr);
            Optional<AllPrice> allPriceOpt = allPriceService.getAllPriceById(id);

            if (allPriceOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Информация не найдена");
                return "redirect:/allPrices";
            }
            model.addAttribute("carwashs", carwashRepository.findAll());
            model.addAttribute("services", serviceRepository.findAll());
            model.addAttribute("bodytypes", bodytypeRepository.findAll());
            model.addAttribute("allPrice", allPriceOpt.get());
            model.addAttribute("action", "edit");
            return "allPrices/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неверный формат ID");
            return "redirect:/allPrices";
        }
    }

    /**
     * Обрабатывает обновление существующей записи о цене.
     * <p>
     * Декодирует старый идентификатор, создает новые данные и обновляет запись.
     * Поддерживает изменение всех полей, включая поля составного ключа.
     *
     * @param idStr строковое представление старого составного идентификатора; не должно быть {@code null}
     * @param carwashName новое название автомойки; не должно быть {@code null} или пустым
     * @param carwashAddress новый адрес автомойки; не должно быть {@code null} или пустым
     * @param service новое название услуги; не должно быть {@code null} или пустым
     * @param bodytype новый тип кузова; может быть {@code null}
     * @param priceDate новая дата цены; не должно быть {@code null}
     * @param serviceInfo новая дополнительная информация об услуге; может быть {@code null}
     * @param leadTime новое время выполнения в минутах; должно быть положительным
     * @param price новая стоимость в рублях; должно быть положительным
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return команда перенаправления на список цен
     *
     * @throws IllegalArgumentException если любой из обязательных параметров равен {@code null}
     * @throws RuntimeException если произошла ошибка при обновлении
     *
     * @see PostMapping
     * @see PathVariable
     * @see RequestParam
     * @see DateTimeFormat
     * @see AllPriceService#updateAllPrice(AllPriceId, AllPriceId, AllPrice)
     */
    @PostMapping("/updateAllPrice/{id}")
    public String updateAllPrice(@PathVariable("id") String idStr,
                                 @RequestParam String carwashName,
                                 @RequestParam String carwashAddress,
                                 @RequestParam String service,
                                 @RequestParam String bodytype,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate priceDate,
                                 @RequestParam String serviceInfo,
                                 @RequestParam Integer leadTime,
                                 @RequestParam Integer price,
                                 RedirectAttributes redirectAttributes) {
        try {
            AllPriceId oldId = AllPriceId.fromString(idStr);
            AllPrice newData = new AllPrice();
            newData.setCarwashName(carwashName);
            newData.setCarwashAddress(carwashAddress);
            newData.setService(service);
            newData.setBodytype(bodytype);
            newData.setPriceDate(priceDate);
            newData.setServiceInfo(serviceInfo);
            newData.setLeadTime(leadTime);
            newData.setPrice(price);
            AllPriceId newId = new AllPriceId(carwashName, carwashAddress, service, priceDate, bodytype);
            allPriceService.updateAllPrice(oldId, newId, newData);
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены!");
            return "redirect:/allPrices";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return "redirect:/allPrices";
        }
    }

    /**
     * Обрабатывает удаление записи о цене.
     * <p>
     * Декодирует строковый идентификатор и удаляет соответствующую запись из базы данных.
     *
     * @param idStr строковое представление составного идентификатора; не должно быть {@code null}
     * @param redirectAttributes атрибуты для передачи данных при перенаправлении
     * @return команда перенаправления на список цен
     *
     * @throws IllegalArgumentException если {@code idStr} равен {@code null}
     *
     * @see GetMapping
     * @see PathVariable
     * @see AllPriceId#fromString(String)
     * @see AllPriceService#deleteAllPrice(AllPriceId)
     * @see RedirectAttributes
     */
    @GetMapping("/deleteAllPrice/{id}")
    public String deleteAllPrice(@PathVariable("id") String idStr,
                                 RedirectAttributes redirectAttributes) {
        try {
            AllPriceId id = AllPriceId.fromString(idStr);
            Optional<AllPrice> allPriceOpt = allPriceService.getAllPriceById(id);
            if (allPriceOpt.isPresent()) {
                allPriceService.deleteAllPrice(id);
                redirectAttributes.addFlashAttribute("successMessage", "Информация успешно удалена");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Информация не найдена");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении информации: " + e.getMessage());
        }
        return "redirect:/allPrices";
    }
}
