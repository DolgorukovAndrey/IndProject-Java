package com.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа для Spring Boot приложения Price Manager.
 * Запускает контекст Spring и инициализирует все компоненты приложения.
 */
@SpringBootApplication
public class PriceManagerApplication {
    /**
     * Главный метод, запускающий Spring Boot приложение.
     *
     * @param args аргументы командной строки для конфигурации приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(PriceManagerApplication.class, args);
    }
}
