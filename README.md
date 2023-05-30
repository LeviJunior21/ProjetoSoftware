# Pits A

Recentemente, diversas empresas do ramo alimentício têm se desvinculado dos grandes aplicativos de delivery. As causas dessa tendência são diversas e vão desde a transformação no modo de operação de cada estabelecimento, até as taxas abusivas das grandes plataformas.
Porém, em 2023, simplesmente não é viável voltar ao modo de trabalho “pré-Ifood”... Foi por isso que a pizzaria Pits A decidiu desenvolver seu próprio aplicativo de delivery. E adivinha só… vocês foram escolhidos para ajudar!


## User Stories já implementadas

- Eu, enquanto administrador do sistema , quero utilizar o sistema para criar,  editar e remover um estabelecimento;
- Eu, enquanto cliente, quero utilizar o sistema para me cadastrar como cliente do sistema. Mais detalhadamente, deve ser possível criar, ler, editar e remover clientes;
- Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me cadastrar como entregador(a) do sistema. Mais detalhadamente, deve ser possível criar, ler, editar e remover entregadores;
- Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me associar como entregador(a) de um estabelecimento;
- Eu, enquanto estabelecimento, quero utilizar o sistema para aprovar ou rejeitar entregadores do estabelecimento;
- Eu, enquanto estabelecimento, quero utilizar o sistema para o CRUD dos sabores de pizza vendidos pelo estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover sabores;
- Eu, enquanto cliente, quero visualizar o cardápio de um estabelecimento;
- Eu, enquanto cliente, quero utilizar o sistema para fazer pedidos de pizza a um  estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover pedidos.
- Eu, enquanto cliente, quero demonstrar interesse em sabores de pizza que não estão disponíveis no momento.
- Eu, enquanto estabelecimento, quero disponibilizar diferentes meios de pagamento para os pedidos, tal que cada meio de pagamento também gere descontos distintos.
## Estrutura básica

- Um projeto: PitsA;
- Controllers que implementam os endpoints da API Rest (VersionController, ClientesV1Controller, ProdutoV1Controller, FuncionarioV1Controller e EstabelecimentoV1Controller).
- Quatro repositórios são utilizados: ClienteRepository, ProdutoRepository e FuncionarioRepository e EstabelecimentoRepository, que são responsáveis por manipular as entidades Cliente, Produto, Estabelecimento e Funcionario em um banco de dados em memória;
- O modelo é composto pelas classes Cliente.java, Produto.java, Entregador.java, Estabelecimento.java e Funcionario.java, que podem ser
  encontradas no pacote model;
- O pacote exceptions guarda as classes de exceções que podem ser levantadas
  dentro do sistema;
- Não há implementação de frontend, mas o projeto fornece uma interface de acesso à API via swagger.

## Tecnologias
Código base gerado via [start.sprint.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.3.3.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.example&artifactId=EstoqueFacil&name=EstoqueFacil&description=Projeto%20Estoque%20Facil&packageName=com.example.EstoqueFacil&dependencies=web,actuator,devtools,data-jpa,h2) com as seguintes dependências:

- Spring Web
- Spring Actuator
- Spring Boot DevTools
- Spring Data JPA
- H2 Database
- Cucumber

## Endereços úteis

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/h2](http://localhost:8080/h2)

## Testes

<code>> mvn clean test </code>



## Contato e Dúvidas

- lucas.pereira@ccc.ufcg.edu.br
- levi.pereira.junior@ccc.ufcg.edu.br
- victor.verissimo@ccc.ufcg.edu.br
- vimerson.silva@ccc.ufcg.edu.br


