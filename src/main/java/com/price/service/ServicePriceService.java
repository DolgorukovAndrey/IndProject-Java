package com.price.service;

import com.price.entity.ServicePrice;
import com.price.repository.ServicePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ценами на услуги.
 * <p>
 * Предоставляет бизнес-логику для операций с сущностью {@link ServicePrice},
 * включая создание, чтение, обновление и удаление записей о ценах услуг автомоек.
 * Все операции выполняются в транзакционном контексте.
 *
 * <p><b>Основные возможности:</b>
 * <ul>
 *   <li>Получение всех цен на услуги</li>
 *   <li>Поиск цены услуги по идентификатору</li>
 *   <li>Сохранение новой цены услуги</li>
 *   <li>Обновление существующей цены услуги</li>
 *   <li>Удаление цены услуги</li>
 * </ul>
 *
 * <p><b>Важно:</b> Цена услуги уникальна для комбинации услуга-автомойка-тип кузова-дата.
 * Попытка создать дублирующую запись может привести к нарушению ограничения уникальности.
 *
 * @see Service
 * @see Transactional
 * @see ServicePrice
 * @see ServicePriceRepository
 */
@Service
@Transactional
public class ServicePriceService {
    /**
     * Репозиторий для работы с ценами на услуги.
     */
    private final ServicePriceRepository servicePriceRepository;

    /**
     * Конструктор с инъекцией зависимости репозитория.
     *
     * @param servicePriceRepository репозиторий для работы с ценами на услуги. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code servicePriceRepository} равен {@code null}
     */
    @Autowired
    public ServicePriceService(ServicePriceRepository servicePriceRepository) {
        this.servicePriceRepository = servicePriceRepository;
    }

    /**
     * Получает все цены на услуги автомоек.
     *
     * @return список всех цен на услуги. Возвращает пустой список, если записи отсутствуют
     *
     * @see ServicePriceRepository#findAll()
     */
    public List<ServicePrice> getAllServicePrice() {
        return servicePriceRepository.findAll();
    }

    /**
     * Находит цену услуги по её идентификатору.
     *
     * @param idServicePrice идентификатор цены услуги. Не должен быть {@code null}
     * @return {@link Optional} с найденной ценой услуги или пустой {@link Optional},
     *         если цена услуги с указанным ID не найдена
     *
     * @throws IllegalArgumentException если {@code idServicePrice} равен {@code null}
     *
     * @see ServicePriceRepository#findById(Object)
     */
    public Optional<ServicePrice> getServicePriceById(Long idServicePrice) {
        return servicePriceRepository.findById(idServicePrice);
    }

    /**
     * Сохраняет новую цену услуги.
     * <p>
     * Если передаваемый объект уже имеет идентификатор, метод выполнит обновление
     * существующей записи. В противном случае будет создана новая запись.
     *
     * @param servicePrice объект цены услуги для сохранения. Не должен быть {@code null}
     * @return сохраненный объект цены услуги
     *
     * @throws IllegalArgumentException если {@code servicePrice} равен {@code null}
     * @throws org.springframework.dao.DataIntegrityViolationException при нарушении ограничений уникальности
     *
     * @see ServicePriceRepository#save(Object)
     */
    public ServicePrice saveServicePrice(ServicePrice servicePrice) {
        return servicePriceRepository.save(servicePrice);
    }

    /**
     * Обновляет существующую цену услуги.
     * <p>
     * Находит цену услуги по идентификатору и обновляет все её поля,
     * включая связанные сущности (услугу, автомойку, тип кузова).
     *
     * @param idServicePrice идентификатор обновляемой цены услуги; не должен быть {@code null}
     * @param servicePriceDetails объект с новыми данными для обновления; не должен быть {@code null}
     * @return обновленный объект цены услуги
     *
     * @throws IllegalArgumentException если {@code idServicePrice} или {@code servicePriceDetails} равны {@code null}
     * @throws RuntimeException если цена услуги с указанным ID не найдена
     * @throws org.springframework.dao.DataIntegrityViolationException при нарушении ограничений уникальности
     *
     * @see ServicePriceRepository#findById(Object)
     * @see ServicePriceRepository#save(Object)
     */
    public ServicePrice updateServicePrice(Long idServicePrice, ServicePrice servicePriceDetails) {
        ServicePrice servicePrice = servicePriceRepository.findById(idServicePrice)
                .orElseThrow(() -> new RuntimeException("Цена услуги с ID не найдена: " + idServicePrice));
        servicePrice.setService(servicePriceDetails.getService());
        servicePrice.setCarwash(servicePriceDetails.getCarwash());
        servicePrice.setBodytype(servicePriceDetails.getBodytype());
        servicePrice.setLeadTime(servicePriceDetails.getLeadTime());
        servicePrice.setPrice(servicePriceDetails.getPrice());
        servicePrice.setPriceDate(servicePriceDetails.getPriceDate());
        return servicePriceRepository.save(servicePrice);
    }

    /**
     * Удаляет цену услуги по идентификатору.
     * <p>
     * Если цена услуги с указанным ID не существует, метод не выбрасывает исключение.
     *
     * @param idServicePrice идентификатор цены услуги для удаления. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code idServicePrice} равен {@code null}
     *
     * @see ServicePriceRepository#deleteById(Object)
     */
    public void deleteServicePrice(Long idServicePrice) {
        servicePriceRepository.deleteById(idServicePrice);
    }
}