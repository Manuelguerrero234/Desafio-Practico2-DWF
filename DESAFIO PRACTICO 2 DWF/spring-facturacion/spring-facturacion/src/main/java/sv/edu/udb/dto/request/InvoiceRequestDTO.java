package sv.edu.udb.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDTO {
    private String description;

    @NotNull(message = "Base amount is required")
    @Positive(message = "Base amount must be positive")
    private Double baseAmount;

    @NotNull(message = "Client ID is required")
    private Long clientId;
}