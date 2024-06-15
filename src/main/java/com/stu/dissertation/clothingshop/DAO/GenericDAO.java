package com.stu.dissertation.clothingshop.DAO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 *
 * @param <E> the type of entity
 * @param <T> the type of id's entity
 */
public interface GenericDAO<E, T> {
    Optional<E> findById(T id);
    E save(E entity);
    E update(E entity);
    void delete(List<T> ids);
}
