package sv.edu.udb.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String document;
    private List<InvoiceResponseDTO> invoices;
}