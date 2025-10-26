pipeline {
    agent any

    environment {
        // Lokasi Flutter SDK di Windows
        FLUTTER_HOME = "C:\\src\\flutter"
        PATH = "${FLUTTER_HOME}\\bin;${env.PATH}"

        // Image DockerHub
        DOCKER_IMAGE = "dzikri2811/truth_or_dare_app"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📥 Checkout dari GitHub..."
                git branch: 'main',
                    url: 'https://github.com/dzikri-dotcom/apkmobile.git',
                    credentialsId: 'github-credentials'
            }
        }

        stage('Flutter Build') {
            steps {
                echo "🚀 Build Flutter APK..."
                bat '''
                git config --system --add safe.directory C:/src/flutter
                git config --global --add safe.directory C:/src/flutter

                echo ===== Flutter Doctor =====
                flutter doctor

                echo ===== Flutter Pub Get =====
                flutter pub get

                echo ===== Build APK Release =====
                flutter build apk --release
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Membangun image Docker..."
                script {
                    // Gunakan Dockerfile yang ada di folder docker/
                    bat """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -f docker/Dockerfile .
                    """
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                echo "📤 Push image ke DockerHub..."
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
            echo "✅ Build & Push berhasil ke DockerHub!"
        }
        failure {
            echo "❌ Build gagal! Silakan cek log Jenkins untuk detail error."
        }
    }
}
