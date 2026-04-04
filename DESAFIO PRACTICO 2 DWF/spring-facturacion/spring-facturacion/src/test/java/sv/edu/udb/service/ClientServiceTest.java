package sv.edu.udb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.domain.Client;
import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;
import sv.edu.udb.mapper.ClientMapper;
import sv.edu.udb.repository.ClientRepository;
import sv.edu.udb.service.impl.ClientServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientRequestDTO requestDTO;
    private ClientResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();

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
    void shouldFindAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toResponseDTOList(anyList())).thenReturn(List.of(responseDTO));

        List<ClientResponseDTO> result = clientService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Juan Perez");
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldFindClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toResponseDTO(any(Client.class))).thenReturn(responseDTO);

        ClientResponseDTO result = clientService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Juan Perez");
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client not found with id: 99");
    }

    @Test
    void shouldSaveClient() {
        when(clientMapper.toEntity(any(ClientRequestDTO.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toResponseDTO(any(Client.class))).thenReturn(responseDTO);

        ClientResponseDTO result = clientService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Juan Perez");
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenDocumentAlreadyExists() {
        when(clientRepository.findByDocument("12345678-9")).thenReturn(Optional.of(client));

        assertThatThrownBy(() -> clientService.save(requestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Document already exists");
    }

    @Test
    void shouldDeleteClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        clientService.delete(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentClient() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> clientService.delete(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client not found with id: 99");
    }
}