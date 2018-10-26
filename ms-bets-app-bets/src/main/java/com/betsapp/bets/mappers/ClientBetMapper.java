package com.betsapp.bets.mappers;

import com.betsapp.bets.domain.ClientBet;
import com.betsapp.bets.services.ClientBetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientBetMapper {

    @Mappings({
            @Mapping(source = "client", target = "client"),
            @Mapping(source = "match", target = "match")
    })
    public ClientBetDTO toDto(ClientBet userBet);

    public List<ClientBetDTO> toDto(List<ClientBet> list);

    @Mappings({
            @Mapping(source = "client", target = "client"),
            @Mapping(source = "match", target = "match")
    })
    public ClientBet toEntity(ClientBetDTO userBet);

}
