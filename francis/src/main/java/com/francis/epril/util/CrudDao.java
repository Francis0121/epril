package com.francis.epril.util;

import java.util.List;

/**
 * @author Francis
 * 
 * @param <T>
 */
public interface CrudDao<T> {

	List<T> selectList(Object object);

	T selectOne(Object object);

	T selectCount(Object object);

	void insert(T t);

	void update(T t);

	void delete(T t);
}
