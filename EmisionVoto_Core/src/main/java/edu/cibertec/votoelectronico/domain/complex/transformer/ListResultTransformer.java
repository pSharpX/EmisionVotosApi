package edu.cibertec.votoelectronico.domain.complex.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

@FunctionalInterface
public interface ListResultTransformer extends ResultTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	default List transformList(List collection) {
		return collection;
	}

}
