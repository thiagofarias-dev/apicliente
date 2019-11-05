package com.clientes.apicliente.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	//Busca todos os Clientes
	@GetMapping("/clientes")
	public List<Cliente> listaCliente(){
		return clienteRepository.findAll();
		
	}
	//Busca o Cliente pelo ID
	@GetMapping("/cliente/{id}")
	public Cliente listaClienteUnico(@PathVariable(value="id") long id){
		return clienteRepository.findById(id);
		
	}
	
	//Busca o Cliente pelo CPF
	@GetMapping("/cliente/busca/{cpf}")
	public Cliente listaClienteUnico(@PathVariable(value="cpf") String cpf){
		return clienteRepository.findByCpf(cpf);
		
	}
	
	
	//Atualiza CLiente e endereco
	@GetMapping("/cliente/Atualiza/{cpf},{cep}")
	public Cliente atualizaClienteUnico(@PathVariable(value="cpf") String cpf, @PathVariable(value="cep") String cep){
		
		Cliente cliente = clienteRepository.findByCpf(cpf);
		Endereco endereco = new EnderecoResource().listaEnderecoUnico(cep);
		System.out.println("AQUI1");
		endereco.setCod(cliente.getEndereco().getCod());
			
		cliente.setEndereco(endereco);
			
		return clienteRepository.saveAndFlush(cliente);
	}
	
	
	
	@PostMapping("/cliente")
	public Cliente salvaCliente(@RequestBody Cliente cliente/*, @RequestBody Endereco endereco*/) {
		//Endereco endRes = new EnderecoResource().salvaEndereco(endereco);
		//System.out.println(endereco.getLogradouro());
		//cliente.setEndereco(endRes);
		return clienteRepository.saveAndFlush(cliente);
	}
	
}
