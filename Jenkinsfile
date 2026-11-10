pipeline {
    agent any

    tools {
        maven: 'Maven 3'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Clonando repositorio gestao-pedidos...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando gestao-pedidos com Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Gerando imagem Docker do gestao-pedidos...'
                sh 'docker build -t vinheria/gestao-pedidos:latest .'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Realizando deploy do gestao-pedidos...'
                sh 'docker stop gestao-pedidos || true'
                sh 'docker rm gestao-pedidos || true'
                sh '''
                    docker run -d \
                        --name gestao-pedidos \
                        --network vinheria-network \
                        -p 8083:8083 \
                        vinheria/gestao-pedidos:latest
                '''
                echo 'Deploy do gestao-pedidos concluido! Rodando na porta 8083.'
            }
        }

    }

    post {
        success {
            echo 'Pipeline do gestao-pedidos finalizado com sucesso!'
        }
        failure {
            echo 'Algo deu errado no pipeline do gestao-pedidos.'
        }
    }
}
