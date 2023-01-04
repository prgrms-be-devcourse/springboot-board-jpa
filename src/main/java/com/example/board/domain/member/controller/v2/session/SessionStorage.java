package com.example.board.domain.member.controller.v2.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionStorage {

  private final Map<UUID, AuthenticatedMember> storage = new ConcurrentHashMap<>();

  public boolean checkLogin(UUID key) {
    return storage.containsKey(key);
  }

  public AuthenticatedMember get(UUID key) {
    return storage.get(key);
  }

  public UUID save(Long memberId, String email, int duration) {
    UUID key = UUID.randomUUID();
    storage.put(
        key,
        AuthenticatedMember.fromDuration(memberId, email, duration));

    return key;
  }

  public void clear(){
    storage.clear();
  }
}
