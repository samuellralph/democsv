# democsv

Recebe as informações de contas em um arquivo CSV
Processa as informações em arquivo de serviço (fake), para simular um serviço externo e retorna ums status de sucesso
Adiciona uma nova coluna com status de sucesso para cada dado aos dados do CSV
Exporta as informações do CSV para um local

# GUI
Interface de interação utilizando JAVA SWING para importar e exportar o arquivo.

# CSV
Bibliotecas OpenCSV para leitura do arquivo.
Bibliotecas JAVA para exportar.

# Requisitos para executar a aplicação
Java 11 SDK
Maven

# Executar a aplicação
Na raiz do projeto executar o comando para empacotar a aplicação
 - mvn package

Na raiz do projeto executar o comando para carregar a aplicação
CMD
java -jar target\democsv-0.0.1-SNAPSHOT.jar

Shell
java -jar .\target\democsv-0.0.1-SNAPSHOT.jar

Bash
java -jar target/democsv-0.0.1-SNAPSHOT.jar

