package com.price.service;


import com.price.entity.Carwash;
import com.price.repository.CarwashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с автомойками.
 * <p>
 * Предоставляет бизнес-логику для операций с сущностью {@link Carwash},
 * включая создание, чтение, обновление и удаление записей об автомойках.
 * Все операции выполняются в транзакционном контексте.
 *
 * <p><b>Основные возможности:</b>
 * <ul>
 *   <li>Получение всех автомоек</li>
 *   <li>Поиск автомойки по идентификатору</li>
 *   <li>Сохранение новой автомойки</li>
 *   <li>Обновление существующей автомойки</li>
 *   <li>Удаление автомойки</li>
 * </ul>
 *
 * @see Service
 * @see Transactional
 * @see Carwash
 * @see CarwashRepository
 */
@Service
@Transactional
public class CarwashService {
    /**
     * Репозиторий для работы с автомойками.
     */
    private final CarwashRepository carwashRepository;

    /**
     * Конструктор с инъекцией зависимости репозитория.
     *
     * @param carwashRepository репозиторий для работы с автомойками. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code carwashRepository} равен {@code null}
     */
    @Autowired
    public CarwashService(CarwashRepository carwashRepository) {
        this.carwashRepository = carwashRepository;
    }

    /**
     * Получает все автомойки.
     *
     * @return список всех автомоек. Возвращает пустой список, если автомойки отсутствуют
     *
     * @see CarwashRepository#findAll()
     */
    public List<Carwash> getAllCarwash() {
        return carwashRepository.findAll();
    }

    /**
     * Находит автомойку по её идентификатору.
     *
     * @param idCarwash идентификатор автомойки. Не должен быть {@code null}
     * @return {@link Optional} с найденной автомойкой или пустой {@link Optional},
     *         если автомойка с указанным ID не найдена
     *
     * @throws IllegalArgumentException если {@code idCarwash} равен {@code null}
     *
     * @see CarwashRepository#findById(Object)
     */
    public Optional<Carwash> getCarwashById(Long idCarwash) {
        return carwashRepository.findById(idCarwash);
    }

    /**
     * Сохраняет новую автомойку.
     * <p>
     * Если передаваемый объект уже имеет идентификатор, метод выполнит обновление
     * существующей записи. В противном случае будет создана новая запись.
     *
     * @param carwash объект автомойки для сохранения. Не должен быть {@code null}
     * @return сохраненный объект автомойки
     *
     * @throws IllegalArgumentException если {@code carwash} равен {@code null}
     *
     * @see CarwashRepository#save(Object)
     */
    public Carwash saveCarwash(Carwash carwash) {
        return carwashRepository.save(carwash);
    }

    /**
     * Обновляет существующую автомойку.
     * <p>
     * Находит автомойку по идентификатору и обновляет её название и адрес.
     *
     * @param idCarwash идентификатор обновляемой автомойки. Не должен быть {@code null}
     * @param carwashDetails объект с новыми данными для обновления. Не должен быть {@code null}
     * @return обновленный объект автомойки
     *
     * @throws IllegalArgumentException если {@code idCarwash} или {@code carwashDetails} равны {@code null}
     * @throws RuntimeException если автомойка с указанным ID не найдена
     *
     * @see CarwashRepository#findById(Object)
     * @see CarwashRepository#save(Object)
     */
    public Carwash updateCarwash(Long idCarwash, Carwash carwashDetails) {
        Carwash carwash = carwashRepository.findById(idCarwash)
                .orElseThrow(() -> new RuntimeException("Автомойка с ID не найдена: " + idCarwash));
        carwash.setCarwashName(carwashDetails.getCarwashName());
        carwash.setCarwashAddress(carwashDetails.getCarwashAddress());
        return carwashRepository.save(carwash);
    }

    /**
     * Удаляет автомойку по идентификатору.
     * <p>
     * Если автомойка с указанным ID не существует, метод не выбрасывает исключение.
     *
     * @param idCarwash идентификатор автомойки для удаления. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code idCarwash} равен {@code null}
     *
     * @see CarwashRepository#deleteById(Object)
     */
    public void deleteCarwash(Long idCarwash) {
        carwashRepository.deleteById(idCarwash);
    }
}

