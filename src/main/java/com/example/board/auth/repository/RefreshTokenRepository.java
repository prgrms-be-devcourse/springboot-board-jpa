package com.example.board.auth.repository;

import com.example.board.auth.domain.TokenInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<TokenInfo, String> {
    Optional<TokenInfo> findByRefreshToken(String refreshToken);
}
