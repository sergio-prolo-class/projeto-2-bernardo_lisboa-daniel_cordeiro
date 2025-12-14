# Funcionalidades implementadas

## Sistema de personagens

**Aldeão:** Personagem básico sem habilidades de combate, com sistema de montaria e coleta

**Arqueiro:** Unidade de ataque à distância com sistema de flechas e sistema de coleta

**Cavaleiro:** Unidade corpo a corpo com sistema de montaria

## Sistema de combate

Cada personagem causa dano baseado em seu atributo de ataque

## Morte e remoção:

Personagens com vida zero são removidos do jogo automaticamente

## Alcance de ataque variável:

Cavaleiro: 75 pixels (ataque corpo a corpo)

Arqueiro: 150 pixels (ataque à distância)

## Sistema de esquiva:

Cada personagem tem chance de esquivar ataques baseado em seu atributo de esquiva

## Filtros por tipo

Radio buttons implementados para filtrar ações por personagem, podendo escolher entre:

+ Todos
+ Aldeão
+ Arqueiro
+ Cavaleiro

## Controle de montaria (Cavaleiro e Aldeão)

Alternar entre estado montado e desmontado

Velocidade ajustável conforme o estado

Sprite visual diferente para cada estado

## Atalhos para teclado

Teclado pode executar todas as ações que antes seriam apenas por botões, através do KeyListener, como o de se movimentar, atacar, desmontar e alterar os filtros

## Sistema de vida:

Personagens possuem vida máxima e atual, com atualização em tempo real

Possuem uma barra superior que indica a vida e muda de cor quando atinge porcentagens especificas:

+ verde (75-100%)
+ amarelo (25-75%)
+ vermelho (<25%)

<br>

# Como executar o projeto

## Pré-requisitos

1. JDK 8 ou superior instalado

2. Gradle instalado

**No terminal:**
```
./gradlew run
```

<br>

# Como jogar

## Controles
```
WASD - Movimentação

M - Alterar montaria

Tab - Alterar personagem que controla

Espaço - Atacar
```
<br>

# Decisões de design importantes

## Sistema de contagem de flechas

Ficava muito estranho utilizar o arqueiro e não ver quantas flechas ele tinha restantes. Portanto, foi implementada uma pequena contagem logo acima da barra de vida, que a cada vez que um ataque é feito, ela irá diminuir em 1

## Sprites diferentes para cada ação

Possui um sprite de personagem para cada ação que ele irá fazer: 
+ Para ataque
+ Para montado
+ Para montado e ataque

## Métodos de fábrica simples para criação

Métodos criarAldeao(), criarArqueiro(), criarCavaleiro() visando o encapsulamento da lógica de criação e buscando como resultado um código cliente mais limpo
