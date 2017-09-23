package com.codespurt.basicappstructureboilerplate.security;

import android.util.Base64;

import com.codespurt.basicappstructureboilerplate.engine.App;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by CodeSpurt on 23-09-2017.
 */

public class EncryptData {

    private static byte[] IV_VECTOR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String encryptData(String string) throws Exception {
        if ((string == null) || (string.isEmpty())) {
            return "";
        }
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        localCipher.init(1, new SecretKeySpec(App.AES_KEY.getBytes(), "AES"), new IvParameterSpec(IV_VECTOR));
        return Base64.encodeToString(localCipher.doFinal(string.getBytes()), 2);
    }
}
