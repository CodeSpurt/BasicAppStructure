package com.codespurt.basicappstructureboilerplate.security;

import android.util.Base64;

import com.codespurt.basicappstructureboilerplate.engine.App;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by CodeSpurt on 23-09-2017.
 */

public class DecryptData {

    private static byte[] IV_VECTOR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String decryptData(String encryptedString) throws Exception {
        if ((encryptedString == null) || (encryptedString.isEmpty())) {
            return "";
        }
        byte[] bytes = Base64.decode(encryptedString, 2);
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        localCipher.init(2, new SecretKeySpec(App.AES_KEY.getBytes(), "AES"), new IvParameterSpec(IV_VECTOR));
        return new String(localCipher.doFinal(bytes));
    }
}
