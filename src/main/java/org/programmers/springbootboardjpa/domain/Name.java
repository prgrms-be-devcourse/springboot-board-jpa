package org.programmers.springbootboardjpa.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Name {

    public Name(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    private static final Set<Locale> FAMILY_FIRST_LOCALES = new HashSet<>();

    static {
        FAMILY_FIRST_LOCALES.add(Locale.JAPANESE);
        FAMILY_FIRST_LOCALES.add(Locale.KOREAN);
        FAMILY_FIRST_LOCALES.add(Locale.CHINESE);
        FAMILY_FIRST_LOCALES.add(Locale.SIMPLIFIED_CHINESE);
        FAMILY_FIRST_LOCALES.add(Locale.TRADITIONAL_CHINESE);
        FAMILY_FIRST_LOCALES.add(Locale.JAPAN);
        FAMILY_FIRST_LOCALES.add(Locale.KOREA);
    }

    private String firstname;
    private String lastname;

    //TODO: 문자열에 + 연산자 사용하여 성능 감소가 어느 정도일지, 적당히 완성된 이후에 성능 비교
    public String getName() {
        return firstname + " " + lastname;
    }

    public String getName(Locale locale) {
        if (isFamilyNameFirstIn(locale)) {
            return lastname + " " + firstname;
        }
        return getName();
    }

    private boolean isFamilyNameFirstIn(Locale locale) {
        return FAMILY_FIRST_LOCALES.contains(locale);
    }
}