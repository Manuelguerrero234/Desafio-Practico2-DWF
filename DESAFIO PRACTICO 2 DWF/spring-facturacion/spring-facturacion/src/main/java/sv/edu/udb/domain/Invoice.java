package sv.edu.udb.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double baseAmount;

    private Double tax; // 13% calculado

    private Double totalAmount;

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}