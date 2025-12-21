package com.price.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Сущность, представляющая ценовую информацию для услуг автомойки.
 * <p>
 * Этот класс отображается на представление {@code allprice} в базе данных и содержит
 * информацию о ценах, сроках выполнения услуг и дополнительных деталях.
 * Использует составной первичный ключ {@link AllPriceId}.
 *
 * <p><b>Пример использования:</b>
 *
 * @see AllPriceId
 * @see Entity
 */
@Entity
@Table(name = "allprice")
public class AllPrice {
    /**
     * Составной первичный ключ сущности.
     * <p>
     * Включает идентификационные поля: название мойки, адрес, услугу,
     * тип кузова и дату установки цены.
     */
    @EmbeddedId
    private AllPriceId id;

    /**
     * Дополнительная информация об услуге.
     * <p>
     * Может содержать описание, условия предоставления услуги
     * или другие примечания.
     */
    @Column(name = "service_info")
    private String serviceInfo;

    /**
     * Время выполнения услуги в минутах.
     * <p>
     * Указывает, сколько времени занимает выполнение услуги
     * от начала до конца.
     */
    @Column(name = "leadtime")
    private Integer leadTime;

    /**
     * Стоимость услуги в рублях.
     * <p>
     * Цена должна быть положительным целым числом.
     */
    @Column(name = "price")
    private Integer price;

    /**
     * Конструктор по умолчанию.
     */
    public AllPrice(){
    }

    /**
     * Создает новый объект AllPrice с указанными параметрами.
     *
     * @param id составной идентификатор цены
     * @param serviceInfo дополнительная информация об услуге
     * @param leadTime время выполнения услуги в минутах
     * @param price стоимость услуги в рублях
     *
     * @throws IllegalArgumentException если какой-либо из обязательных параметров равен {@code null}
     *
     */
    public AllPrice(AllPriceId id, String serviceInfo,
                    Integer leadTime, Integer price) {
        this.id = id;
        this.serviceInfo = serviceInfo;
        this.leadTime = leadTime;
        this.price = price;
    }

    /**
     * Возвращает составной идентификатор цены.
     *
     * @return объект {@link AllPriceId}
     */
    public AllPriceId getId() {
        return id;
    }


    /**
     * Устанавливает составной идентификатор цены.
     *
     * @param id новый идентификатор
     */
    public void setId(AllPriceId id) {
        this.id = id;
    }

    /**
     * Устанавливает название автомойки.
     * <p>
     * Если внутренний идентификатор {@code id} равен {@code null},
     * создает новый экземпляр {@link AllPriceId}.
     *
     * @param carwashName название автомойки. Не должно быть {@code null} или пустым
     */
    public void setCarwashName(String carwashName) {
        if (this.id == null) {
            this.id = new AllPriceId();
        }
        this.id.setCarwashName(carwashName);
    }

    /**
     * Устанавливает адрес автомойки.
     * <p>
     * Если внутренний идентификатор {@code id} равен {@code null},
     * создает новый экземпляр {@link AllPriceId}.
     *
     * @param carwashAddress адрес автомойки. Не должно быть {@code null} или пустым
     */
    public void setCarwashAddress(String carwashAddress) {
        if (this.id == null) {
            this.id = new AllPriceId();
        }
        this.id.setCarwashAddress(carwashAddress);
    }

    /**
     * Устанавливает название услуги.
     * <p>
     * Если внутренний идентификатор {@code id} равен {@code null},
     * создает новый экземпляр {@link AllPriceId}.
     *
     * @param service название услуги. Не должно быть {@code null} или пустым
     */
    public void setService(String service) {
        if (this.id == null) {
            this.id = new AllPriceId();
        }
        this.id.setService(service);
    }

    /**
     * Устанавливает дату установки цены.
     * <p>
     * Если внутренний идентификатор {@code id} равен {@code null},
     * создает новый экземпляр {@link AllPriceId}.
     *
     * @param priceDate дата установки цены; Не должно быть {@code null} или пустым
     */
    public void setPriceDate(LocalDate priceDate) {
        if (this.id == null) {
            this.id = new AllPriceId();
        }
        this.id.setPriceDate(priceDate);
    }

    /**
     * Возвращает дополнительную информацию об услуге.
     *
     * @return дополнительная информация
     */
    public String getServiceInfo() {
        return serviceInfo;
    }

    /**
     * Устанавливает дополнительную информацию об услуге.
     *
     * @param serviceInfo новая дополнительная информация
     */
    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    /**
     * Возвращает название автомойки из составного идентификатора.
     *
     * @return название автомойки или {@code null}, если идентификатор не установлен
     */
    public String getCarwashName() {
        return id != null ? id.getCarwashName() : null;
    }

    /**
     * Возвращает адрес автомойки из составного идентификатора.
     *
     * @return адрес автомойки или {@code null}, если идентификатор не установлен
     */
    public String getCarwashAddress() {
        return id != null ? id.getCarwashAddress() : null;
    }

    /**
     * Возвращает название услуги из составного идентификатора.
     *
     * @return название услуги или {@code null}, если идентификатор не установлен
     */
    public String getService() {
        return id != null ? id.getService() : null;
    }

    /**
     * Возвращает дату установки цены из составного идентификатора.
     *
     * @return дата установки цены или {@code null}, если идентификатор не установлен
     */
    public LocalDate getPriceDate() {
        return id != null ? id.getPriceDate() : null;
    }

    /**
     * Возвращает тип кузова автомобиля из составного идентификатора.
     *
     * @return тип кузова или {@code null}, если идентификатор не установлен
     */
    public String getBodytype() {
        return id != null ? id.getBodytype() : null;
    }

    /**
     * Устанавливает тип кузова автомобиля.
     * <p>
     * Если внутренний идентификатор {@code id} равен {@code null},
     * создает новый экземпляр {@link AllPriceId}.
     *
     * @param bodytype тип кузова автомобиля
     */
    public void setBodytype(String bodytype) {
        if (this.id == null) {
            this.id = new AllPriceId();
        }
        this.id.setBodytype(bodytype);
    }

    /**
     * Возвращает время выполнения услуги.
     *
     * @return Время выполнения в минутах
     */
    public Integer getLeadTime() {
        return leadTime;
    }

    /**
     * Устанавливает время выполнения услуги в минутах.
     *
     * @param leadTime новое время выполнения в минутах. Должно быть положительным числом
     *
     * @throws IllegalArgumentException если {@code leadTime} меньше или равно 0
     */
    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    /**
     * Возвращает стоимость услуги.
     *
     * @return Стоимость услуги в рублях
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Устанавливает стоимость услуги.
     *
     * @param price новая стоимость в рублях. Должна быть положительным числом
     *
     * @throws IllegalArgumentException если {@code price} меньше или равно 0
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат: {@code AllPrice{carwashName=..., carwashAddress=..., service=..., ...}}
     *
     * @return строковое представление объекта
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "AllPrice{" +
                "carwashName=" + getCarwashName() +
                ", carwashAddress='" + getCarwashAddress() + '\'' +
                ", service=" + getService() +
                ", serviceInfo='" + serviceInfo + '\'' +
                ", bodytype=" + getBodytype() +
                ", leadTime=" + leadTime +
                ", price=" + price +
                ", priceDate=" + getPriceDate() + "}";
    }
}
