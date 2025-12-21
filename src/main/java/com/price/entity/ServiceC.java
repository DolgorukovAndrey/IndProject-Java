package com.price.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Сущность, представляющая услугу автомойки.
 * <p>
 * Этот класс отображается на таблицу {@code services} в базе данных и содержит
 * информацию об услугах, предоставляемых автомойками. Каждая услуга имеет
 * название и дополнительное описание.
 *
 * <p><b>Примечание:</b> Класс назван {@code ServiceC} во избежание конфликта
 * с зарезервированным словом "service" в некоторых контекстах.
 *
 * @see Entity
 * @see Table
 */
@Entity
@Table(name = "services")
public class ServiceC {
    /**
     * Уникальный идентификатор услуги.
     * <p>
     * Генерируется автоматически базой данных при вставке новой записи.
     * Используется стратегия идентификации {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Long idService;

    /**
     * Название услуги.
     * <p>
     * Должно быть уникальным и непустым. Ограничено 300 символами.
     */
    @NotBlank(message = "Название услуги не может быть пустым")
    @Size(min = 1, max = 200, message = "Название должно быть от 1 до 300 символов")
    @Column(name = "service_name", nullable = false, length = 300)
    private String serviceName;

    /**
     * Дополнительная информация об услуге.
     * <p>
     * Содержит подробное описание услуги, включая состав работ,
     * используемые материалы, особенности и ограничения.
     * Не имеет ограничения по длине в базе данных.
     */
    @NotBlank(message = "Информация о услуге не может быть пустым")
    @Column(name = "service_info")
    private String serviceInfo;

    /**
     * Конструктор по умолчанию.
     */
    public ServiceC(){
    }

    /**
     * Создает новую услугу с указанными параметрами.
     *
     * @param serviceName название услуги. Не должно быть {@code null} или пустым
     * @param serviceInfo дополнительная информация об услуге. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если любой из параметров равен {@code null} или пустой
     */
    public ServiceC(String serviceName, String serviceInfo){
        this.serviceName = serviceName;
        this.serviceInfo = serviceInfo;
    }

    /**
     * Возвращает уникальный идентификатор услуги.
     *
     * @return идентификатор услуги
     */
    public Long getIdService() {
        return idService;
    }

    /**
     * Устанавливает уникальный идентификатор услуги.
     * <p>
     * Обычно вызывается фреймворком JPA, не рекомендуется для ручного использования.
     *
     * @param idService новый идентификатор
     */
    public void setIdService(Long idService) {
        this.idService = idService;
    }

    /**
     * Возвращает название услуги.
     *
     * @return название услуги
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Устанавливает название услуги.
     *
     * @param service новое название услуги. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code service} равен {@code null} или пустой
     */
    public void setServiceName(String service) {
        this.serviceName = service;
    }

    /**
     * Возвращает дополнительную информацию об услуге.
     *
     * @return информация об услуге
     */
    public String getServiceInfo() {
        return serviceInfo;
    }

    /**
     * Устанавливает дополнительную информацию об услуге.
     *
     * @param serviceInfo новая информация об услуге. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code serviceInfo} равен {@code null} или пустой
     */
    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат: {@code ServiceC{idService = ..., serviceName = ..., serviceInfo = ...}}
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "ServiceC{" +
                "idService = " + idService +
                ", serviceName = " + serviceName +
                ", serviceInfo  = " + serviceInfo + "}";
    }
}
