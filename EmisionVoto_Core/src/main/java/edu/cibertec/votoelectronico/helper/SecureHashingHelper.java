package edu.cibertec.votoelectronico.helper;

public interface SecureHashingHelper {

	String hashPassword(String plainTextPassword);

	boolean checkPass(String plainPassword, String hashedPassword);

}
