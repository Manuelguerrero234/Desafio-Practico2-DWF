package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.dto.request.InvoiceRequestDTO;
import sv.edu.udb.dto.response.InvoiceResponseDTO;
import sv.edu.udb.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    public InvoiceResponseDTO getInvoiceById(@PathVariable Long id) {
        return invoiceService.findById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<InvoiceResponseDTO> getInvoicesByClient(@PathVariable Long clientId) {
        return invoiceService.findByClientId(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponseDTO createInvoice(@Valid @RequestBody InvoiceRequestDTO dto) {
        return invoiceService.save(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.delete(id);
    }
}