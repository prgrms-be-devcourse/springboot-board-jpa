package prgrms.project.post.util.mapper;

import org.mapstruct.Mapper;
import prgrms.project.post.domain.user.Hobby;
import prgrms.project.post.service.user.HobbyDto;

@Mapper(componentModel = "spring")
public interface HobbyMapper extends EntityMapper<HobbyDto, Hobby> {
}
