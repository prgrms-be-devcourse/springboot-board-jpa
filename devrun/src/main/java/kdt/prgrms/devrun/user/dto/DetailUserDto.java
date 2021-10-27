package kdt.prgrms.devrun.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class DetailUserDto {

    private Long userId;

    private String loginId;

    private String name;

    private int age;

    private String email;

}
