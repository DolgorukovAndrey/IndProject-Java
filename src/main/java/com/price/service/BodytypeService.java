package com.price.service;

import com.price.entity.Bodytype;
import com.price.repository.BodytypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с типами кузовов автомобилей.
 * <p>
 * Предоставляет бизнес-логику для операций с сущностью {@link Bodytype},
 * включая создание, чтение, обновление и удаление типов кузовов.
 * Все операции выполняются в транзакционном контексте.
 *
 * <p><b>Основные возможности:</b>
 * <ul>
 *   <li>Получение всех типов кузовов</li>
 *   <li>Поиск типа кузова по идентификатору</li>
 *   <li>Сохранение нового типа кузова</li>
 *   <li>Обновление существующего типа кузова</li>
 *   <li>Удаление типа кузова</li>
 * </ul>
 *
 * @see Service
 * @see Transactional
 * @see Bodytype
 * @see BodytypeRepository
 */
@Service
@Transactional
public class BodytypeService {
    /**
     * Репозиторий для работы с типами кузовов.
     */
    private final BodytypeRepository bodytypeRepository;

    /**
     * Конструктор с инъекцией зависимости репозитория.
     *
     * @param bodytypeRepository репозиторий для работы с типами кузовов. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code bodytypeRepository} равен {@code null}
     */
    @Autowired
    public BodytypeService(BodytypeRepository bodytypeRepository) {
        this.bodytypeRepository = bodytypeRepository;
    }

    /**
     * Получает все типы кузовов автомобилей.
     *
     * @return список всех типов кузовов. Возвращает пустой список, если типы кузовов отсутствуют
     *
     * @see BodytypeRepository#findAll()
     */
    public List<Bodytype> getAllBodytype() {
        return bodytypeRepository.findAll();
    }

    /**
     * Находит тип кузова по его идентификатору.
     *
     * @param idBodytype идентификатор типа кузова. Не должен быть {@code null}
     * @return {@link Optional} с найденным типом кузова или пустой {@link Optional},
     *         если тип кузова с указанным ID не найден
     *
     * @throws IllegalArgumentException если {@code idBodytype} равен {@code null}
     *
     * @see BodytypeRepository#findById(Object)
     */
    public Optional<Bodytype> getBodytypeById(Long idBodytype) {
        return bodytypeRepository.findById(idBodytype);
    }

    /**
     * Сохраняет новый тип кузова.
     * <p>
     * Если передаваемый объект уже имеет идентификатор, метод выполнит обновление
     * существующей записи. В противном случае будет создана новая запись.
     *
     * @param bodytype объект типа кузова для сохранения; не должен быть {@code null}
     * @return сохраненный объект типа кузова
     *
     * @throws IllegalArgumentException если {@code bodytype} равен {@code null}
     *
     * @see BodytypeRepository#save(Object)
     */
    public Bodytype saveBodytype(Bodytype bodytype) {
        return bodytypeRepository.save(bodytype);
    }

    /**
     * Обновляет существующий тип кузова.
     * <p>
     * Находит тип кузова по идентификатору и обновляет его название.
     *
     * @param idBodytype идентификатор обновляемого типа кузова. Не должен быть {@code null}
     * @param bodytypeDetails объект с новыми данными для обновления. Не должен быть {@code null}
     * @return обновленный объект типа кузова
     *
     * @throws IllegalArgumentException если {@code idBodytype} или {@code bodytypeDetails} равны {@code null}
     * @throws RuntimeException если тип кузова с указанным ID не найден
     *
     * @see BodytypeRepository#findById(Object)
     * @see BodytypeRepository#save(Object)
     */    public Bodytype updateBodytype(Long idBodytype, Bodytype bodytypeDetails) {
        Bodytype bodytype = bodytypeRepository.findById(idBodytype)
                .orElseThrow(() -> new RuntimeException("Тип кузова с ID не найден: " + idBodytype));
        bodytype.setBodytypeName(bodytypeDetails.getBodytypeName());
        return bodytypeRepository.save(bodytype);
    }

    /**
     * Удаляет тип кузова по идентификатору.
     * <p>
     * Если тип кузова с указанным ID не существует, метод не выбрасывает исключение.
     *
     * @param id идентификатор типа кузова для удаления. Не должен быть {@code null}
     *
     * @throws IllegalArgumentException если {@code id} равен {@code null}
     *
     * @see BodytypeRepository#deleteById(Object)
     */
    public void deleteBodytype(Long id) {
        bodytypeRepository.deleteById(id);
    }
}
