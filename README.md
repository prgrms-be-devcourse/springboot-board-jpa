# [미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎
Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

## 이곳은 공개 Repo입니다.
1. 여러분의 포트폴리오로 사용하셔도 됩니다.
2. 때문에 이 repo를 fork한 뒤
3. 여러분의 개인 Repo에 작업하며 
4. 이 Repo에 PR을 보내어 멘토의 코드 리뷰와 피드백을 받으세요.

## Branch 명명 규칙
1.  여러분 repo는 알아서 해주시고 😀(본인 레포니 main으로 하셔두 되져)
2.  prgrms-be-devcourse/spring-board 레포로 PR시 branch는 본인 username을 적어주세요 :)  
base repo : `여기repo` base : `username` ← head repo : `여러분repo` compare : `main`

## 미션에서 학습한 부분
1. FetchType.LAZY를 사용한 이유는 1:N 관계에서 1 부분을 조회할 때 EAGAR 형식이면 N관게까지 같이 조회를 해버린다. 이 때 FetchType.LAZY를 통해Proxy 객체를 만들어 조회가 되지 않도록 한다.이는 1번만 조회하면 될 것을 조회할때마다 N부분을 추가로 조회하여 N+1 문제가 발생하는 것을 방지할 때 주로 사용한다.
2. CASCADE란 부모 엔티티가 영속화될 때 자식 엔티티도 같이 영속화되고, 부모엔티티가 삭제될 때 자식 엔티티도 삭제되는 등 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 전이되는 것을 말한다. 이때 종류로는 ALL, PERSIST, MERGE, REMOVE, DETACH, REFRESH가 있다. 이름에서 볼 수 있듯이 이름과 관련된 상태로 바뀔 때 Cascade를 적용시킨다는 의미이다.
3. 
```
public void setUser(User user) {
        if(Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
```
이런 코드를 사용하는 이유로는 연관관계에서 N부분이 이미 User가 등록되어 있을때 1부분에서 기존에 있던 자신을 제거하고 다시 등록하기 위함이다.
4. 