pipeline {
    agent any

    environment {
        REGISTRY = "docker.io"
        IMAGE_NAME = "rouamenaa452/monimage"
    }

    triggers {
        githubPush()   // <-- déclenchement automatique via webhook GitHub
    }

    stages {

        stage('Checkout') {
            steps {
                echo "📥 Récupération du dernier commit depuis GitHub..."
                checkout scm
            }
        }

        stage('Clean & Build') {
            steps {
                echo "🧹 Nettoyage + compilation..."
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Docker Build') {
            steps {
                echo "🐳 Construction de l’image Docker..."
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Login & Push') {
            steps {
                echo "📤 Push Docker Hub..."
                withCredentials([usernamePassword(
                    credentialsId: 'dockercredentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${IMAGE_NAME}:latest
                    """
                }
            }
        }
    }

    post {
        always {
            echo "✔️ Pipeline terminée."
            sh "docker logout"
        }
    }
}
