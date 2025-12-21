package com.price.service;

import com.price.entity.AllPrice;
import com.price.entity.AllPriceId;
import com.price.repository.AllPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ценами услуг автомоек.
 * <p>
 * Предоставляет бизнес-логику для операций с сущностью {@link AllPrice},
 * включая получение, сохранение, обновление и удаление записей о ценах.
 * Использует как Spring Data JPA репозиторий, так и прямой доступ через
 * {@link JdbcTemplate} для сложных операций обновления.
 *
 * <p><b>Основные возможности:</b>
 * <ul>
 *   <li>Получение актуальных цен</li>
 *   <li>Поиск цен по составному ключу</li>
 *   <li>Сохранение новых цен</li>
 *   <li>Обновление существующих записей</li>
 *   <li>Удаление записей</li>
 * </ul>
 *
 * @see Service
 * @see Transactional
 * @see AllPrice
 * @see AllPriceId
 */
@Service
@Transactional
public class AllPriceService {
    /**
     * Репозиторий для работы с ценами.
     */
    private final AllPriceRepository allPriceRepository;

    /**
     * Шаблон для выполнения SQL-запросов.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Конструктор с инъекцией зависимости репозитория.
     *
     * @param allPriceRepository репозиторий для работы с ценами. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code allPriceRepository} равен {@code null}
     */
    @Autowired
    public AllPriceService(AllPriceRepository allPriceRepository) {
        this.allPriceRepository = allPriceRepository;
    }

    /**
     * Находит запись о цене по составному идентификатору.
     *
     * @param id составной идентификатор цены; не должен быть {@code null}
     * @return {@link Optional} с найденной записью о цене или пустой {@link Optional},
     *         если запись не найдена
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see AllPriceRepository#findById(Object)
     */
    public Optional<AllPrice> getAllPriceById(AllPriceId id) {
        return allPriceRepository.findById(id);
    }

    /**
     * Сохраняет или обновляет запись о цене.
     * <p>
     * Если запись с таким идентификатором уже существует, она будет обновлена.
     * В противном случае будет создана новая запись.
     *
     * @param allPrice объект цены для сохранения. Не должен быть {@code null}
     * @return сохраненный объект цены
     *
     * @throws IllegalArgumentException если {@code allPrice} равен {@code null}
     *
     * @see AllPriceRepository#save(Object)
     */
    public AllPrice saveAllPrice(AllPrice allPrice) {
        return allPriceRepository.save(allPrice);
    }

    /**
     * Обновляет существующую запись о цене.
     * <p>
     * Выполняет прямое обновление в базе данных через {@link JdbcTemplate},
     * что позволяет изменять значения составного ключа.
     *
     * <p><b>Важно:</b> Этот метод изменяет не только данные записи, но и
     * значения составного ключа при необходимости.
     *
     * @param oldId исходный идентификатор записи для обновления; не должен быть {@code null}
     * @param newId новый идентификатор для записи (может совпадать с oldId); не должен быть {@code null}
     * @param newData новые данные для записи; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если любой из параметров равен {@code null}
     * @throws RuntimeException если запись не была найдена и обновлена
     *
     * @see JdbcTemplate#update(String, Object...)
     */
    @Transactional
    public void updateAllPrice(AllPriceId oldId, AllPriceId newId, AllPrice newData) {
        String sql = "UPDATE AllPrice SET " +
                "carwash_name = ?, " +
                "carwash_address = ?, " +
                "service_name = ?, " +
                "service_info = ?, " +
                "bodytype_name = ?, " +
                "leadtime = ?, " +
                "price = ?, " +
                "price_date = ? " +
                "WHERE carwash_name = ? " +
                "AND carwash_address = ? " +
                "AND service_name = ? " +
                "AND bodytype_name = ? " +
                "AND price_date = ?";

        int updated = jdbcTemplate.update(sql,
                newData.getCarwashName(),
                newData.getCarwashAddress(),
                newData.getService(),
                newData.getServiceInfo(),
                newData.getBodytype(),
                newData.getLeadTime(),
                newData.getPrice(),
                newData.getPriceDate(),
                oldId.getCarwashName(),
                oldId.getCarwashAddress(),
                oldId.getService(),
                oldId.getBodytype(),
                oldId.getPriceDate()
        );
        if (updated == 0) {
            throw new RuntimeException("Запись не обновлена");
        }
    }

    /**
     * Удаляет запись о цене по составному идентификатору.
     *
     * @param id идентификатор записи для удаления; не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see AllPriceRepository#deleteById(Object)
     */
    public void deleteAllPrice(AllPriceId id) {
        allPriceRepository.deleteById(id);
    }

    /**
     * Получает все актуальные цены на услуги автомоек.
     * @return список актуальных цен. Возвращает пустой список, если нет данных
     *
     * @see AllPriceRepository#findActualPrices()
     */
    public List<AllPrice> getActualPrices() {
        return allPriceRepository.findActualPrices();
    }
}
