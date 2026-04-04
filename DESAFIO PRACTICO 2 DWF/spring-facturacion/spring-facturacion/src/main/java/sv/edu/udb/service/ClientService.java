package sv.edu.udb.service;

import sv.edu.udb.dto.request.ClientRequestDTO;
import sv.edu.udb.dto.response.ClientResponseDTO;
import java.util.List;

public interface ClientService {
    List<ClientResponseDTO> findAll();
    ClientResponseDTO findById(Long id);
    ClientResponseDTO save(ClientRequestDTO dto);
    void delete(Long id);
}