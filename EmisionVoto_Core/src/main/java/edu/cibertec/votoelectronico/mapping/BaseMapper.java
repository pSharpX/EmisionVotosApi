package edu.cibertec.votoelectronico.mapping;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<X, Y> {

	Y convertFrom(X object);

	X convertTo(Y object);

	default List<X> convertTo(List<Y> collections) {
		return collections.stream().map(this::convertTo).collect(Collectors.toList());
	}

	default List<Y> convertFrom(List<X> collections) {
		return collections.stream().map(this::convertFrom).collect(Collectors.toList());
	}
}