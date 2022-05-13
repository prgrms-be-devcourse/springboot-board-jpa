package org.spring.notice.domain.user;

import lombok.*;
import org.spring.notice.domain.BaseEntity;
import org.spring.notice.domain.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.text.MessageFormat.format;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user")
@ToString(of= {"name", "age", "hobby"})
@EqualsAndHashCode(of="uuid", callSuper = false)
public class User extends BaseEntity {
    public static final int NAME_MAX_LENGTH = 30;
    public static final int HOBBY_MAX_LENGTH = 30;

    /* 유저 아이디 */
    @Id
    @Column(name="id", nullable = false, length=36)
    private String uuid;

    /* 유저 이름 */
    @Column(nullable = false, length=NAME_MAX_LENGTH)
    private String name;

    /* 유저 나이 */
    @Column(nullable = false)
    private int age;

    /* 유저 취미 */
    @Column(length = HOBBY_MAX_LENGTH)
    private String hobby;

    @OneToMany(mappedBy = "writer")
    private final List<Post> posts = new ArrayList<>();

    private User(String uuid, String name, int age, String hobby){
        try{
            UUID.fromString(uuid); // uuid validation
        } catch(IllegalArgumentException ex){
            throw new IllegalArgumentException("uuid 형식이 잘못되었습니다");
        }

        checkArgument(hasText(name), "name은 비어있으면 안됩니다");
        checkArgument(
                name.length() <= NAME_MAX_LENGTH,
                format("name 의 길이는 {0}보다 작아야 합니다", NAME_MAX_LENGTH)
        );

        checkArgument(
                hobby.length() <= HOBBY_MAX_LENGTH,
                format("hobby 의 길이는 {0}보다 작아야 합니다", HOBBY_MAX_LENGTH)
        );

        checkArgument(age > 0, "나이는 음수일 수 없습니다");

        this.uuid = uuid;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User create(String uuid, String name, int age, String hobby){
        return new User(uuid, name, age, hobby);
    }
}
