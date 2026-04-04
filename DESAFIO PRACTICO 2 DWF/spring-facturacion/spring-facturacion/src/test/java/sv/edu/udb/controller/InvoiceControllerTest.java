package sv.edu.udb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;
import sv.edu.udb.service.InvoiceService;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceService invoiceService;

    private InvoiceRequestDTO requestDTO;
    private InvoiceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
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
                .issueDate(LocalDate.now())
                .clientId(1L)
                .build();
    }

    @Test
    void shouldGetAllInvoices() throws Exception {
        when(invoiceService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Laptop"));
    }

    @Test
    void shouldGetInvoiceById() throws Exception {
        when(invoiceService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(1130.0));
    }

    @Test
    void shouldGetInvoicesByClientId() throws Exception {
        when(invoiceService.findByClientId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/invoices/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId").value(1L));
    }

    @Test
    void shouldCreateInvoice() throws Exception {
        when(invoiceService.save(any(InvoiceRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalAmount").value(1130.0));
    }

    @Test
    void shouldReturn400WhenInvoiceValidationFails() throws Exception {
        InvoiceRequestDTO invalidRequest = InvoiceRequestDTO.builder()
                .description("")
                .baseAmount(-100.0)
                .clientId(null)
                .build();

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenClientNotFoundOnInvoiceCreation() throws Exception {
        when(invoiceService.save(any(InvoiceRequestDTO.class)))
                .thenThrow(new EntityNotFoundException("Client not found with id: 99"));

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteInvoice() throws Exception {
        doNothing().when(invoiceService).delete(1L);

        mockMvc.perform(delete("/api/invoices/1"))
                .andExpect(status().isNoContent());
    }
}