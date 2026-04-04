package sv.edu.udb.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-04T08:45:31-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public Invoice toEntity(InvoiceRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Invoice.InvoiceBuilder invoice = Invoice.builder();

        invoice.client( mapClient( dto.getClientId() ) );
        invoice.description( dto.getDescription() );
        invoice.baseAmount( dto.getBaseAmount() );

        return invoice.build();
    }

    @Override
    public InvoiceResponseDTO toResponseDTO(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceResponseDTO.InvoiceResponseDTOBuilder invoiceResponseDTO = InvoiceResponseDTO.builder();

        invoiceResponseDTO.clientId( invoiceClientId( invoice ) );
        invoiceResponseDTO.id( invoice.getId() );
        invoiceResponseDTO.description( invoice.getDescription() );
        invoiceResponseDTO.baseAmount( invoice.getBaseAmount() );
        invoiceResponseDTO.tax( invoice.getTax() );
        invoiceResponseDTO.totalAmount( invoice.getTotalAmount() );
        invoiceResponseDTO.issueDate( invoice.getIssueDate() );

        return invoiceResponseDTO.build();
    }

    @Override
    public List<InvoiceResponseDTO> toResponseDTOList(List<Invoice> invoices) {
        if ( invoices == null ) {
            return null;
        }

        List<InvoiceResponseDTO> list = new ArrayList<InvoiceResponseDTO>( invoices.size() );
        for ( Invoice invoice : invoices ) {
            list.add( toResponseDTO( invoice ) );
        }

        return list;
    }

    private Long invoiceClientId(Invoice invoice) {
        Client client = invoice.getClient();
        if ( client == null ) {
            return null;
        }
        return client.getId();
    }
}
