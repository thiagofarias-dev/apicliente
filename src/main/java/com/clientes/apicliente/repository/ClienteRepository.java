package com.clientes.apicliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.apicliente.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findById(long id);
	Cliente findByCpf(String cpf);
}
