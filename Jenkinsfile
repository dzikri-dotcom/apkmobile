pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "dzikri2811/truth_or_dare_app"  // Ganti dengan nama image DockerHub kamu
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pastikan kamu sudah menambahkan credentials GitHub dengan ID: github-credentials
                git branch: 'main',
                    url: 'https://github.com/dzikri-dotcom/apkmobile.git',
                    credentialsId: 'github-credentials'
            }
        }

        stage('Flutter Build') {
            steps {
                // Pastikan Jenkins sudah terinstall Flutter SDK dan ada di PATH
                bat 'flutter pub get'
                bat 'flutter build apk --release'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker push %DOCKER_IMAGE%:%DOCKER_TAG%
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
