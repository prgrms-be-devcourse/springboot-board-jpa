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
