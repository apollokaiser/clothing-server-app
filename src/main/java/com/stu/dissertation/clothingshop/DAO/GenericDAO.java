package com.stu.dissertation.clothingshop.DAO;

import java.util.List;
import java.util.Optional;
/**
 *
 * @param <E> the type of entity
 * @param <T> the type of id
 */
public interface GenericDAO<E, T> {
    default Optional<E> findById(T id) {
        System.out.println("Find by id default");
        return Optional.empty();
    }
   default E save(E entity){
        System.out.println("Save entity default");
        return entity;
   }
    default E update(E entity){
        System.out.println("Update entity default");
        return entity;
    }
    default void delete(List<T> ids){
      System.out.println("Delete entity default");
    }
}
