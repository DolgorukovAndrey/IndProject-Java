package com.price.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Сущность, представляющая автомойку.
 * <p>
 * Этот класс отображается на таблицу {@code carwashes} в базе данных и содержит
 * информацию об автомойках: уникальный идентификатор, название и адрес.
 * @see Entity
 * @see Table
 */
@Entity
@Table(name = "carwashes")
public class Carwash {
    /**
     * Уникальный идентификатор автомойки.
     * <p>
     * Генерируется автоматически базой данных при вставке новой записи.
     * Используется стратегия идентификации {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carwash")
    private Long idCarwash;

    /**
     * Название автомойки.
     * <p>
     * Должно быть уникальным в сочетании с адресом. Ограничено 300 символами.
     */
    @NotBlank(message = "Название автомойки не может быть пустым")
    @Size(min = 1, max = 200, message = "Название должно быть от 1 до 300 символов")
    @Column(name = "carwash_name", nullable = false, length = 300)
    private String carwashName;

    /**
     * Адрес автомойки.
     * <p>
     * Должен быть уникальным в сочетании с названием. Ограничен 300 символами.
     * Содержит адрес местоположения автомойки.
     */
    @NotBlank(message = "Адрес автомойки не может быть пустым")
    @Size(min = 1, max = 200, message = "Адрес должен быть от 1 до 300 символов")
    @Column(name = "carwash_address", nullable = false, length = 300)
    private String carwashAddress;

    /**
     * Конструктор по умолчанию.
     */
    public Carwash() {
    }

    /**
     * Создает новую автомойку с указанными параметрами.
     *
     * @param carwashName название автомойки. Не должно быть {@code null} или пустым
     * @param carwashAddress адрес автомойки. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если любой из параметров равен {@code null} или пустой
     */
    public Carwash(String carwashName, String carwashAddress) {
        this.carwashName = carwashName;
        this.carwashAddress = carwashAddress;
    }

    /**
     * Возвращает уникальный идентификатор автомойки.
     *
     * @return идентификатор автомойки
     */
    public Long getIdCarwash() {
        return idCarwash;
    }

    /**
     * Устанавливает уникальный идентификатор автомойки.
     * <p>
     * Обычно вызывается фреймворком JPA, не рекомендуется для ручного использования.
     *
     * @param idCarwash новый идентификатор
     */
    public void setIdCarwash(Long idCarwash) {
        this.idCarwash = idCarwash;
    }

    /**
     * Возвращает название автомойки.
     *
     * @return название автомойки
     */
    public String getCarwashName() {
        return carwashName;
    }

    /**
     * Устанавливает название автомойки.
     *
     * @param carwashName новое название автомойки. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code carwashName} равен {@code null} или пустой
     */
    public void setCarwashName(String carwashName) {
        this.carwashName = carwashName;
    }

    /**
     * Возвращает адрес автомойки.
     *
     * @return адрес автомойки
     */
    public String getCarwashAddress() {
        return carwashAddress;
    }

    /**
     * Устанавливает адрес автомойки.
     *
     * @param carwashAddress новый адрес автомойки. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code carwashAddress} равен {@code null} или пустой
     */
    public void setCarwashAddress(String carwashAddress) {
        this.carwashAddress = carwashAddress;
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат: {@code Carwash{idCarwash = ..., carwashName = ..., carwashAddress = ...}}
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Carwash{" +
                "idCarwash = " + idCarwash +
                ", carwashName = " + carwashName +
                ", carwashAddress = " + carwashAddress + "}";
    }
}
