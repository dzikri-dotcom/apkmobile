pipeline {
    agent any

    environment {
        FLUTTER_HOME = "C:\\src\\flutter"
        ANDROID_HOME = "C:\\Users\\Dzikri\\AppData\\Local\\Android\\Sdk"
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
                set PATH=%FLUTTER_HOME%\\bin;%PATH%
                flutter doctor
                flutter pub get
                flutter build apk --release
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Membangun image Docker..."
                bat '''
                docker build -t dzikri2811/truth_or_dare_app:latest -f docker/Dockerfile .
                '''
            }
        }

        stage('Push to DockerHub') {
            steps {
                echo "📦 Push image ke DockerHub..."
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push dzikri2811/truth_or_dare_app:latest
                    '''
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
