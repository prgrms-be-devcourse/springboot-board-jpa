package com.example.board.domain.post.entity;

import com.example.board.domain.common.entity.BaseTimeEntity;
import com.example.board.domain.member.entity.Member;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SequenceGenerator(
    name = "POST_SEQ_GENERATOR",
    sequenceName = "POST_SEQ"
)
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
  @Column(name = "post_id")
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  public Post(String title, String content, Member member) {
    this.title = title;
    this.content = content;
    registerMember(member);
  }

  public void changeTitle(String newTitle) {
    this.title = newTitle;
  }

  public void changeContent(String newContent) {
    this.content = newContent;
  }

  public void preUpdate(String memberName){
    this.updatedBy = memberName;
  }

  public void registerMember(Member member) {
    if (Objects.nonNull(this.member)) {
      this.member.getPosts().remove(this);
    }

    this.member = member;
    this.member.getPosts().add(this);
  }

  @PrePersist
  public void prePersist(){
    this.createdBy = this.getMember().getName();
    this.updatedBy = this.createdBy;
  }
}
