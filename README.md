O cenário escolhido (Produto/Categoria).<br>
O relacionamento implementado (ManyToOne/OneToMany).<br>
A estrutura de pacotes (model, repository, controller).<br>
Banco de dados (MariaDB/XAMPP, db_produtos_categorias).<br>
<br>
Aplicação (mvn spring-boot:run).<br>
Use o Postman ou Bruno para testar as operações CRUD:<br>
Criar Categoria: POST http://localhost:8080/api/categorias com JSON no corpo: {"nome": "Eletrônicos", "descricao": "Dispositivos eletrônicos"}<br>
Listar Categorias: GET http://localhost:8080/api/categorias<br>
Criar Produto: POST http://localhost:8080/api/produtos com JSON no corpo, referenciando o ID da categoria criada (ex: ID 1): {"nome": "Smartphone XPTO", "descricao": "Modelo avançado", "preco": 1999.99, "categoria": {"id": 1}}<br>
Listar Produtos: GET http://localhost:8080/api/produtos<br>
Listar Produtos por Categoria: GET http://localhost:8080/api/produtos/por-categoria/1<br>
Buscar Produto por ID: GET http://localhost:8080/api/produtos/1<br>
Atualizar Produto: PUT http://localhost:8080/api/produtos/1 com JSON no corpo: {"nome": "Smartphone XPTO Plus", "descricao": "Modelo atualizado", "preco": 2199.00, "categoria": {"id": 1}}<br>
Deletar Produto: DELETE http://localhost:8080/api/produtos/1<br>
Deletar Categoria: DELETE http://localhost:8080/api/categorias/1 (Isso também deletará os produtos associados devido ao CascadeType.ALL).
 
