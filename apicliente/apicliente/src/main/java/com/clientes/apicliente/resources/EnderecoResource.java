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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.apicliente.models.Cidade;
import com.clientes.apicliente.models.Endereco;
import com.clientes.apicliente.models.Estado;
import com.clientes.apicliente.repository.EnderecoRepository;

@RestController
@RequestMapping(value="/api2")
public class EnderecoResource {

	@Autowired
	EnderecoRepository enderecoRepository;
	//BUSCA CEP INICIO
	@GetMapping(value="endereco/busca/{cep}")
	public Endereco listaEnderecoUnico(@PathVariable(value="cep") String cep) {

		JsonObject jsonObject = getCepResponse(cep);
		Endereco endereco = null;

		JsonValue erro = jsonObject.get("erro");

		if (erro == null) {

			endereco = new Endereco(jsonObject.getString("logradouro"),
					jsonObject.getString("bairro"),
					jsonObject.getString("localidade"),
					jsonObject.getString("uf"),
					jsonObject.getString("cep"));

		}
		return endereco;
	}

	private static JsonObject getCepResponse(String cep) {

		JsonObject responseJO = null;

		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			System.out.println("AQUI2");
			HttpGet httpGet = new HttpGet("https://viacep.com.br/ws/"+cep+"/json");
			System.out.println("AQUI3");
			HttpResponse response = httpclient.execute(httpGet);
			System.out.println("AQUI4");
			HttpEntity entity = response.getEntity();
			System.out.println("AQUI5");
			System.out.println(cep);
			responseJO = Json.createReader(entity.getContent()).readObject();
			System.out.println("AQUI6");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return responseJO;
	}
	//BUSCA CEP FIM


	//LISTA ESTADOS INICIO
	@GetMapping(value="endereco/busca/estados")
	public List<Estado> listaEstados() {
		List<Estado> estados = getEstadosLista();

		return new Estado().ordenarEstados(estados);
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
	//LISTA ESTADOS FIM


	@GetMapping(value="/endereco/busca/cidades/{id}")
	public List<Cidade> listaCidades(@PathVariable(value="id") int id) {
		return getCidadesLista(id);
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
				System.err.println("AQUI4");

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









	public Endereco atualizaEndereco(Endereco endereco) {
		return enderecoRepository.saveAndFlush(endereco);

	}	



}


