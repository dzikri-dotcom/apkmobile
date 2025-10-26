pipeline {
    agent any

    environment {
        FLUTTER_HOME = "C:\\src\\flutter"
        PATH = "${FLUTTER_HOME}\\bin;${env.PATH}"
        DOCKER_IMAGE = "dzikri2811/truth_or_dare_app"  // ganti dengan nama image kamu di DockerHub
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                // Ambil kode dari GitHub (pakai credentials GitHub kamu)
                git branch: 'main',
                    url: 'https://github.com/dzikri-dotcom/apkmobile.git',
                    credentialsId: 'github-credentials'
            }
        }

        stage('Flutter Build') {
            steps {
                // Perbaiki akses Git dan pastikan Flutter dikenali
                bat '''
                git config --system --add safe.directory C:/src/flutter
                git config --global --add safe.directory C:/src/flutter

                echo ===== Flutter Doctor =====
                flutter doctor

                echo ===== Flutter Pub Get =====
                flutter pub get

                echo ===== Build APK =====
                flutter build apk --release
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                    """
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'dockerhub-credentials',
                                     usernameVariable: 'DOCKER_USER',
                                     passwordVariable: 'DOCKER_PASS')
                ]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
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
