# Gunakan Ubuntu sebagai base image
FROM ubuntu:22.04

# Set environment variable
ENV DEBIAN_FRONTEND=noninteractive
ENV ANDROID_HOME=/usr/lib/android-sdk
ENV PATH=$PATH:/usr/local/flutter/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Install dependencies
RUN apt-get update && apt-get install -y git curl unzip xz-utils zip openjdk-11-jdk

# Install Flutter
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable

# Install Android SDK Command Line Tools
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    cd ${ANDROID_HOME}/cmdline-tools && \
    curl -o commandlinetools.zip https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip && \
    unzip commandlinetools.zip -d latest && \
    rm commandlinetools.zip && \
    yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses && \
    sdkmanager --sdk_root=${ANDROID_HOME} \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# Set working directory
WORKDIR /app

# Copy project files
COPY . /app

# Jalankan flutter pub get
RUN flutter pub get

# Build APK (debug mode)
RUN flutter build apk --debug

# Expose port
EXPOSE 8080

# Jalankan aplikasi Flutter web (opsional, jika ada web support)
CMD ["flutter", "run", "-d", "web-server", "--web-port=8080", "--web-hostname=0.0.0.0"]
