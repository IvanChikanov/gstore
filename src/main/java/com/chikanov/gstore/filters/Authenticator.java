package com.chikanov.gstore.filters;

import com.chikanov.gstore.exceptions.ResponseException;
import com.chikanov.gstore.records.AuthData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class Authenticator {
    @Value("${token.value}")
    private String token;

    private final String key = "WebAppData";

    public AuthData validation(String telegramSafeData)
    {
        try {
            Map<String, String> params = getTokenMap(telegramSafeData);
            String ready = getString(params);
            byte[] token = getHash(this.token.getBytes(), key.getBytes());
            byte[] data = getHash(ready.getBytes(), token);
            if(getHex(data).equals(params.get("hash")))
            {
                return new AuthData(HttpStatus.OK, params.get("user"));
            }
            else{
                return new AuthData(HttpStatus.FORBIDDEN, "Not authenticated");
            }
        }
        catch (Exception ex)
        {
            return new AuthData(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
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
    private Map<String, String> getTokenMap(String auth)
    {
        Map<String, String> params = new TreeMap<>();
        String decoded = URLDecoder.decode(auth, StandardCharsets.UTF_8);
        String[] splitted = decoded.split("&");
        SortedSet<String> others = new TreeSet<>();
        for(var s : splitted)
        {
            String[] buff = s.split("=");
            params.put(buff[0], buff[1]);
        }
        return params;
    }
    private String getString(Map<String, String> params)
    {
        SortedSet<String> str = new TreeSet<>();
        params.forEach((key, value) -> {
            if(!key.equals("hash"))
                str.add(String.format("%s=%s", key, value));
        });
        return String.join("\n", str);
    }

}
