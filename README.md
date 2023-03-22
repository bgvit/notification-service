# notification-context


## Objetivo
Esse projeto exercita a criação de uma arquitetura escalável. Para isso, foi colocado o esforço em contexto de envio de notificações para usuários.

- Para rodá-lo use o comando: `docker compose up -d`.


# Detalhes
Como o desafio tinha o foco em escalabilidade e resiliência, montei dois microserviços com responsabilidades diferentes:

### notification-service(está funcional, porém, estou encontrando problemas ao rodá-lo com todo o ambiente no `docker compose` e ainda não achei a solução. O microserviço eventualmente para de conectar às dependências externas apesar de estar usando corretamente a rede interna do docker.). Caso você não tenha esse problema, ele praticamente está pronto, exceto pela falta de implementação do consumo do tópico do RedPanda.

    Esse microserviço, investi muito esforço de qualidade nele. Ele é responsável por adicionar as notificações no banco e enviá-las aos usuários (conforme o tipo).

    - Foi implementado um tipo de notificação de um serviço chamado `ntfy` que "permite" o envio de notificações para celular, computadores conforme vc se increveu em um dos seus tópicos. Mais informações: (ntfy.sh)[https://ntfy.sh]. O serviço consegue já consegue enviar esse tipo de notificação.

    - Implementado um serviço fake para envio de push normal.

    - Após uma notificação ser enviada, o cache irá criar uma "trava" de 2 minutos para que não seja enviada uma notificação duplicada, se por ventura der algum problema.

    - Adição no banco de dados de notificações que foram agendadas e outras a serem enviadas.

### notification-watch (não está funcional):

É uma "espécie" de cron (só que não "morre"). Ele irá rastrear por notificações que não foram enviadas de tempos e tempos e enviar para o tópico para ser consumido pelo `notification-service`. Não está completo e está com bugs, mas já demonstra a ideia arquitetural "da coisa".


- Pontos a melhorar:
    - Ambos os microserviços carecem de testes automatizados e enriquecimento de logs;
    - Seria interessante adicionar um mecanismos de `migration`de banco de dados como `liquibase`ou `flyway`
    - O projeto não é a ideal. Os próximos passos desse projeto serão para concretizar a arquitetura pensada originalmente mas que não foi possível desenvolver no tempo previsto (colocarei a imagem da arquitetura pensada aqui logo após enviar esse projeto).

## Componentes
    - RedPanda: É uma implementação de Kafka escrita em C++ e que já é `production ready`no uso da nova arquitetura que dispensa um container de `schema registry`que um algoritmo chamado `Raft`de consenso para sincronizar os dados, algo assim.

    - Redis: Ferramenta de cache

    - PostgreSQL: Banco de dados para guardar as notificações e contas de usuário.

    - Notification-service

    - Notification-watch


# Id de contas:

    - Cada notificação precisa estar ligada à um `accountId`, como não deu para completar o CRUD em tempo de `account`disponibilizo uma lista de contas:

```
    {
        {
"account": [
	{
		"id" : "2NJ14ffd3iToJlzpcgqEf6D31ff",
		"account_name" : "Bernardo",
		"created_on" : "2023-03-22T16:20:27.572Z",
		"updated_on" : null
	},
	{
		"id" : "2NJ14d4P9F33AgkN4FlSV4q9sgD",
		"account_name" : "Daniel",
		"created_on" : "2023-03-22T16:20:27.573Z",
		"updated_on" : null
	},
	{
		"id" : "2NJ14bt7YN8YnpUrTbXqgwsnwrk",
		"account_name" : "Nilson",
		"created_on" : "2023-03-22T16:20:27.574Z",
		"updated_on" : null
	},
	{
		"id" : "2NJ14dXPE6kb4jRhjDQB1IwJD47",
		"account_name" : "Diego",
		"created_on" : "2023-03-22T16:20:27.575Z",
		"updated_on" : null
	},
	{
		"id" : "2NJ14aovE6y0z5a23MnefFEkJrA",
		"account_name" : "Marcus",
		"created_on" : "2023-03-22T16:20:27.575Z",
		"updated_on" : null
	}
}
```

    }


Em que ponto chegou(para abrir o diagrama, só ir no site (excalidraw.com/)[excalidraw.com/]:

![Como está](https://user-images.githubusercontent.com/51809748/227026931-1464a7ee-51f2-4604-97ce-00d433da61d3.svg)


Os próximos passos se darão até ele chegar no ponto abaixo:

