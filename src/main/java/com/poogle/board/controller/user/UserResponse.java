package com.poogle.board.controller.user;

import com.poogle.board.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponse(User source) {
        copyProperties(source, this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("hobby", hobby)
                .append("createdAt", createdAt)
                .append("modifiedAt", modifiedAt)
                .toString();
    }

}
