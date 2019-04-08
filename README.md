# Descrição

Projeto de uma API REST responsável por gerenciar hóspedes e checkins de um hotel.

Endpoints disponíveis:

* /checkins (GET, POST, PUT, DELETE)
* /pessoas (GET, POST, PUT, DELETE)

## Regras
* Uma diária no hotel de segunda à sexta custa R$120,00;
*  Uma diária no hotel em finais de semana custa R$150,00;
* Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo
diário, sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
* Caso o horário da saída seja após às 16h deve ser cobrada uma diária extra;


# Como executar

1) Clonar o projeto em sua máquina e abrir na IDE (Eclipse ou STS) com SpringBoot configurado.

2) Criar um banco de dados 'reservas' e definir usuário e senha em application.propreties

3) Rodar aplicação como SpringBootApp

# API-reservas
