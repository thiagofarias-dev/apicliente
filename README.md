# apicliente
 Teste BancoPan

### ROTEIRO PARA USO DOS ENDPOINT

Ferramenta utilizada: Postman Version 7.10.0

Cenário 1 - Consultar Cliente url: localhost:8080/api/cliente/busca/{cpf} method: GET Variável {cpf} é um número de 14 digítos.
Cenário 2 - Consultar CEP url: localhost:8080/api2/endereco/busca/{cep} method: GET Variável {cep} é um número de 8 digítos.
Cenário 3 – Consultar Estados url: localhost:8080/api/endereco/busca/estados/ method: GET
Cenário 4 – Consultar Municípios url: localhost:8080/api2/endereco/busca/cidades/{uf} method: GET Variável {uf} é um número de 2 digítos, pode ser consultado por um dos codigos do resultado do Cenário 3, ou 35 para o estado de São Paulo
Cenário 5 – Alterar endereço - Não funcional

Os dois CPFs abaixo estão gravados no banco e podem ser usados no Cenário 1: 12345678901 10987654321
