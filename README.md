# netty-jersey-starter

Este projeto busca prover uma estrutura base para projetos Java em ambiente Cloud-AWS.
Sua estrutura se baseia nos componentes da Netflix OSS (https://netflix.github.io/).

Stack do servidor -> Netty -> RxNetty -> Karyon.

Módulos pré-carregador pelo módulo netty-jersey-starter
- Jersey (Rest)
- Servo (métricas, auditorias)
- Shutdown (Desligamento por linha de comando)
- Eureka
