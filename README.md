# Luiza Labs Android Test

Criar um aplicativo para consultar a API do GitHub e trazer os repositórios mais populares de Java.

## Desafio
O desafio se consiste em codificar duas telas mostrando os repositórios e seus respectivos pull requests exibindo algumas informações iniciais.

#### Primeira tela
Aqui é onde os repositórios são exibidos devendo conter o nome do repositório, descrição do repositório, nome e imagem do autor, número de stars e de fork

#### Segunda tela
Ao clicar em um repositório na primera tela o usuário é levado para esta que exibe os pull requests devendo exibir nome e foto do autor do PR, título do PR, data e body do PR.

## Abordagem

#### Layout
O inicio do desenvolvimento partiu do layout, com ConstraintLayout e RecyclerView para ambas telas inflando os itens por um Adapter.

#### API
Para as requisições foi utilizado um JSON local afim de simular a real requisição tendo em vista que sofro com perda de pacotes e uma conexão instável (mais sobre isso na seção **Problemas Enfrentados**), mantendo as *models* de forma com que cada objeto / dado fosse referenciado via *key name*. Foi feito um fetch geral de todo o o JSON afim de trazer os dados e ir carregando ao longo do scroll (pelo menos essa foi a ideia inicial).

#### Testes
A abordagem com os testes foi um tanto quanto simplista, buscando testar as urls da API apenas. Sem testes de UI.

#### Geral
Por fim, com cada adapter/model e uma *request* para repositórios e outra para PR's pude conectar tudo as *Activities* afim de carregar as informações para o usuário, deixando um *loading spinner* demonstrando um carregamento.

## Problemas Enfrentados

#### API
Todo o desenvolvimento foi feito a partir de uma cópia do JSON local, deixando a conexão e *fetch* real para uma das últimas etapas. Foi nesse momento onde enfrentei (no meu ver) o principal problema (mais sobre isso na seção ***Troubleshooting***).

#### *API rate limit exceeded*
O erro que da nome para esta seção é o que tornou dificil os ajustes finais para a finalização do teste. O GitHub limita sua API para um número limitado de requisição por IP e como o App ainda não estava tratando de carregar progressivamente os blocos de requisicão, em apenas **UMA** *request* esse limite é excedido. Caso venha a executar o App uma segunda vez (gerando uma nova request) o App sofre um *crash* impedindo de abrir ele novamente.

##### Mais informações sobre o *API rate limit exceeded*
[Requests from personal accounts](https://docs.github.com/pt/rest/overview/resources-in-the-rest-api#rate-limiting)
[Automatic token authentication](https://docs.github.com/pt/actions/security-guides/automatic-token-authentication)

## *Troubleshooting*

#### Para tentar contonar o erro *API rate limit exceeded*
- Acesse o **GitHub**
- Procure no **canto superior direito** seu usuário e abra o menu
- Com o menu aberto procure por ***Settings*** e clique nele
- No menu lateral esquerdo procure por ***Personal access tokens*** e clique em ***Tokens (classic)***
- Agora clique em ***Generate new token***
- Dê um nome ao token
- Copie o token e cole no arquivo ***GeneralRequests***
	 >val token = "Bearer seu-token-aqui"

##### Para inserir o Token no *local.properties*
- *local.properties* é um arquivo que não está no versionamento (Git), pode ser que você precise criar, geralmente o Android Studio cria assim que o projeto é criado
- Com o arquivo aberto insira a linha abaixo
	>API_TOKEN="Bearer seu-token-aqui"
	>Exemplo API_TOKEN="Bearer ghp_hash-aqui"
- Após isso, retorne ao arquivo ***GeneralRequests*** e insira o seguinte
	> val token = BuildConfig.API_TOKEN;

Pronto, agora você pode verificar com o token e a URL API e checar quantas requisições faltam para atingir o limite. Isso pode ser feito usando o seguinte comando, lembrando sempre de usar o mesmo token.
>curl \\
>-H "Accept: application/vnd.github+json" \\
>-H "Authorization: Bearer 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" \\
https://api.github.com/rate_limit

O retorno é um JSON e entre os dados deve se parecer com o seguinte:
>"rate": {
>	"limit": 5000,
>	"used": 120,
>	"remaining": 4880,
>	"reset": 1668872070
>}

## Futuras melhorias

#### API
Certamente esse seria a área de atuação onde este app mais precisa de melhorias. Se assegurar de fazer as requisições em blocos no instante em que o usuário se aproxima do final do scroll seria o cenário ideal.

#### UI
Componentizar e atualizar para Jetpack Compose poderia trazer beneficios de visual quanto a possibilidade de escalabilidade em relação a possiveis animações que o time de UX/UI possa ter em vista.

#### Testes
Aprimorar os atuais testes de *requests* e implementar testes de UI para que possa abranger o maior numero de dispositivos possiveis e evitando possiveis falhas visuais e tambem ajudando no processo do time de testes.

## Considerações Finais
Apesar da constante falha apresentada principalmente por parte da API gostaria de deixar os agradecimentos e aprendizados (*vide* API do GitHub) para a pessoa idealizadora desse teste e dizer tambem que tudo apresentado foi feito num intervalo de tempo de **5 dias** (segunda a sexta) em média **2h30/dia** sendo uma **meia *sprint*** onde as futuras melhorias certamente poderiam ser atingidas em um cenário mais real em relação ao *full time job* e com um compartilhamento de conhecimento entre diversos dev's.

Meu muito obrigado!
