package dao;


import java.util.List;

public interface DAO<T> {
    int create(Object entity);
    T read(int id);
    List<T> readAll();
    int update(int id, T entity);
    int delete(int id);
}
