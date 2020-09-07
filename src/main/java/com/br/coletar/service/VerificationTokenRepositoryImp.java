package com.br.coletar.service;

import com.br.coletar.model.VerificationToken;
import com.br.coletar.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class VerificationTokenRepositoryImp implements VerificationTokenRepository {

    private RedisTemplate<String, VerificationToken> redisTemplate;

    private HashOperations hashOperations;

    public VerificationTokenRepositoryImp(RedisTemplate<String, VerificationToken> redisTemplate) {
        this.redisTemplate = redisTemplate;

        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(VerificationToken verificationToken) {
        hashOperations.put("verificationToken", verificationToken.getToken(), verificationToken.getUserId());
    }

    @Override
    public Long findByToken(String token) {
        return (Long) hashOperations.get("verificationToken", token);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("verificationToken", id);
    }

    @Override
    public Map<String, String> findAll() {
        return hashOperations.entries("verificationToken");
    }


}
