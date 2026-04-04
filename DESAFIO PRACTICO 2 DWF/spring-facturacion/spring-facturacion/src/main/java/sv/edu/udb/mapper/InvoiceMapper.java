package sv.edu.udb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tax", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "issueDate", ignore = true)
    @Mapping(target = "client", source = "clientId", qualifiedByName = "mapClient")
    Invoice toEntity(InvoiceRequestDTO dto);

    @Mapping(source = "client.id", target = "clientId")
    InvoiceResponseDTO toResponseDTO(Invoice invoice);

    List<InvoiceResponseDTO> toResponseDTOList(List<Invoice> invoices);

    @Named("mapClient")
    default Client mapClient(Long clientId) {
        if (clientId == null) return null;
        Client client = new Client();
        client.setId(clientId);
        return client;
    }
}