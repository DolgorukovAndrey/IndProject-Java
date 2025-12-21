package com.price.repository;

import com.price.entity.AllPrice;
import com.price.entity.AllPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link AllPrice}.
 * <p>
 * Предоставляет методы доступа к данным о ценах услуг автомоек, включая
 * базовые CRUD-операции, наследуемые от {@link JpaRepository},
 * и кастомные запросы для получения актуальных цен.
 *
 * <p><b>Наследуемые методы:</b>
 * <ul>
 *   <li>{@link JpaRepository#save(Object)} - сохранение или обновление записи</li>
 *   <li>{@link JpaRepository#findById(Object)} - поиск по составному ключу</li>
 *   <li>{@link JpaRepository#findAll()} - получение всех записей</li>
 *   <li>{@link JpaRepository#delete(Object)} - удаление записи</li>
 *   <li>{@link JpaRepository#count()} - подсчет количества записей</li>
 * </ul>
 *
 * @see JpaRepository
 * @see AllPrice
 * @see AllPriceId
 */
public interface AllPriceRepository extends JpaRepository<AllPrice, AllPriceId> {
    /**
     * Находит актуальные цены на услуги автомоек.
     * <p>
     * Выполняет нативный SQL-запрос, вызывающий хранимую функцию базы данных
     * {@code get_actual_from_allprice()}. Функция возвращает самые последние
     * (актуальные) цены для каждой комбинации услуги, автомойки и типа кузова.
     *
     * <p><b>Логика актуальности:</b>
     * Для каждой уникальной комбинации (автомойка × услуга × тип кузова)
     * выбирается запись с самой поздней датой ({@code price_date}).
     *
     * <p><b>Примечание:</b> Используется нативный запрос, так как логика выборки
     * актуальных цен реализована на уровне базы данных в хранимой функции.
     *
     * @return список актуальных цен услуг; возвращает пустой список, если нет данных
     *
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к базе данных
     *
     * @see Query
     * @see AllPrice
     */
    @Query(value = "SELECT * FROM get_actual_from_allprice()", nativeQuery = true)
    List<AllPrice> findActualPrices();
}
