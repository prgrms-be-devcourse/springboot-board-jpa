package org.jpa.kdtboard.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yunyun on 2021/10/14.
 */
public class Encrypt {
    public static String SHA256(String pwBeforeEncryption) throws NoSuchAlgorithmException {
        StringBuffer sbuf = new StringBuffer();

        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        mDigest.update(pwBeforeEncryption.getBytes());

        byte[] msgStr = mDigest.digest() ;

        for(int i=0; i < msgStr.length; i++){
            byte tmpStrByte = msgStr[i];
            String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);

            sbuf.append(tmpEncTxt) ;
        }
        return sbuf.toString();
    }
}
