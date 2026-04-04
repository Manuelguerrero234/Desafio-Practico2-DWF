package sv.edu.udb.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class InvoiceResponseDTO {
    private Long id;
    private String description;
    private Double baseAmount;
    private Double tax;
    private Double totalAmount;
    private LocalDate issueDate;
    private Long clientId;
}