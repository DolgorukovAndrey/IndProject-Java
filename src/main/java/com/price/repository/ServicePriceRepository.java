package com.price.repository;

import com.price.entity.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link ServicePrice}.
 * <p>
 * Предоставляет стандартные CRUD-операции для управления ценами на услуги автомоек.
 * Все базовые операции наследуются от {@link JpaRepository}.
 *
 * <p><b>Доступные операции (наследуемые от JpaRepository):</b>
 * <ul>
 *   <li><b>Создание/Обновление:</b> {@link JpaRepository#save(Object) save(ServicePrice)}</li>
 *   <li><b>Поиск по ID:</b> {@link JpaRepository#findById(Object) findById(Long)}</li>
 *   <li><b>Получение всех записей:</b> {@link JpaRepository#findAll() findAll()}</li>
 *   <li><b>Удаление:</b> {@link JpaRepository#delete(Object) delete(ServicePrice)}</li>
 *   <li><b>Удаление по ID:</b> {@link JpaRepository#deleteById(Object) deleteById(Long)}</li>
 *   <li><b>Проверка существования:</b> {@link JpaRepository#existsById(Object) existsById(Long)}</li>
 *   <li><b>Подсчет записей:</b> {@link JpaRepository#count() count()}</li>
 * </ul>
 *
 * @see JpaRepository
 * @see ServicePrice
 */
public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {
}
