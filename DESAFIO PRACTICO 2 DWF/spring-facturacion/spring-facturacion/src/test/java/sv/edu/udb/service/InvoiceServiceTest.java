package sv.edu.udb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;
import sv.edu.udb.mapper.InvoiceMapper;
import sv.edu.udb.repository.ClientRepository;
import sv.edu.udb.repository.InvoiceRepository;
import sv.edu.udb.service.impl.InvoiceServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Client client;
    private Invoice invoice;
    private InvoiceRequestDTO requestDTO;
    private InvoiceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();

        invoice = Invoice.builder()
                .id(1L)
                .description("Laptop")
                .baseAmount(1000.0)
                .tax(130.0)
                .totalAmount(1130.0)
                .issueDate(LocalDate.now())
                .client(client)
                .build();

        requestDTO = InvoiceRequestDTO.builder()
                .description("Laptop")
                .baseAmount(1000.0)
                .clientId(1L)
                .build();

        responseDTO = InvoiceResponseDTO.builder()
                .id(1L)
                .description("Laptop")
                .baseAmount(1000.0)
                .tax(130.0)
                .totalAmount(1130.0)
                .clientId(1L)
                .build();
    }

    @Test
    void shouldFindAllInvoices() {
        when(invoiceRepository.findAll()).thenReturn(List.of(invoice));
        when(invoiceMapper.toResponseDTOList(anyList())).thenReturn(List.of(responseDTO));

        List<InvoiceResponseDTO> result = invoiceService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Laptop");
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void shouldFindInvoiceById() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toResponseDTO(any(Invoice.class))).thenReturn(responseDTO);

        InvoiceResponseDTO result = invoiceService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getBaseAmount()).isEqualTo(1000.0);
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {
        when(invoiceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> invoiceService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Invoice not found with id: 99");
    }

    @Test
    void shouldCalculateTaxCorrectlyWhenSavingInvoice() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(invoiceMapper.toEntity(any(InvoiceRequestDTO.class))).thenReturn(invoice);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        when(invoiceMapper.toResponseDTO(any(Invoice.class))).thenReturn(responseDTO);

        InvoiceResponseDTO saved = invoiceService.save(requestDTO);

        assertThat(saved.getBaseAmount()).isEqualTo(1000.0);
        assertThat(saved.getTax()).isEqualTo(130.0);
        assertThat(saved.getTotalAmount()).isEqualTo(1130.0);
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnSave() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());
        requestDTO.setClientId(99L);

        assertThatThrownBy(() -> invoiceService.save(requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client not found with id: 99");
    }

    @Test
    void shouldDeleteInvoice() {
        when(invoiceRepository.existsById(1L)).thenReturn(true);
        doNothing().when(invoiceRepository).deleteById(1L);

        invoiceService.delete(1L);

        verify(invoiceRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentInvoice() {
        when(invoiceRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> invoiceService.delete(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Invoice not found with id: 99");
    }

    @Test
    void shouldFindInvoicesByClientId() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findByClientId(1L)).thenReturn(List.of(invoice));
        when(invoiceMapper.toResponseDTOList(anyList())).thenReturn(List.of(responseDTO));

        List<InvoiceResponseDTO> result = invoiceService.findByClientId(1L);

        assertThat(result).hasSize(1);
        verify(invoiceRepository, times(1)).findByClientId(1L);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnFindByClientId() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> invoiceService.findByClientId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client not found with id: 99");
    }
}