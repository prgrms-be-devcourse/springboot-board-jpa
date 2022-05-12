package org.spring.notice.utils;

import java.util.UUID;

public final class UuidUtils {

    /* 정적 유틸리티 클래스 */
    private UuidUtils(){}

    public static boolean isValidUuid(String uuid){
        try{
            UUID uuid1 = UUID.fromString(uuid);
        }
        catch (IllegalArgumentException exception){
            return false;
        }
        return true;
    }
}
