package com.price.repository;

import com.price.entity.Bodytype;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Bodytype}.
 * <p>
 * Предоставляет стандартные CRUD-операции для управления типами кузовов автомобилей.
 * Не требует реализации дополнительных методов, так как все базовые операции
 * наследуются от {@link JpaRepository}.
 *
 * <p><b>Доступные операции (наследуемые от JpaRepository):</b>
 * <ul>
 *   <li><b>Создание/Обновление:</b> {@link JpaRepository#save(Object) save(Bodytype)}</li>
 *   <li><b>Поиск по ID:</b> {@link JpaRepository#findById(Object) findById(Long)}</li>
 *   <li><b>Получение всех записей:</b> {@link JpaRepository#findAll() findAll()}</li>
 *   <li><b>Удаление:</b> {@link JpaRepository#delete(Object) delete(Bodytype)}</li>
 *   <li><b>Удаление по ID:</b> {@link JpaRepository#deleteById(Object) deleteById(Long)}</li>
 *   <li><b>Проверка существования:</b> {@link JpaRepository#existsById(Object) existsById(Long)}</li>
 *   <li><b>Подсчет записей:</b> {@link JpaRepository#count() count()}</li>
 * </ul>
 * @see JpaRepository
 * @see Bodytype
 */
public interface BodytypeRepository extends JpaRepository<Bodytype, Long> {
}
