#rest-assured-automation-barriga

Description
This project was developed with the support of the "Testing API Rest with Rest-Assured" course, from the website Udemy, made by professor Francisco Wagner. This project contains 8 functional test scenarios, from which 3 are positive and 4 are negative, contemplating all of the features provided by the api being tested

Repository Setup
In order to execute the tests stored on this repository, you'll need to execute the project on your machine.
Follow the below steps:

Clone the repository
Open your machine's native terminal, navigate to the cloned project root folder and run the following command to install the required dependencies:

 mvn install -DskipTests
 
After that, you can execute all the tests by running the "RunTests.java" class or by executing the following command:

mvn clean test

Be sure to have the following dependences already installed on your machine:

Java >= 8
Maven >= 3.1+
Allure-Report (optional report generation)

About the tests implemented on this repository
This repository contains the following tests:

Feature	Scenario	Description
PositiveScenarios	Create an account and insert balance into it	Verifica se o CPF informado possui alguma restricao.
PositiveScenarios	Edit a previously created account's name	Realiza a consulta de um CPF inexistente na lista de restricoes.
PositiveScenarios	Insert and remove banking movement from account	Realiza a consulta de uma simulacao.
NegativeScenarios	Trying to create an account without authenticating	Consulta um CPF inexistente.
NegativeScenarios	Trying to create a duplicate account	Recebe uma lista de simulacoes e verifica a existencia de um nome especifico.
NegativeScenarios	Trying to insert a banking movement without the appropriate payload	Verifica se existe alguma simulacao cadastrada.
NegativeScenarios	Trying to insert a banking movement with incorrect end date	Cria uma nova simulacao utilizando os seguinte dados randomizados: cpf(válido), nome, email.
NegativeScenarios	Trying to delete an account with banking transactions	Tenta criar uma nova simulacao utilizando um CPF já existente.

Gerar relatórios
Após a execução dos testes, gere o relatório usando o seguinte comando:

mvn allure:report
Inciar o servidor e visualizar o relatório:

mvn allure:serve
Logs
Os logs gerados durante a execuçãp dos testes são armazenadps na pasta logs 

Estrutura do projeto
.
└── logs
    └── .gitkeep
└── src
   └── test
       └── java
           ├── core
           │   └── BaseTest.java
           │   └── Constants.java
           ├── test
           │   └── ConsultRestrictionsTest.java
           │   └── ConsultSimulationTest.java
           │   └── CreateSimalationTest.java
           │   └── DeleteSimulationTest.java
           │   └── SchemaTest.java
           │   └── UpdateSimulationTest.java                      
           │── util
           │   └── CsvUtil.java
           │   └── DataGenerator.java

           └── resources
                  └── app
                  │   └── prova-tecnica-api-master.zip
                  └── data
                  │   └── restrictions.csv
                  ├── runners
                  │   └── consult.restriction.xml
                  │   └── consult.simulation.xml
                  │   └── create.simulation.xml
                  │   └── delete.simulation..xml
                  │   └── schema.xml
                  │   └── update.simulation.xml
                  └── allure.properties
                  └── logging.properties
                  └── simulation.json
└── .gitignore
└── README.md
└── pom.xml

Stack
RestAssured - Testing Framework
MAVEN - Software project management and comprehension tool
JAVA - Programming Language
Cucumber - Testing tool for Behaviour Driver Development

Author
Vinicius Souza

GitHub
Linkedin

Roadmap
