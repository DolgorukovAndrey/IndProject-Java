package com.price.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Сущность, представляющая тип кузова автомобиля.
 * <p>
 * Этот класс отображается на таблицу {@code bodytypes} в базе данных и хранит
 * информацию о различных типах кузовов автомобилей (например, седан, внедорожник, универсал).
 * Используется для классификации услуг автомойки в зависимости от типа кузова.
 *
 *
 * @see Entity
 * @see Table
 */
@Entity
@Table(name = "bodytypes")
public class Bodytype {
    /**
     * Уникальный идентификатор типа кузова.
     * <p>
     * Генерируется автоматически базой данных при вставке новой записи.
     * Используется стратегия идентификации {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodytype")
    private Long idBodytype;

    /**
     * Название типа кузова.
     * <p>
     * Должно быть уникальным и непустым. Ограничено 300 символами.
     */
    @NotBlank(message = "Тип кузова не может быть пустым")
    @Size(min = 1, max = 200, message = "Тип кузова должен быть от 1 до 300 символов")
    @Column(name = "bodytype_name", nullable = false, length = 300)
    private String bodytypeName;

    /**
     * Конструктор по умолчанию.
     */
    public Bodytype(){
    }

    /**
     * Создает новый тип кузова с указанным названием.
     *
     * @param bodytype название типа кузова. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code bodytype} равен {@code null} или пустой
     */
    public Bodytype(String bodytype){
        this.bodytypeName = bodytype;
    }

    /**
     * Возвращает уникальный идентификатор типа кузова.
     *
     * @return идентификатор типа кузова
     */
    public Long getIdBodytype() {
        return idBodytype;
    }

    /**
     * Устанавливает уникальный идентификатор типа кузова.
     * <p>
     * Обычно вызывается фреймворком JPA, не рекомендуется для ручного использования.
     *
     * @param idBodytype новый идентификатор
     */
    public void setIdBodytype(Long idBodytype) {
        this.idBodytype = idBodytype;
    }

    /**
     * Возвращает название типа кузова.
     *
     * @return название типа кузова
     */
    public String getBodytypeName() {
        return bodytypeName;
    }

    /**
     * Устанавливает название типа кузова.
     *
     * @param bodytype новое название типа кузова. Не должно быть {@code null} или пустым
     *
     * @throws IllegalArgumentException если {@code bodytype} равен {@code null} или пустой
     */
    public void setBodytypeName(String bodytype) {
        this.bodytypeName = bodytype;
    }

    /**
     * Возвращает строковое представление объекта.
     * <p>
     * Формат: {@code Bodytype{idBodytype = ..., bodytype = ...}}
     *
     * @return строковое представление объекта
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Bodytype{" +
                "idBodytype = " + idBodytype +
                ", bodytype = " + bodytypeName +  '}';
    }
}
