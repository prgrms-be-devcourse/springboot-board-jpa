package kdt.prgms.springbootboard.global.error.exception;

public class PostNotFoundException extends EntityNotFoundException{

    public PostNotFoundException(String target) {
        super(target + " is not found");
    }
}