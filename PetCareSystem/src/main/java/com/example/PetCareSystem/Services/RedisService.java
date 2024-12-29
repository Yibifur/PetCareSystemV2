package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.Security.SecurityConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addSession(String userId, String token) {
        // Rastgele bir sessionId oluştur
        Random random = new Random();
        String sessionId =String.valueOf(random.nextInt(Integer.MAX_VALUE)) ;

        // Session verilerini bir Map olarak hazırlayın
        Map<String, String> sessionData = new HashMap<>();
        sessionData.put("userId", String.valueOf(userId));
        sessionData.put("token", token);

        // Redis'e kaydet
        redisTemplate.opsForHash().putAll(sessionId, sessionData);

        // İsteğe bağlı olarak TTL ekleyebilirsiniz (örn. 15 dakika)
        redisTemplate.expire(sessionId, Duration.ofSeconds(SecurityConstants.SESSIONANDJWT_LIFETIME));


    }

    public Map<Object, Object> getSession(String sessionId) {
        // Redis'ten veriyi al
        return redisTemplate.opsForHash().entries(sessionId);
    }

    public void deleteSession(String userID) {
        // Redis'teki tüm oturum anahtarlarını al
        Set<String> keys = redisTemplate.keys("*");

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                // Redis'teki oturum verilerini al
                Map<Object, Object> sessionData = redisTemplate.opsForHash().entries(key);

                // Eğer userID eşleşiyorsa bu oturumu sil
                if (sessionData != null && userID.equals(sessionData.get("userId"))) {
                    redisTemplate.delete(key);
                    System.out.println("Session deleted for userID: " + userID);
                    return; // Eşleşme bulunduğunda işlemi sonlandır
                }
            }
        }

        System.out.println("No session found for userID: " + userID);
    }


    public boolean isSessionValid(String userId) {
        // Redis'teki tüm session anahtarlarını al
        Set<String> keys = redisTemplate.keys("*"); // Anahtarlar String olarak alınır

        if (keys == null || keys.isEmpty()) {
            return false; // Hiçbir anahtar yoksa, geçersiz
        }

        // Her bir anahtarı dolaşarak userId'yi kontrol et
        for (String key : keys) {
            // Redis'ten key'e karşılık gelen oturum verilerini al
            Map<Object, Object> sessionData = redisTemplate.opsForHash().entries(key);

            // Eğer oturumda userId eşleşiyorsa geçerli
            if (sessionData != null && userId.equals(sessionData.get("userId"))) {
                return true; // userId bulundu, oturum geçerli
            }
        }

        return false; // Hiçbir eşleşme bulunamadıysa, geçersiz
    }




}

