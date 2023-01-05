package com.example.board.common.session;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionStorage {

  private final Map<UUID, AuthenticatedMember> storage = new ConcurrentHashMap<>();

  public boolean containsKey(UUID key) {
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

  public Optional<UUID> getLoginedSessionId(Long memberId, String email) {
    Set<UUID> keys = storage.keySet();
    return keys.stream()
        .filter(key -> {
          AuthenticatedMember member = storage.get(key);
          return member.equals(AuthenticatedMember.from(memberId, email));
        })
        .findAny();
  }

  public void remove(UUID sessionId) {
    storage.remove(sessionId);
  }
}
