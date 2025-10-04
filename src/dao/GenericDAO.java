package com.mycompany.irr00_group_project.dao;

import com.mycompany.irr00_group_project.data.FileDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Abstract generic Data Access Object (DAO) class that provides
 * base methods for entity persistence operations using a FileDb instance.
 *
 * @param <T> the entity type this DAO handles
 * 
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public abstract class GenericDAO<T> {

    protected FileDb fileDb;
    private Class<T> entityClass;

    /**
    * Constructs a GenericDAO for the given entity class and
    * initializes the in-memory database instance.
    *
    * @param entityClass the class type of the entity
    */
    public GenericDAO(Class<T> entityClass) {
        this.fileDb = FileDb.getInstance();
        this.entityClass = entityClass;
    }

    public void save(T entity) {

    }

    public T findById(long id) {
        return null;
    }

    public void update(T entity) {
    }

    public void delete(T entity) {

    }

    public List<T> findAll() {
        return null;
    }
}
