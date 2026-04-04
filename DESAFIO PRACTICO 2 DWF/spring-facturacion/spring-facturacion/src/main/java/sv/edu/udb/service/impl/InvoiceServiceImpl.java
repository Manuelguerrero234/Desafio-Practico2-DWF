package sv.edu.udb.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;
import sv.edu.udb.mapper.InvoiceMapper;
import sv.edu.udb.repository.ClientRepository;
import sv.edu.udb.repository.InvoiceRepository;
import sv.edu.udb.service.InvoiceService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final InvoiceMapper invoiceMapper;

    private static final double TAX_RATE = 0.13;

    @Override
    public List<InvoiceResponseDTO> findAll() {
        return invoiceMapper.toResponseDTOList(invoiceRepository.findAll());
    }

    @Override
    public InvoiceResponseDTO findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    @Transactional
    public InvoiceResponseDTO save(InvoiceRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + dto.getClientId()));

        // Business logic: calculate tax and total
        double base = dto.getBaseAmount();
        double tax = Math.round((base * TAX_RATE) * 100.0) / 100.0;
        double total = Math.round((base + tax) * 100.0) / 100.0;

        Invoice invoice = invoiceMapper.toEntity(dto);
        invoice.setTax(tax);
        invoice.setTotalAmount(total);
        invoice.setIssueDate(LocalDate.now());
        invoice.setClient(client);

        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    @Override
    public List<InvoiceResponseDTO> findByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
        return invoiceMapper.toResponseDTOList(invoiceRepository.findByClientId(clientId));
    }
}