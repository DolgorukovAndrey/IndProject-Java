package com.price.repository;

import com.price.entity.ServiceC;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link ServiceC}.
 * <p>
 * Предоставляет стандартные CRUD-операции для управления услугами автомоек.
 * Все базовые операции наследуются от {@link JpaRepository}.
 *
 * <p><b>Примечание:</b> Класс сущности назван {@code ServiceC} во избежание
 * конфликта имен с зарезервированным словом "service" в некоторых контекстах.
 *
 * <p><b>Доступные операции (наследуемые от JpaRepository):</b>
 * <ul>
 *   <li><b>Создание/Обновление:</b> {@link JpaRepository#save(Object) save(ServiceC)}</li>
 *   <li><b>Поиск по ID:</b> {@link JpaRepository#findById(Object) findById(Long)}</li>
 *   <li><b>Получение всех записей:</b> {@link JpaRepository#findAll() findAll()}</li>
 *   <li><b>Удаление:</b> {@link JpaRepository#delete(Object) delete(ServiceC)}</li>
 *   <li><b>Удаление по ID:</b> {@link JpaRepository#deleteById(Object) deleteById(Long)}</li>
 *   <li><b>Проверка существования:</b> {@link JpaRepository#existsById(Object) existsById(Long)}</li>
 *   <li><b>Подсчет записей:</b> {@link JpaRepository#count() count()}</li>
 * </ul>
 *
 * @see JpaRepository
 * @see ServiceC
 */
public interface ServiceRepository extends JpaRepository<ServiceC, Long> {
}
