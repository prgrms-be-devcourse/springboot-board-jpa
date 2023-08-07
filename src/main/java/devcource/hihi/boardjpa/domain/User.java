package devcource.hihi.boardjpa.domain;

import devcource.hihi.boardjpa.dto.user.CreateUserDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer age;

    private String hobby;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @Builder
    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(Integer age) {
        this.age = age;
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }

    public static ResponseUserDto toDtoForResponse(User user) {
        return new ResponseUserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
    public static CreateUserDto toDtoForCreate(User user) {
        return new CreateUserDto(user.getName(), user.getAge(), user.getHobby());
    }

}
