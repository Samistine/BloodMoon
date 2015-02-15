package uk.co.jacekk.bukkit.bloodmoon;

public class EntityRegistrationException extends Exception {
	
	private static final long serialVersionUID = 5269865836575653186L;
	
	public EntityRegistrationException(String message){
		super(message);
	}
	
	public EntityRegistrationException(String message, Exception cause){
		super(message, cause);
	}
	
}
