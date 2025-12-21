package com.price.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Составной первичный ключ для сущности {@link AllPrice}.
 * <p>
 * Этот класс представляет собой уникальный идентификатор записи о цене услуги автомойки.
 * Ключ состоит из пяти полей, которые в совокупности обеспечивают уникальность записи.
 *
 * <p><b>Структура ключа:</b>
 * <ol>
 *   <li>Название автомойки</li>
 *   <li>Адрес автомойки</li>
 *   <li>Название услуги</li>
 *   <li>Дата установки цены</li>
 *   <li>Тип кузова автомобиля</li>
 * </ol>
 *
 * <p><b>Важно:</b> Класс реализует интерфейс {@link Serializable} для корректной работы
 * в распределенных системах и кэшировании.
 *
 * @see AllPrice
 * @see Embeddable
 * @see Serializable
 */
@Embeddable
public class AllPriceId implements Serializable {
    /**
     * Название автомойки.
     */
    @Column(name = "carwash_name")
    private String carwashName;

    /**
     * Адрес автомойки.
     */
    @Column(name = "carwash_address")
    private String carwashAddress;

    /**
     * Название услуги, предоставляемой мойкой.
     */
    @Column(name = "service_name")
    private String service;

    /**
     * Дата установки цены.
     */
    @Column(name = "price_date")
    private LocalDate priceDate;

    /**
     * Тип кузова автомобиля, для которого предназначена услуга.
     */
    @Column(name = "bodytype_name")
    private String bodytype;

    /**
     * Конструктор по умолчанию.
     */
    public AllPriceId() {}

    /**
     * Создает новый составной идентификатор с указанными значениями, все параметры идентификатора не должны быть {@code null} или пустыми.
     *
     * @param carwashName название автомойки
     * @param carwashAddress адрес автомойки
     * @param service название услуги
     * @param priceDate дата установки цены
     * @param bodytype тип кузова
     *
     * @throws IllegalArgumentException если любой из обязательных параметров равен {@code null}
     */
    public AllPriceId(String carwashName, String carwashAddress,
                      String service, LocalDate priceDate, String bodytype) {
        this.carwashName = carwashName;
        this.carwashAddress = carwashAddress;
        this.service = service;
        this.priceDate = priceDate;
        this.bodytype = bodytype;
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
     * Возвращает адрес автомойки.
     *
     * @return адрес автомойки
     */
    public String getCarwashAddress() {
        return carwashAddress;
    }

    /**
     * Возвращает название услуги.
     *
     * @return название услуги
     */
    public String getService() {
        return service;
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
     * Устанавливает название автомойки.
     *
     * @param carwashName новое название автомойки. Не должно быть {@code null} или пустым
     */
    public void setCarwashName(String carwashName) {
        this.carwashName = carwashName;
    }

    /**
     * Устанавливает адрес автомойки.
     *
     * @param carwashAddress новый адрес автомойки. Не должно быть {@code null} или пустым
     */
    public void setCarwashAddress(String carwashAddress) {
        this.carwashAddress = carwashAddress;
    }

    /**
     * Устанавливает название услуги.
     *
     * @param service новое название услуги. Не должно быть {@code null} или пустым
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Устанавливает дату установки цены.
     *
     * @param priceDate новая дата установки цены. Не должно быть {@code null}
     */
    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    /**
     * Устанавливает тип кузова автомобиля.
     *
     * @param bodytype новый тип кузова. Не может быть {@code null} или пустым
     */
    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    /**
     * Возвращает тип кузова автомобиля.
     *
     * @return тип кузова
     */
    public String getBodytype(){
        return bodytype;
    }

    /**
     * Сравнивает этот объект с другим объектом на равенство.
     * <p>
     * Два объекта {@code AllPriceId} считаются равными, если равны все их поля.
     *
     * @param object объект для сравнения
     * @return {@code true} если объекты равны, {@code false} в противном случае
     *
     * @see Objects#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AllPriceId that = (AllPriceId) object;
        return Objects.equals(carwashName, that.carwashName) &&
                Objects.equals(carwashAddress, that.carwashAddress) &&
                Objects.equals(service, that.service) &&
                Objects.equals(priceDate, that.priceDate) &&
                Objects.equals(bodytype,that.bodytype);
    }

    /**
     * Возвращает хэш-код объекта.
     * <p>
     * Хэш-код вычисляется на основе всех полей объекта для обеспечения
     * консистентности с методом {@link #equals(Object)}.
     *
     * @return хэш-код объекта
     *
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(carwashName, carwashAddress, service, priceDate,bodytype);
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат строки: {@code "carwashName,carwashAddress,service,bodytype,priceDate"}
     * где поля разделены запятыми. Это представление используется методом
     * {@link #fromString(String)} для обратного преобразования.
     *
     * @return строковое представление объекта в формате CSV
     *
     * @see #fromString(String)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return carwashName + "," + carwashAddress + "," + service + "," +
                bodytype + "," + priceDate.toString();
    }

    /**
     * Создает объект {@code AllPriceId} из строкового представления.
     * <p>
     * Метод парсит строку в формате, возвращаемом методом {@link #toString()}.
     * Поддерживает URL-encoded строки (автоматически декодирует).
     *
     * <p><b>Формат строки:</b>
     * {@code "название_мойки,адрес_мойки,услуга,тип_кузова,дата"}
     *
     * <p><b>Пример:</b>
     * <pre>{@code
     * // Простая строка
     * String str = "Мойка Центральная,ул. Ленина, 10,Полная мойка,Седан,2024-01-15";
     * AllPriceId id = AllPriceId.fromString(str);
     *
     * // URL-encoded строка
     * String encoded = "Мойка%20Центральная,ул.%20Ленина%2C%2010,Полная%20мойка,Седан,2024-01-15";
     * AllPriceId id2 = AllPriceId.fromString(encoded);
     * }</pre>
     *
     * @param str строковое представление объекта в CSV формате
     * @return новый объект {@code AllPriceId}
     *
     * @throws IllegalArgumentException если строка имеет неверный формат
     * @throws RuntimeException если возникает ошибка парсинга или декодирования
     *
     * @see #toString()
     * @see java.net.URLDecoder
     */
    public static AllPriceId fromString(String str) {
        try {
            String decoded = str;
            if (str.contains("%")) {
                decoded = java.net.URLDecoder.decode(str, "UTF-8");
            }
            String[] parts = decoded.split(",");
            return new AllPriceId(
                    parts[0],
                    parts[1],
                    parts[2],
                    LocalDate.parse(parts[4]),
                    parts[3]
            );
        } catch (Exception e) {
            throw new RuntimeException("Не могу распарсить ID: '" + str + "'", e);
        }
    }
}