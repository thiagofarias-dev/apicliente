package com.clientes.apicliente.resources;

import static org.assertj.core.api.Assertions.assertThat;

import javax.json.Json;
import javax.json.JsonObject;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnderecoResourceTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private EnderecoResource enderecoResource;

	@Test
	public void testaListaEnderecoUnico() throws JSONException  {
		JsonObject enderecoJson = Json.createObjectBuilder().add("cod","0")
				.add("logradouro", "Rua Tomisaburo Urano").add("numero", "null").add("bairro", "Vila Figueira").add("cidade", "Suzano").add("uf", "SP").add("cep", "08676-230").build();
		BDDMockito.when(enderecoResource.getCepResponse("08676230")).thenReturn((JsonObject) enderecoJson);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/endereco/busca/{id}", String.class, "08676231");
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
}


