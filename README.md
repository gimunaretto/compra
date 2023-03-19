# CARTÃO - FIAP

## Avaliação Spring
___


<p>
    <h3 align=center><b>Tecnologias do projeto:</b></h3>
    <li>SpringBoot 2.7.9</li>
    <li>Java 11</li>
    <li>Docker - Mysql</li>
    <li>Documentação Swagger</li>
</p>

<h3 align=center><b>Start do Servidor MySql:</b></h3>

    O banco de dados - Mysql - foi instalado em um Docker.
    Para inicializar, abrir o terminal dentro na pasta do projeto
    e executar o comando "docker-compose up".


<h3 align=center><b>Funcionalidades:</b></h3>

<p>
 <b>Crud de Alunos:</b>
    <li>post (/alunos) - Cria um aluno</li>
    <li>post (/alunos/upload) - Faz upload do arquivo txt e cria todos os alunos</li>
    <li>post (/alunos/{id}/extrato) - Gera um extrato com as compras do aluno</li>
    <li>get (/alunos) - Retorna uma lista paginada com todos os alunos, com as suas respectivas compras</li>
    <li>get (/alunos/{id}) - Retorna o aluno pelo id (nome ou limite do cartão)</li>
    <li>put (/alunos/{id}) - Atualiza o aluno pelo id</li>
    <li>delete (/alunos/{id}) - Realiza o soft delete do aluno pelo id</li>
</p>

