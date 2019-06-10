package models;

import java.util.List;

public interface IEntityModel<T> {
	
	public List<T> fetchMultiple();
	
	public T fetch(int id);
	
	public boolean persist(T entity);
	
	public boolean delete(int id);
}
