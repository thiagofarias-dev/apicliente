package com.clientes.apicliente.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.apicliente.models.Cliente;
import com.clientes.apicliente.models.Endereco;
import com.clientes.apicliente.repository.ClienteRepository;

@RestController
@RequestMapping(value="/api")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;

	@GetMapping("/clientes")
	public List<Cliente> listaCliente(){
		return clienteRepository.findAll();
	}

	@GetMapping("/cliente/{id}")
	public Cliente listaClienteUnico(@PathVariable(value="id") long id){
		return clienteRepository.findById(id);
	}

	@GetMapping("/cliente/busca/{cpf}")
	public ResponseEntity<?> listaClienteUnico(@PathVariable(value="cpf") String cpf){
		Cliente clienteOpt = clienteRepository.findByCpf(cpf);
		if (!clienteOpt.getNome().isEmpty()) {
			return ResponseEntity.ok(clienteOpt);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/cliente")
	public Cliente salvaCliente(@RequestBody Cliente cliente) {
		Endereco endRes = new EnderecoResource().atualizaEndereco(cliente.getEndereco());
		cliente.setEndereco(endRes);
		return clienteRepository.saveAndFlush(cliente);
	}
}
