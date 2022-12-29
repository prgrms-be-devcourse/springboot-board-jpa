package devcourse.board.domain.member;

import devcourse.board.domain.member.model.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findOne(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findByEmail(String email) {
        try {
            return Optional.of(
                    em.createQuery("select m from Member m where m.email = :email", Member.class)
                            .setParameter("email", email)
                            .getSingleResult()
            );
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }
}
