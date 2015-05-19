package com.zsxj.datareport2.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sen on 15-5-19.
 */
public class ListOfJson<T> implements ParameterizedType {
	private Class<?> wrapped;

	public ListOfJson(Class<T> wrapper) {
		this.wrapped = wrapper;
	}

	@Override
	public Type[] getActualTypeArguments() {
		return new Type[]{wrapped};
	}

	@Override
	public Type getRawType() {
		return List.class;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}
}
