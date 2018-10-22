package com.betsapp.bets.mappers;

import com.betsapp.bets.domain.UserBet;
import com.betsapp.bets.services.UserBetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBetMapper {

    @Mappings({
            @Mapping(source = "user.id", target = "user"),
            @Mapping(source = "match.id", target = "match")
    })
    public UserBetDTO toDto(UserBet userBet);

    public List<UserBetDTO> toDto(List<UserBet> list);

    @Mappings({
            @Mapping(source = "user", target = "user.id"),
            @Mapping(source = "match", target = "match.id")
    })
    public UserBet toEntity(UserBetDTO userBet);

}
