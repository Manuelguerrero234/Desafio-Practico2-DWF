package sv.edu.udb.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sv.edu.udb.domain.Client;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .name("Juan Perez")
                .email("juan@example.com")
                .document("12345678-9")
                .build();
    }

    @Test
    void shouldSaveClient() {
        Client saved = clientRepository.save(testClient);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Juan Perez");
        assertThat(saved.getEmail()).isEqualTo("juan@example.com");
        assertThat(saved.getDocument()).isEqualTo("12345678-9");
    }

    @Test
    void shouldFindClientById() {
        Client saved = clientRepository.save(testClient);
        Optional<Client> found = clientRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Juan Perez");
    }

    @Test
    void shouldFindClientByDocument() {
        clientRepository.save(testClient);
        Optional<Client> found = clientRepository.findByDocument("12345678-9");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    void shouldFindAllClients() {
        clientRepository.save(testClient);
        Client client2 = Client.builder()
                .name("Maria Lopez")
                .email("maria@example.com")
                .document("87654321-0")
                .build();
        clientRepository.save(client2);

        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(2);
    }

    @Test
    void shouldDeleteClient() {
        Client saved = clientRepository.save(testClient);
        clientRepository.deleteById(saved.getId());
        Optional<Client> found = clientRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void shouldUpdateClient() {
        Client saved = clientRepository.save(testClient);
        saved.setName("Juan Carlos Perez");
        clientRepository.save(saved);

        Optional<Client> updated = clientRepository.findById(saved.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("Juan Carlos Perez");
    }
}