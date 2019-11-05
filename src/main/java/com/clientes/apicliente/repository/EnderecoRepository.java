package com.clientes.apicliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clientes.apicliente.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByCep(String cep);

	@Query(value="update TB_ENDERECO set bairro = :endereco.bairro, cep = :endereco.cep, cidade = :endereco.cidade, logradouro = :endereco.logradouro, numero = :endereco.numero, uf = :endereco.uf where cod = :endereco.cod", nativeQuery=true)
	Endereco updateEndereco(@Param("endereco") Endereco endereco);
}
