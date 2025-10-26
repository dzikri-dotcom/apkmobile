pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "dzikri2811/truth_or_dare_app"  // ganti dengan username dockerhub kamu
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/username/truth_or_dare.git'
            }
        }

        stage('Flutter Build') {
            steps {
                sh 'flutter pub get'
                sh 'flutter build apk --release'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $DOCKER_IMAGE:$DOCKER_TAG
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build & push berhasil ke Docker Hub!"
        }
        failure {
            echo "❌ Build gagal, cek log Jenkins."
        }
    }
}
