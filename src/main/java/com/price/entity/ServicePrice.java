package com.price.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Сущность, представляющая цену на услугу автомойки.
 * <p>
 * Этот класс отображается на таблицу {@code servicesprice} в базе данных и содержит
 * информацию о стоимости услуги для конкретной автомойки, типа кузова и даты.
 * Обеспечивает уникальность комбинации услуги, автомойки, типа кузова и даты через
 * ограничение уникальности базы данных.
 *
 * <p><b>Бизнес-правила:</b>
 * <ul>
 *   <li>Для одной и той же услуги, автомойки и типа кузова не может быть двух цен на одну дату</li>
 *   <li>Время выполнения услуги должно быть от 1 до 600 минут</li>
 *   <li>Стоимость услуги должна быть от 0 до 1,000,000 рублей</li>
 * </ul>
 *
 * @see Entity
 * @see Table
 * @see UniqueConstraint
 */
@Entity
@Table(name = "servicesprice", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_service_carwash_bodytype_date",
                columnNames = {"id_service", "id_carwash", "id_bodytype", "price_date"}
        )
})
public class ServicePrice {
    /**
     * Уникальный идентификатор записи о цене услуги.
     * <p>
     * Генерируется автоматически базой данных при вставке новой записи.
     * Используется стратегия идентификации {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serviceprice")
    private Long idServicePrice;

    /**
     * Услуга, для которой установлена цена.
     * <p>
     * Связь многие-к-одному с сущностью {@link ServiceC}.
     * Не может быть {@code null}.
     */
    @ManyToOne
    @JoinColumn(name = "id_service", referencedColumnName = "id_service")
    private ServiceC service;

    /**
     * Автомойка, предоставляющая услугу.
     * <p>
     * Связь многие-к-одному с сущностью {@link Carwash}.
     * Не может быть {@code null}.
     */
    @ManyToOne
    @JoinColumn(name = "id_carwash", referencedColumnName = "id_carwash")
    private Carwash carwash;

    /**
     * Тип кузова, для которого предназначена услуга.
     * <p>
     * Связь многие-к-одному с сущностью {@link Bodytype}.
     * Не может быть {@code null}.
     */
    @ManyToOne
    @JoinColumn(name = "id_bodytype", referencedColumnName = "id_bodytype")
    private Bodytype bodytype;

    /**
     * Время выполнения услуги в минутах.
     * <p>
     * Должно быть в диапазоне от 1 до 600 минут.
     */
    @NotNull(message = "Время работы не может быть пустым")
    @Min(value = 1, message = "Время работы не может быть меньше 1 минуты")
    @Max(value = 600, message = "Время не может превышать 600 минут")
    @Column(name = "leadtime")
    private Integer leadTime;

    /**
     * Стоимость услуги в рублях.
     * <p>
     * Должна быть в диапазоне от 0 до 1,000,000 рублей.
     */
    @NotNull(message = "Стоимость услуги не может быть пустой")
    @Min(value = 0, message = "Стоимость должна быть больше 0")
    @Max(value = 1000000, message = "Стоимость не может превышать 1000000")
    @Column(name = "price")
    private Integer price;

    /**
     * Дата установки цены.
     */
    @NotNull(message = "Поле даты не может быть пустым")
    @Column(name = "price_date")
    private LocalDate priceDate;

    /**
     * Конструктор по умолчанию.
     */
    public ServicePrice(){
    }

    /**
     * Создает новую запись о цене услуги с указанными параметрами.
     *
     * @param service услуга; не должно быть {@code null}
     * @param carwash автомойка; не должно быть {@code null}
     * @param bodytype тип кузова; не должно быть {@code null}
     * @param leadTime время выполнения в минутах; должно быть от 1 до 600
     * @param price стоимость в рублях; должно быть от 0 до 1,000,000
     * @param priceDate дата установки цены; не должно быть {@code null}
     *
     * @throws IllegalArgumentException если любой из параметров не соответствует ограничениям
     */
    public ServicePrice(ServiceC service, Carwash carwash, Bodytype bodytype, int leadTime,
                        int price, LocalDate priceDate){
        this.service = service;
        this.carwash = carwash;
        this.bodytype = bodytype;
        this.leadTime = leadTime;
        this.price = price;
        this.priceDate = priceDate;
    }

