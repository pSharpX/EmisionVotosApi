package edu.cibertec.votoelectronico.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.helper.BCryptSecureHashing;
import edu.cibertec.votoelectronico.helper.SecureHashingHelper;

public class VotoListener {

	private SecureHashingHelper hashingHelper;

	public VotoListener() {
		this.hashingHelper = new BCryptSecureHashing();
	}

	private final Logger LOG = LoggerFactory.getLogger(VotoListener.class);

	@PrePersist
	public void beforePersist(Voto object) {
		LOG.info("BeforePersist EventListener");
		LOG.info("Before Hashing secure information:");
		LOG.info(object.getData());
		String hashedData = hashingHelper.hashPassword(object.getData());
		LOG.info("After Hashing secure information:");
		LOG.info(hashedData);
		object.setHash(hashedData);
	}

	@PreUpdate
	public void beforeUpdate(Voto object) {
		LOG.info("BeforeUpdate EventListener");
	}
}
