package sv.edu.udb.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient;
    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();
        clientRepository.save(testClient);

        testInvoice = Invoice.builder()
                .description("Laptop")
                .baseAmount(1000.0)
                .tax(130.0)
                .totalAmount(1130.0)
                .issueDate(LocalDate.now())
                .client(testClient)
                .build();
    }

    @Test
    void shouldSaveInvoice() {
        Invoice saved = invoiceRepository.save(testInvoice);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getBaseAmount()).isEqualTo(1000.0);
        assertThat(saved.getTax()).isEqualTo(130.0);
        assertThat(saved.getTotalAmount()).isEqualTo(1130.0);
    }

    @Test
    void shouldFindInvoiceById() {
        Invoice saved = invoiceRepository.save(testInvoice);
        var found = invoiceRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDescription()).isEqualTo("Laptop");
    }

    @Test
    void shouldFindInvoicesByClientId() {
        invoiceRepository.save(testInvoice);

        Invoice invoice2 = Invoice.builder()
                .description("Mouse")
                .baseAmount(25.0)
                .tax(3.25)
                .totalAmount(28.25)
                .issueDate(LocalDate.now())
                .client(testClient)
                .build();
        invoiceRepository.save(invoice2);

        List<Invoice> invoices = invoiceRepository.findByClientId(testClient.getId());
        assertThat(invoices).hasSize(2);
    }

    @Test
    void shouldDeleteInvoice() {
        Invoice saved = invoiceRepository.save(testInvoice);
        invoiceRepository.deleteById(saved.getId());
        var found = invoiceRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}