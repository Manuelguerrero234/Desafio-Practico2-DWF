package sv.edu.udb.service;

import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;
import java.util.List;

public interface InvoiceService {
    List<InvoiceResponseDTO> findAll();
    InvoiceResponseDTO findById(Long id);
    InvoiceResponseDTO save(InvoiceRequestDTO dto);
    void delete(Long id);
    List<InvoiceResponseDTO> findByClientId(Long clientId);
}