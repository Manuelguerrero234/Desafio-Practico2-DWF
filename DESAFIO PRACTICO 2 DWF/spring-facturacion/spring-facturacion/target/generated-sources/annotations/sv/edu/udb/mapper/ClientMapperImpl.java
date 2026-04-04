package sv.edu.udb.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-04T08:45:31-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.name( dto.getName() );
        client.email( dto.getEmail() );
        client.document( dto.getDocument() );

        return client.build();
    }

    @Override
    public ClientResponseDTO toResponseDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO.ClientResponseDTOBuilder clientResponseDTO = ClientResponseDTO.builder();

        clientResponseDTO.id( client.getId() );
        clientResponseDTO.name( client.getName() );
        clientResponseDTO.email( client.getEmail() );
        clientResponseDTO.document( client.getDocument() );
        clientResponseDTO.invoices( invoiceListToInvoiceResponseDTOList( client.getInvoices() ) );

        return clientResponseDTO.build();
    }

    @Override
    public List<ClientResponseDTO> toResponseDTOList(List<Client> clients) {
        if ( clients == null ) {
            return null;
        }

        List<ClientResponseDTO> list = new ArrayList<ClientResponseDTO>( clients.size() );
        for ( Client client : clients ) {
            list.add( toResponseDTO( client ) );
        }

        return list;
    }

    protected InvoiceResponseDTO invoiceToInvoiceResponseDTO(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceResponseDTO.InvoiceResponseDTOBuilder invoiceResponseDTO = InvoiceResponseDTO.builder();

        invoiceResponseDTO.id( invoice.getId() );
        invoiceResponseDTO.description( invoice.getDescription() );
        invoiceResponseDTO.baseAmount( invoice.getBaseAmount() );
        invoiceResponseDTO.tax( invoice.getTax() );
        invoiceResponseDTO.totalAmount( invoice.getTotalAmount() );
        invoiceResponseDTO.issueDate( invoice.getIssueDate() );

        return invoiceResponseDTO.build();
    }

    protected List<InvoiceResponseDTO> invoiceListToInvoiceResponseDTOList(List<Invoice> list) {
        if ( list == null ) {
            return null;
        }

        List<InvoiceResponseDTO> list1 = new ArrayList<InvoiceResponseDTO>( list.size() );
        for ( Invoice invoice : list ) {
            list1.add( invoiceToInvoiceResponseDTO( invoice ) );
        }

        return list1;
    }
}
