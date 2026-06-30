# DOCUMENTO DE ELICITAÇÃO DE REQUISITOS - ArenaPay

## 1. Visão geral do sistema

``` 
O ArenaPay é uma plataforma de gerenciamento de torneios e desafios baseados 
em habilidade para e-sport. O sistema atua como um intermediário seguro, garantindo
a retenção financeira das apostase a distribuição automatizada  de prêmios aos vencedores
via integração com APIs de PIX, mitingado fraudes e calotes entre jogadores.
```

## 2. Requisitos Funcionais (RF)

- [ ] **Cadastro de usuários**
    - *Descrição:* O sistema deve permitir o cadastro de usuários informando
      nome, email, endereço e cpf.
    - *Critério de Aceite:* O Email e cpf devem ser únicos. A senha deve ser armazenada com criptografia.
- [ ] **Autenticação de usuários**
    - *Descrição*: O usuário deve efetuar seu login utilizando email e senha.,
    - *Critério de Aceite:* A senha deve ser de no minimo 8 digitos.
- [x] **Cadastro de cidades**
    - *Descrição:* O sistema deve permitir o cadastro e atualização de cidades vinculadas a um estado cadastrado.
    - *Critério de Aceite:* Não deve permitir cidades com o mesmo nome (ignorando maiúsculas/minúsculas e acentos) no
      mesmo estado.
