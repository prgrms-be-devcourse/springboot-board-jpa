package com.sdardew.board.domain.post.converter;

import com.sdardew.board.domain.user.User;
import com.sdardew.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.NoSuchElementException;

@Component
@Converter
public class UserIdConverter implements AttributeConverter<User, Long> {

  @Autowired
  private UserRepository userRepository;

//  public UserIdConverter(UserRepository userRepository) {
//    this.userRepository = userRepository;
//  }



  @Override
  public Long convertToDatabaseColumn(User user) {
    return user.getId();
  }

  @Override
  public User convertToEntityAttribute(Long userId) {
    if(userRepository.existsById(userId)) return userRepository.findById(userId).get();
    throw new NoSuchElementException("존재하지 않는 User입니다");
  }
}
