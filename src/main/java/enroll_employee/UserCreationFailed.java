package enroll_employee;

public class UserCreationFailed extends RuntimeException {

    public UserCreationFailed(String error) {
        super(error);
    }
}