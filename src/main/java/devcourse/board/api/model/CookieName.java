package devcourse.board.api.model;

public enum CookieName {

    MEMBER_ID("memberId");

    private final String cookieName;

    CookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieName() {
        return cookieName;
    }
}
