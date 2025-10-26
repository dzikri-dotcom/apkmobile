pipeline {
    agent any

    environment {
        // Nama image di DockerHub (ganti sesuai akunmu)
        DOCKER_IMAGE = "dzikri2811/truth_or_dare_app"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout Repository') {
            steps {
                // Ambil source code dari GitHub
                git branch: 'main',
                    url: 'https://github.com/dzikri-dotcom/apkmobile.git',
                    credentialsId: 'github-credentials'
            }
        }

        stage('Flutter Setup & Build') {
            steps {
                echo "🚀 Menjalankan build Flutter APK..."
                bat 'flutter --version'
                bat 'flutter pub get'
                bat 'flutter build apk --release'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Membangun image Docker..."
                script {
                    // Gunakan Dockerfile dari folder docker/
                    bat """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -f docker/Dockerfile .
                    """
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                echo "📤 Mengunggah image ke DockerHub..."
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                    docker logout
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build & push berhasil ke DockerHub!"
        }
        failure {
            echo "❌ Build gagal, silakan cek log Jenkins untuk detail error."
        }
    }
}
