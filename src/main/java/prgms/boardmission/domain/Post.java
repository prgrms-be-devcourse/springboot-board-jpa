package prgms.boardmission.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Post extends BaseEntity{
    @Id
    private long id;

    @Column(nullable = false,length = 30)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "userId")
    private Member member;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMember(Member postMember){
        member = postMember;
        this.setCreatedBy(postMember.getName());
    }
}
