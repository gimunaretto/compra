# CARTÃO - FIAP

## Avaliação Spring
___

* SpringBoot 3.0;
* Java 17;
* Mysql;
* Swagger;
* Gerar números de cartões: fordevs;
  https://www.4devs.com.br/gerador_de_numero_cartao_credito


    * URL: localhost:8083/api
      * GET:
        * /alunos => lista todos os alunos
        * /{id}/ => traz dados do aluno pelo ID;
        * /{id}/extrato => emite extrato de compras do aluno;
      * POST:
        * /{id}/compra => fazer compra
        * /upload => cadastro inicial dos alunos
                     OBS: ao cadastrar, gerar cartão de crédito


___

### DETALHES:

<b>Inicialização do projeto</b> - Abra o terminal dentro na pasta do projeto e execute o comando `docker-compose up`
</br>

<h3 align=center><b>Funcionalidades</b></h3>

<p>
 <b>Crud de Alunos</b>
    <li>post (/alunos) - Cria um aluno</li>
    <li>post (/alunos/upload) - Faz upload do arquivo txt e cria todos os alunos</li>
    <li>post (/alunos/{id}/extrato) - Gera um extrato com as compras do aluno</li>
    <li>get (/alunos) - Retorna uma lista paginada com todos os alunos, com as suas respectivas compras</li>
    <li>get (/alunos/{id}) - Retorna o aluno pelo id (nome ou limite do cartão)</li>
    <li>put (/alunos/{id}) - Atualiza o aluno pelo id</li>
    <li>delete (/alunos/{id}) - Realiza o soft delete do aluno pelo id</li>
</p>

