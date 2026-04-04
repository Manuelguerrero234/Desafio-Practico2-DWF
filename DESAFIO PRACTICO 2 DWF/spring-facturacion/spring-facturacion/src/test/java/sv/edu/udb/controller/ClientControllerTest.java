package sv.edu.udb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;
import sv.edu.udb.service.ClientService;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    private ClientRequestDTO requestDTO;
    private ClientResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ClientRequestDTO.builder()
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();

        responseDTO = ClientResponseDTO.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();
    }

    @Test
    void shouldGetAllClients() throws Exception {
        when(clientService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Juan Perez"));

        verify(clientService, times(1)).findAll();
    }

    @Test
    void shouldGetClientById() throws Exception {
        when(clientService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"));

        verify(clientService, times(1)).findById(1L);
    }

    @Test
    void shouldReturn404WhenClientNotFound() throws Exception {
        when(clientService.findById(99L)).thenThrow(new EntityNotFoundException("Client not found with id: 99"));

        mockMvc.perform(get("/api/clients/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateClient() throws Exception {
        when(clientService.save(any(ClientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Perez"));

        verify(clientService, times(1)).save(any(ClientRequestDTO.class));
    }

    @Test
    void shouldReturn400WhenClientValidationFails() throws Exception {
        ClientRequestDTO invalidRequest = ClientRequestDTO.builder()
                .name("")
                .email("invalid-email")
                .document("invalid")
                .build();

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteClient() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).delete(1L);
    }
}