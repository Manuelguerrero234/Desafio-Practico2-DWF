package sv.edu.udb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sv.edu.udb.domain.Client;
import sv.edu.udb.domain.Invoice;
import sv.edu.udb.repository.ClientRepository;
import sv.edu.udb.repository.InvoiceRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpiar datos existentes
        invoiceRepository.deleteAll();
        clientRepository.deleteAll();

        // CREAR CLIENTES

        // Cliente 1 - Con DUI
        Client client1 = Client.builder()
                .name("Ana María Martínez")
                .email("ana.martinez@email.com")
                .document("12345678-9") // Formato DUI
                .build();
        clientRepository.save(client1);

        // Cliente 2 - Con NIT
        Client client2 = Client.builder()
                .name("Carlos Enrique Gómez")
                .email("carlos.gomez@empresa.com")
                .document("0614-180694-101-8") // Formato NIT
                .build();
        clientRepository.save(client2);

        // Cliente 3
        Client client3 = Client.builder()
                .name("Laura Beatriz Fernández")
                .email("laura.fernandez@email.com")
                .document("87654321-0") // Formato DUI
                .build();
        clientRepository.save(client3);

        // Cliente 4
        Client client4 = Client.builder()
                .name("Roberto José Sánchez")
                .email("roberto.sanchez@empresa.com")
                .document("0212-300598-102-5") // Formato NIT
                .build();
        clientRepository.save(client4);

        // Cliente 5
        Client client5 = Client.builder()
                .name("Martha Elena Rodríguez")
                .email("martha.rodriguez@email.com")
                .document("98765432-1") // Formato DUI
                .build();
        clientRepository.save(client5);

        //CREAR FACTURAS

        // Facturas para Cliente 1 (Ana Martínez)
        Invoice invoice1 = Invoice.builder()
                .description("Compra de Laptop HP Pavilion")
                .baseAmount(850.00)
                .tax(110.50)
                .totalAmount(960.50)
                .issueDate(LocalDate.of(2026, 3, 15))
                .client(client1)
                .build();
        invoiceRepository.save(invoice1);

        Invoice invoice2 = Invoice.builder()
                .description("Mouse inalámbrico Logitech")
                .baseAmount(35.00)
                .tax(4.55)
                .totalAmount(39.55)
                .issueDate(LocalDate.of(2026, 3, 20))
                .client(client1)
                .build();
        invoiceRepository.save(invoice2);

        // Facturas para Cliente 2 (Carlos Gómez)
        Invoice invoice3 = Invoice.builder()
                .description("Servicio de consultoría TI")
                .baseAmount(1500.00)
                .tax(195.00)
                .totalAmount(1695.00)
                .issueDate(LocalDate.of(2026, 3, 10))
                .client(client2)
                .build();
        invoiceRepository.save(invoice3);

        Invoice invoice4 = Invoice.builder()
                .description("Licencia Microsoft Office 365")
                .baseAmount(120.00)
                .tax(15.60)
                .totalAmount(135.60)
                .issueDate(LocalDate.of(2026, 3, 25))
                .client(client2)
                .build();
        invoiceRepository.save(invoice4);

        // Facturas para Cliente 3 (Laura Fernández)
        Invoice invoice5 = Invoice.builder()
                .description("Curso de Programación Java")
                .baseAmount(299.99)
                .tax(39.00)
                .totalAmount(338.99)
                .issueDate(LocalDate.of(2026, 3, 5))
                .client(client3)
                .build();
        invoiceRepository.save(invoice5);

        // Facturas para Cliente 4 (Roberto Sánchez)
        Invoice invoice6 = Invoice.builder()
                .description("Servidores Cloud - Mensual")
                .baseAmount(450.00)
                .tax(58.50)
                .totalAmount(508.50)
                .issueDate(LocalDate.of(2026, 3, 1))
                .client(client4)
                .build();
        invoiceRepository.save(invoice6);

        Invoice invoice7 = Invoice.builder()
                .description("Dominio y Hosting .com")
                .baseAmount(89.99)
                .tax(11.70)
                .totalAmount(101.69)
                .issueDate(LocalDate.of(2026, 3, 18))
                .client(client4)
                .build();
        invoiceRepository.save(invoice7);

        // Factura para Cliente 5 (Martha Rodríguez)
        Invoice invoice8 = Invoice.builder()
                .description("Smartphone Samsung Galaxy")
                .baseAmount(650.00)
                .tax(84.50)
                .totalAmount(734.50)
                .issueDate(LocalDate.of(2026, 3, 22))
                .client(client5)
                .build();
        invoiceRepository.save(invoice8);

        System.out.println("\n✅ DATOS DE PRUEBA CARGADOS CORRECTAMENTE:");
        System.out.println("   - Clientes: " + clientRepository.count());
        System.out.println("   - Facturas: " + invoiceRepository.count());
        System.out.println();
    }
}