package projects.koko.tinder_backend.utils;

public class ProfileExistsException extends RuntimeException{
    public ProfileExistsException(String message){
        super(message);
    }

}
