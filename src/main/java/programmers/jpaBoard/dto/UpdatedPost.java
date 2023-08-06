package programmers.jpaBoard.dto;

public class UpdatedPost {
    public record Request(
            String title,
            String content) {
    }
}