    /**
     * Возвращает уникальный идентификатор записи о цене.
     *
     * @return идентификатор записи
     */
    public Long getIdServicePrice() {
        return idServicePrice;
    }

    /**
     * Устанавливает уникальный идентификатор записи о цене.
     * <p>
     * Обычно вызывается фреймворком JPA, не рекомендуется для ручного использования.
     *
     * @param idServicePrice новый идентификатор
     */
    public void setIdServicePrice(Long idServicePrice) {
        this.idServicePrice = idServicePrice;
    }

    /**
     * Возвращает услугу, для которой установлена цена.
     *
     * @return услуга
     */
    public ServiceC getService() {
        return service;
    }

    /**
     * Устанавливает услугу, для которой устанавливается цена.
     *
     * @param service услуга. Не должно быть {@code null}
     *
     * @throws IllegalArgumentException если {@code service} равен {@code null}
     */
    public void setService(ServiceC service) {
        this.service = service;
    }

    /**
     * Возвращает автомойку, предоставляющую услугу.
     *
     * @return автомойка
     */
    public Carwash getCarwash() {
        return carwash;
    }

    /**
     * Устанавливает автомойку, предоставляющую услугу.
     *
     * @param carwash автомойка. Не должно быть {@code null}
     *
     * @throws IllegalArgumentException если {@code carwash} равен {@code null}
     */
    public void setCarwash(Carwash carwash) {
        this.carwash = carwash;
    }

    /**
     * Возвращает тип кузова, для которого предназначена услуга.
     *
     * @return тип кузова
     */
    public Bodytype getBodytype() {
        return bodytype;
    }

    /**
     * Устанавливает тип кузова, для которого предназначена услуга.
     *
     * @param bodytype тип кузова. Не должно быть {@code null}
     *
     * @throws IllegalArgumentException если {@code bodytype} равен {@code null}
     */
    public void setBodytype(Bodytype bodytype) {
        this.bodytype = bodytype;
    }

    /**
     * Возвращает стоимость услуги.
     *
     * @return стоимость в рублях
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Устанавливает стоимость услуги.
     *
     * @param price новая стоимость в рублях; должно быть от 0 до 1,000,000
     *
     * @throws IllegalArgumentException если {@code price} не соответствует ограничениям
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Возвращает время выполнения услуги.
     *
     * @return время выполнения в минутах
     */
    public Integer getLeadTime() {
        return leadTime;
    }

    /**
     * Устанавливает время выполнения услуги.
     *
     * @param leadTime новое время выполнения в минутах; должно быть от 1 до 600
     *
     * @throws IllegalArgumentException если {@code leadTime} не соответствует ограничениям
     */
    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    /**
     * Возвращает дату установки цены.
     *
     * @return дата установки цены
     */
    public LocalDate getPriceDate() {
        return priceDate;
    }

    /**
     * Устанавливает дату установки цены.
     *
     * @param priceDate новая дата установки цены. Не должно быть {@code null}
     *
     * @throws IllegalArgumentException если {@code priceDate} равен {@code null}
     */
    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат: {@code ServicePrice{idServiceprice = ..., serviceId = ...,
     * carwashId = ..., bodytypeId = ..., price = ..., leadTime = ..., priceDate = ...}}
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "ServicePrice{" +
                "idServiceprice = " + idServicePrice +
                ", serviceId = " + (service != null ? service.getIdService() : null) +
                ", carwashId = " + (carwash != null ? carwash.getIdCarwash() : null) +
                ", bodytypeId = " + (bodytype != null ? bodytype.getIdBodytype() : null) +
                ", price = " + price +
                ", leadTime = " + leadTime +
                ", priceDate = " + priceDate + '}';
    }
}
