package com.clientes.apicliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.apicliente.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByCep(String cep);
}
