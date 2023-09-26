package devcource.hihi.boardjpa.dto.user;

import devcource.hihi.boardjpa.domain.User;
import lombok.Getter;

public record ResponseUserDto (Long id,String name,Integer age,String hobby){

    public static ResponseUserDto toDtoForResponse(User user) {
        return new ResponseUserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}