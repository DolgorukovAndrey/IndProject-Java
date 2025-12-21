package com.price.service;

import com.price.entity.ServiceC;
import com.price.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с услугами автомоек.
 * <p>
 * Предоставляет бизнес-логику для операций с сущностью {@link ServiceC},
 * включая создание, чтение, обновление и удаление записей об услугах автомоек.
 * Все операции выполняются в транзакционном контексте.
 *
 * <p><b>Примечание:</b> Класс сущности назван {@code ServiceC} во избежание
 * конфликта имен с зарезервированным словом "service" в некоторых контекстах.
 *
 * <p><b>Основные возможности:</b>
 * <ul>
 *   <li>Получение всех услуг</li>
 *   <li>Поиск услуги по идентификатору</li>
 *   <li>Сохранение новой услуги</li>
 *   <li>Обновление существующей услуги</li>
 *   <li>Удаление услуги</li>
 * </ul>
 *
 * @see Service
 * @see Transactional
 * @see ServiceC
 * @see ServiceRepository
 */
@Service
@Transactional
public class ServiceService {
    /**
     * Репозиторий для работы с услугами.
     */
    private final ServiceRepository serviceRepository;

    /**
     * Конструктор с инъекцией зависимости репозитория.
     *
     * @param serviceRepository репозиторий для работы с услугами. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code serviceRepository} равен {@code null}
     */
    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Получает все услуги автомоек.
     *
     * @return список всех услуг. Возвращает пустой список, если услуги отсутствуют
     *
     * @see ServiceRepository#findAll()
     */
    public List<ServiceC> getAllService() {
        return serviceRepository.findAll();
    }

    /**
     * Находит услугу по её идентификатору.
     *
     * @param idService идентификатор услуги. Не должен быть {@code null}
     * @return {@link Optional} с найденной услугой или пустой {@link Optional},
     *         если услуга с указанным ID не найдена
     *
     * @throws IllegalArgumentException если {@code idService} равен {@code null}
     *
     * @see ServiceRepository#findById(Object)
     */
    public Optional<ServiceC> getServiceCById(Long idService) {
        return serviceRepository.findById(idService);
    }

    /**
     * Сохраняет новую услугу.
     * <p>
     * Если передаваемый объект уже имеет идентификатор, метод выполнит обновление
     * существующей записи. В противном случае будет создана новая запись.
     *
     * @param serviceC объект услуги для сохранения. Не должен быть {@code null}
     * @return сохраненный объект услуги
     *
     * @throws IllegalArgumentException если {@code serviceC} равен {@code null}
     *
     * @see ServiceRepository#save(Object)
     */
    public ServiceC saveServiceC(ServiceC serviceC) {
        return serviceRepository.save(serviceC);
    }

    /**
     * Обновляет существующую услугу.
     * <p>
     * Находит услугу по идентификатору и обновляет её название и описание.
     *
     * @param idService идентификатор обновляемой услуги. Не должен быть {@code null}
     * @param serviceCDetails объект с новыми данными для обновления. Не должен быть {@code null}
     * @return обновленный объект услуги
     *
     * @throws IllegalArgumentException если {@code idService} или {@code serviceCDetails} равны {@code null}
     * @throws RuntimeException если услуга с указанным ID не найдена
     *
     * @see ServiceRepository#findById(Object)
     * @see ServiceRepository#save(Object)
     */

    public ServiceC updateServiceC(Long idService, ServiceC serviceCDetails) {
        ServiceC serviceC = serviceRepository.findById(idService)
                .orElseThrow(() -> new RuntimeException("Услуга с ID не найдена: " + idService));
        serviceC.setServiceName(serviceCDetails.getServiceName());
        serviceC.setServiceInfo(serviceCDetails.getServiceInfo());
        return serviceRepository.save(serviceC);
    }

    /**
     * Удаляет услугу по идентификатору.
     * <p>
     * Если услуга с указанным ID не существует, метод не выбрасывает исключение.
     *
     * @param idService идентификатор услуги для удаления. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code idService} равен {@code null}
     *
     * @see ServiceRepository#deleteById(Object)
     */
    public void deleteServiceC(Long idService) {
        serviceRepository.deleteById(idService);
    }
}

