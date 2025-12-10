pipeline {
    agent any

    environment {
        REGISTRY = "docker.io"
        IMAGE_NAME = "rouamenaa452/monimage"
        SONAR_HOST_URL = "http://192.168.33.10:9000"
        SONAR_LOGIN = "admin"
        SONAR_PASSWORD = "sonar" // ou ton mot de passe changé
        SONAR_PROJECT_KEY = "student-management"
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

        stage('MVN SONARQUBE') {
            steps {
                echo "🔍 Lancement analyse SonarQube..."
                sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_LOGIN} \
                        -Dsonar.password=${SONAR_PASSWORD}
                """
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
