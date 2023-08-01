package devcource.hihi.boardjpa.test;

import devcource.hihi.boardjpa.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTestHelper {
    public static List<User> createSampleUsers(int count){
        List<User> users = new ArrayList<>();
        for(int i = 0; i <= count; i++){
            users.add(User.builder()
                    .name("User" + i)
                    .age(20 + i)
                    .hobby("Hobby" + i)
                    .build());
        }
        return users;

    }


}
