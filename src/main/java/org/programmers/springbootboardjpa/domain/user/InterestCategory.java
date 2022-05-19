package org.programmers.springbootboardjpa.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestCategory {

    @Id
    @GeneratedValue
    private Long interestId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private InterestCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<InterestCategory> children;
}