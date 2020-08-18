package com.br.coletar.repository;

import com.br.coletar.model.VerificationToken;

import java.util.Map;

public interface VerificationTokenRepository {

    void save(VerificationToken verificationToken);
    Long findByToken(String id);
    void delete(String id);
    Map<String, String> findAll();
}

