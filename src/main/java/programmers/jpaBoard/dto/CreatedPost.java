package programmers.jpaBoard.dto;

public class CreatedPost {
    public record Request(
            String title,
            String content) {
    }
}
