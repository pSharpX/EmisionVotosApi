package edu.cibertec.votoelectronico.domain.complex.transformer;

import java.util.List;

import edu.cibertec.votoelectronico.domain.complex.VotoResumen;

public class VotoResumenTransformer implements ListResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public VotoResumen transformTuple(Object[] tuple, String[] aliases) {
		return new VotoResumen((tuple[2]).toString(), ((List<String>) tuple[0]), ((Number) tuple[1]).intValue());
	}

}
