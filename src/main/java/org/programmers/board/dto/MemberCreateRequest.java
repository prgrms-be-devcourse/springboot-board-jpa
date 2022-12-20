package org.programmers.board.dto;

public class MemberCreateRequest {

    private final String name;
    private final int age;
    private final String hobby;

    public MemberCreateRequest(String name, int age, String hobby) {
//        validateName(name);
        validateAge(age);
        validateHobby(hobby);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateHobby(String hobby) {
        if (hobby.isBlank()) {
            throw new IllegalArgumentException("취미는 적어도 하나 이상 적어주세요.");
        }
    }

    private void validateAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("나이는 양수로 입력해주세요.");
        }
    }

//    private void validateName(String name) {
//        if (name.isBlank()) {
//            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
//        }
//    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}