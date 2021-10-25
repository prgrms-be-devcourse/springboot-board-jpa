package kdt.prgms.springbootboard.global.error.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String target) {
        
        super(target + " is not found");
    }
}