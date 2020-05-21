package edu.cibertec.votoelectronico.helper;

import javax.enterprise.context.ApplicationScoped;

import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class BCryptSecureHashing implements SecureHashingHelper {

	@Override
	public String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	@Override
	public boolean checkPass(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

}
