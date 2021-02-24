# Desafio_API
Este desafio foi elaborado com o intuito de comprovar as habilidades do autor em determinadas tecnologias para o exercício
de função no desenvolvimento de aplicações back-end java.

## Descricao do projeto
* O projeto é uma API rest com um recurso, Group, e um subrecurso, User.
* A API foi feita na linguagem Java com o springboot utilizando os starters data-JPA e Web.
* A aplicação executa em um container Docker junto com outro container contendo o banco de dados da aplicação. 
* Os containers são configurados para trabalhar juntos utilizando o Docker compose.

Tabela de Conteúdos
=================
<!--ts-->
* [Tecnologias](#tecnologias)
* [Como Usar](#como-usar)
    * [Pre-requisitos](#pre-requisitos)
    * [Executando](#executando)
* [Documentacao Interativa](#documentacao-Interativa)
* [Acesso aos Recursos](#acesso-aos-recursos)
* [Arquitetura da API](#arquitetura-da-api)
  * [Camadas](#camadas)
  * [Tratador de Excecoes](#tratador-de-excecoes)
<!--te-->

---
### Tecnologias
As seguintes Tecnologias foram Utilizadas nesse Projeto:
* [JDK 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
* [SpringBoot 2.4.2](https://start.spring.io/) (Starters abaixo)
  * Data JPA (Utiliza o Hibernate)
  * Web
* [MySQL 5.6](https://www.mysql.com/)
* [Docker 19.03.13](https://www.docker.com/)
* [Docker Compose 1.21.2](https://docs.docker.com/compose/)
* [Swagger 2.9.2](https://swagger.io/)
* [Maven 3.6.1](https://maven.apache.org/)

---
### Como Usar

#### Pre-requisitos
Antes de começar, precisa-se ter instalado na própria máquina as
seguintes aplicações:
* [Docker ( > = 19.03.x)](https://docs.docker.com/get-docker/)
* [Docker-Compose ( > = 1.21.x)](https://docs.docker.com/compose/install/)

#### Executando
Não é necessário fazer o build da aplicação java para utilizar a API, pois,
neste repositório do github, a pasta target está com o arquivo jar da aplicação incluida.

```bash
#Clone este repositório
$ git clone https://github.com/murilofarias/Desafio_API

# Acesse a pasta do projeto no terminal/cmd
$ cd Desafio_API

#Utilize o docker-compose para construir a image do Dockerfile e
#executar o script docker-compose.yaml.
#Pode ser necessário de mais permissões no SO para realizar esse comando. 
#Utilize sudo no início do comando caso precise
$ sudo docker-compose up --build --force-recreate
#A API iniciará com a url base <http://localhost:8080/api/> 
```

---
### Documentacao Interativa
Com a API executando, acesse a url <http://localhost:8080/api/swagger-ui.html> através de um
navegador para poder ler a documentação e interagir com a API.

---
### Acesso aos Recursos
Há um recurso Group e um subrecurso de Group que é User. Um User pertence a um Group e
um Group pode conter vários Users. Todos os métodos retornam ou/e recebem apenas conteúdos 
do tipo application/json no corpo da requisição/resposta. Abaixo há uma breve descrição do acesso a cada recurso.

* Group:
  * url base: <http://localhost:8080/api/groups>
  * Métodos aceitos: GET, POST, PUT, DELETE
  * Atributos:
    * id : UUID (Gerado automaticamente pela API)
    * name : String (Obrigatório)
    * createdAt : DATE (Gerado automaticamente pela API)
    * users : List\<User> (Opcional)
  
* User:
  * url base: <http://localhost:8080/api/groups/{idGroup}/users>
  * Métodos aceitos: GET, POST, PUT, DELETE
  * Atributos:
    * id: UUID (Gerado automaticamente pela API)
    * name: String (Obrigatório)
    * phone : String (Opcional)
    * createdAt: DATE (Gerado automaticamente pela API)
    * group : Group (Obrigatório. O User só existe dentro de um Group)
  
**Mais informações sobre os métodos podem ser encontradas na documentação gerada
pelo Swagger.**

---
### Arquitetura da API
A arquitetura é baseada em 4 camadas e um tratador de exceções. A camada adjacente mais  profunda serve de interface
para a camada superficial.( Ex: Rest Controller utiliza as funções providenciadas pela camada Service. Service 
utiliza as funções expostas pela Data access Object)

#### Camadas

1. **Rest Controller** :
  Responsável por encaminhar as requisições http aos devidos métodos da aplicação Java.
  
  
2. **Service**:
  Camada responsável por implementar as regras de negócio da API
  
  
3. **Data access Object (dao)**:
  Acessa o Banco de dados levando em conta os dados definidos em Domain Model para contruir e armazenar objetos java a 
  partir da estrutura das entidades no banco de dados. Esta camada é também chamada de **Repository** pela sua relação
  com o banco de dados.
  
  
4. **Domain Model (domain)**:
  Camada responsável por definir os tipos dos atributos das entidades, as restrições, os relacionamentos com outras 
  entidades e a forma como os objetos Java vão ser armazenados e recuperados no banco de dados.
   
#### Tratador de Excecoes
 Quando uma das 4 camadas da API lança uma Exceção, Esta parte é responsável por tratar a exceção formatando uma 
 resposta mais significativa do erro para o usuário e fazendo com que a aplicação não precise ser reiniciada para 
 voltar a operação. Abaixo há o nome das exceções, sejam elas criadas no projeto ou contextualizadas nele, que são 
 tratadas:

* **idNaoValidoServiceException**:
  Exceção lançada quando o id, dado como parametro na requisição de um recurso ou 
  subrecurso, não tem o formato UUID. Essa validação acontece na camada service, por isso este nome.
  

* **org.hibernate.exception.ConstraintViolationException.**:
  Lançada pelo Hibernate quando uma restrição do banco de dados é violada. No caso desta aplicação, não vi uso
  para ela, porém, na dúvida, inclui ela para tratar qualquer restrição implícita que possa ter.
  
  
* **org.hibernate.PersistentObjectException**:
  Esta Exceção é lançada pelo Hibernate e, no contexto desta API, vai ser lançada quando for fornecido um id para um
  recurso e o Hibernate for salvar este atributo no banco de dados da entidade subjacente. Como o id é gerado 
  automaticamente pelo banco de dados, há um tipo de conflito. Esta exceção vai ser lançada quando o usuário fizer
  um POST, para algum dos recursos, passando um valor para o id.
  
  
* **org.hibernate.PropertyValueException**:
  Outra Exceção do Hibernate que, no contexto desta aplicação, servirá para tratar o caso quando um atributo não puder 
  ser salvo no banco de dados com um valor nulo.
  
  
* **NaoExisteDaoException**:
  Exceção lançada a nível da camada DAO quando um recurso não é encontrado no banco de dados.
  
  
* **NullPointerException e IllegalArgumentException**:
  Estas duas são exceções tratadas de forma genérica pelo tratador de Exceções para cobrir possibilidades de uso.
  


##### - A classe DetalheErro
Esta classe é definida em domain e serve para o tratador de Exceções construir e retornar uma resposta em 
formato application/json mais informativa e padronizada.






  
  




