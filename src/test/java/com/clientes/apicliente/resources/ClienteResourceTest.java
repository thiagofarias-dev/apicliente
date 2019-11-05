package com.clientes.apicliente.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.clientes.apicliente.models.Cliente;
import com.clientes.apicliente.models.Endereco;
import com.clientes.apicliente.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteResourceTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private ClienteRepository clienteRepository;
	private Cliente cliente;
	
	@Test
	public void testaConsultaClientePorCpf() {
		cliente = new Cliente(1L,"Obi-Wan Kenobi","12564832598", new Endereco(1L, "Alameda Galatica", 420L, "Jardim Imperial", "Suzano", "SP", "08615000"));
		BDDMockito.when(clienteRepository.findByCpf("12564832598")).thenReturn(cliente);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/cliente/busca/12564832598", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isNotEmpty();
	}

	@Test
	public void testaClientes() {
		List<Cliente> clientesLista = new ArrayList<Cliente>();
		clientesLista.add(new Cliente(1L,"Obi-Wan Kenobi","12564832598", new Endereco(1L, "Alameda Galatica", 420L, "Jardim Imperial", "Suzano", "SP", "08615000")));
		clientesLista.add(new Cliente(2L,"Luminara Unduli","42526854812", new Endereco(2L, "Avenida Europa", 60L, "Parque das nacoes", "Suzano", "SP", "11552244")));
		BDDMockito.when(clienteRepository.findAll()).thenReturn(clientesLista);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isNotEmpty();
	}
}
