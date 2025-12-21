package com.price.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов к корневому URL приложения.
 * <p>
 * Этот контроллер предоставляет простую логику перенаправления с домашней страницы
 * на основную страницу с ценами. Является точкой входа в веб-интерфейс приложения.
 *
 * <p><b>Маршруты:</b>
 * <ul>
 *   <li>{@code GET /} - перенаправляет на страницу со всеми ценами</li>
 * </ul>
 *
 * @see Controller
 * @see GetMapping
 */
@Controller
public class HomeController {
    /**
     * Обрабатывает запрос к корневому URL приложения.
     * <p>
     * Выполняет перенаправление (redirect) на страницу со всеми ценами услуг.
     *
     * @return строка с командой перенаправления на {@code /allPrices}
     *
     * @see GetMapping
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/allPrices";
    }
}
