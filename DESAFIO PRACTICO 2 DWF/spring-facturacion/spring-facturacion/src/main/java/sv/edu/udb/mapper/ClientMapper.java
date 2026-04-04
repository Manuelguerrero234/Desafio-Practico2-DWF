package sv.edu.udb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.domain.Client;
import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponseDTO(Client client);

    List<ClientResponseDTO> toResponseDTOList(List<Client> clients);
}