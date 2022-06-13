# Spring Boot JPA로 게시판 구현

- 진행 기간 : 2022.05.22 ~ (진행중)
- 주요내용
- JPA 학습한 것을 이해하고 적용하여 블로그 시스템을 이해하고자 한다.
- 계층마다 독립적으로 Test Code 작성
  - Appliation Layer에서는 행위 테스트 : 필요에 따라 상태테스트 진행
  - 가능한 많은 엣지 케이스 작성 노력
- RestDocs를 통해 API 명세화
- 객체 매핑 전략
  - 성능에 필요한 상황에 따라 조인 연산 개선
- Lombok과 유효성 검사 빈 사용 X
- AOP를 사용하여 HTTP Request와 ip 등 필요 정보 logging

- 스스로 연관관계를 고민하며, 주어진 미션보다 확장하여 배운 기술을 활용하고자함

  - (AOP, 테스트 코드, 매핑 전략, 라이브러리 의존도 낮춰서 진행)

- 기술 스택
  - Springboot, JPA, OOP, RESTDOCS, Mockito
