package com.chikanov.gstore.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class Authenticator {
    @Value("${token.value}")
    private String token;
    private final String key = "WebAppData";
    public boolean validation(String telegramSafeData)
    {
        try {
            String decoded = URLDecoder.decode(telegramSafeData, StandardCharsets.UTF_8);
            System.out.println(decoded);
            String[] splitted = decoded.split("&");
            String hash = "";
            String user;
            SortedSet<String> others = new TreeSet<>();
            for(var s : splitted)
            {
                if(s.contains("hash"))
                {
                    hash = s.split("=")[1];

                } else if (s.contains("user")) {
                    user = s.split("=")[1];
                } else
                {
                    others.add(s);
                }
            }
            String ready = others.stream().collect(Collectors.joining("\n"));
            byte[] token = getHash(this.token.getBytes(), key.getBytes());
            byte[] data = getHash(ready.getBytes(), token);
            return getHex(data).equals(hash);
        }
        catch (Exception ex)
        {
            return false;
        }

    }
    private byte[] getHash(byte[] value, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        hmac.init(secretKey);
        byte[] hashBytes =  hmac.doFinal(value);
        return hashBytes;
    }
    private String getHex(byte[] byteArr)
    {
        StringBuilder bytes = new StringBuilder();
        for(byte b: byteArr)
        {
            bytes.append(b);
        }
        StringBuilder sb = new StringBuilder();
        for(byte b : byteArr)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
