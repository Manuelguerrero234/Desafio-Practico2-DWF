package sv.edu.udb.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.domain.Client;
import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;
import sv.edu.udb.mapper.ClientMapper;
import sv.edu.udb.repository.ClientRepository;
import sv.edu.udb.service.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientMapper.toResponseDTOList(clientRepository.findAll());
    }

    @Override
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {
        if (clientRepository.findByDocument(dto.getDocument()).isPresent()) {
            throw new IllegalArgumentException("Document already exists: " + dto.getDocument());
        }
        Client client = clientMapper.toEntity(dto);
        return clientMapper.toResponseDTO(clientRepository.save(client));
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}