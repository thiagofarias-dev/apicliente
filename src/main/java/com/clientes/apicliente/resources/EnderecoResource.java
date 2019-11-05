package com.clientes.apicliente.resources;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.apicliente.models.Cidade;
import com.clientes.apicliente.models.Endereco;
import com.clientes.apicliente.models.Estado;
import com.clientes.apicliente.repository.EnderecoRepository;

@RestController
@RequestMapping(value="/api")
public class EnderecoResource {

	@Autowired
	EnderecoRepository enderecoRepository;

	@GetMapping(value="endereco/busca/{cep}")
	public ResponseEntity<?> listaEnderecoUnico(@PathVariable(value="cep") String cep) {
		JsonObject enderecoJsonRecebido = getCepResponse(cep);
		Endereco endereco = null;
		JsonValue erro = enderecoJsonRecebido.get("erro");
		if (erro == null) {
			endereco = new Endereco(enderecoJsonRecebido.getString("logradouro"),
					enderecoJsonRecebido.getString("bairro"),
					enderecoJsonRecebido.getString("localidade"),
					enderecoJsonRecebido.getString("uf"),
					enderecoJsonRecebido.getString("cep"));
			return ResponseEntity.ok(endereco);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public JsonObject getCepResponse(String cep) {
		JsonObject enderecoJsonRecebido = null; 
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet("https://viacep.com.br/ws/"+cep+"/json");
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			enderecoJsonRecebido = Json.createReader(entity.getContent()).readObject();
		} catch (Exception e) {
			return enderecoJsonRecebido;
		}
		return enderecoJsonRecebido;
	}

	@GetMapping(value="endereco/busca/estados")
	public ResponseEntity<?> listaEstados() {
		List<Estado> estados = getEstadosLista();
		return ResponseEntity.ok(new Estado().ordenarEstados(estados));
	}

	private static List<Estado> getEstadosLista() {
		JsonArray responseJO = null;
		List<Estado> estados = new ArrayList<Estado>();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			responseJO = Json.createReader(entity.getContent()).readArray();
			for (int i=0; i<responseJO.size();i++) {
				JsonObject properties =  responseJO.getJsonObject(i);
				estados.add(new Estado(properties.getInt("id"),
						properties.getString("sigla"),
						properties.getString("nome")));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return estados;
	}

	@GetMapping(value="/endereco/busca/cidades/{id}")
	public ResponseEntity<?> listaCidades(@PathVariable(value="id") int id) {
		List<Cidade> cidades = getCidadesLista(id);
		if (!cidades.isEmpty()) {
			return ResponseEntity.ok(cidades);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private static List<Cidade> getCidadesLista(int id) {
		JsonArray responseJO = null;
		List<Cidade> cidades = new ArrayList<Cidade>();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+id+"/municipios/");
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			responseJO = Json.createReader(entity.getContent()).readArray();
			for (int i=0; i<responseJO.size();i++) {
				// raiz
				JsonObject properties =  responseJO.getJsonObject(i);
				int identificador = properties.getInt("id");
				String nome = properties.getString("nome");						
				// microrregiao
				JsonObject objectMicrorregiao = properties.getJsonObject("microrregiao");
				// mesorregiao
				JsonObject objectMesorregiao = objectMicrorregiao.getJsonObject("mesorregiao");
				// mesorregiao
				JsonObject objectUF = objectMesorregiao.getJsonObject("UF");
				String sigla = objectUF.getString("sigla");				
				cidades.add(new Cidade(identificador, nome, sigla));			
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cidades;
	}
	// WIP - Erro ao executar a query no DB
	@PostMapping(value="/endereco/editar")
	public Endereco atualizaEndereco(@RequestBody Endereco endereco) {
		return enderecoRepository.updateEndereco(endereco);
	}	
}


