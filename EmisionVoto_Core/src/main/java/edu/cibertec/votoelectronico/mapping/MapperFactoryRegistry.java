package edu.cibertec.votoelectronico.mapping;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.dto.EmisionVotoDto;
import edu.cibertec.votoelectronico.dto.VotoDto;
import edu.cibertec.votoelectronico.dto.VotoResumenDto;

public class MapperFactoryRegistry {

	private Map<Key, BaseMapper<?, ?>> converters = new HashMap<>();

	@Inject
	public MapperFactoryRegistry(EmisionVotoDtoMapper emisionVotoDtoMapper, VotoDtoMapper votoDtoMapper,
			VotoResumenDtoMapper votoResumenDtoMapper) {
		converters.put(new Key(Voto.class, EmisionVotoDto.class), emisionVotoDtoMapper);
		converters.put(new Key(EmisionVotoDto.class, Voto.class), emisionVotoDtoMapper);
		converters.put(new Key(Voto.class, VotoDto.class), votoDtoMapper);
		converters.put(new Key(VotoDto.class, Voto.class), votoDtoMapper);
		converters.put(new Key(VotoResumen.class, VotoResumenDto.class), votoResumenDtoMapper);
		converters.put(new Key(VotoResumenDto.class, VotoResumen.class), votoResumenDtoMapper);
	}

	@SuppressWarnings("unchecked")
	private <F, T> BaseMapper<F, T> getMapper(Class<F> fromType, Class<T> toType) {
		Key key = new Key(fromType, toType);
		if (!this.converters.containsKey(key))
			throw new IllegalArgumentException("Missing Converter Factory for Type " + key.getClass().getTypeName());
		return (BaseMapper<F, T>) converters.get(key);
	}

	@SuppressWarnings("unchecked")
	public <X, Y> Y convertFrom(X object, Class<Y> typeMap) {
		BaseMapper<X, Y> mapper = (BaseMapper<X, Y>) this.getMapper(object.getClass(), typeMap);
		return (Y) mapper.convertFrom(object);
	}

	@SuppressWarnings("unchecked")
	public <X, Y> X convertTo(Y object, Class<X> typeMap) {
		BaseMapper<X, Y> mapper = (BaseMapper<X, Y>) this.getMapper(object.getClass(), typeMap);
		return (X) mapper.convertTo(object);
	}

	private class Key {

		private Class<?> fromType;
		private Class<?> toType;

		public Key(Class<?> fromType, Class<?> toType) {
			super();
			this.fromType = fromType;
			this.toType = toType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((fromType == null) ? 0 : fromType.hashCode());
			result = prime * result + ((toType == null) ? 0 : toType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (fromType == null) {
				if (other.fromType != null)
					return false;
			} else if (!fromType.equals(other.fromType))
				return false;
			if (toType == null) {
				if (other.toType != null)
					return false;
			} else if (!toType.equals(other.toType))
				return false;
			return true;
		}

		private MapperFactoryRegistry getEnclosingInstance() {
			return MapperFactoryRegistry.this;
		}

	}

}
