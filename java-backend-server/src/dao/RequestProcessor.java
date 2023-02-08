package dao;

public interface RequestProcessor<T> {
    T operation(String data);
}
