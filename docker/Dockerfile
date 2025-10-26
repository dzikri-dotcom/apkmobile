FROM ubuntu:22.04

# Non-interaktif agar build tidak terhenti
ENV DEBIAN_FRONTEND=noninteractive

# Install dependencies
RUN apt-get update && apt-get install -y \
    git curl unzip xz-utils zip openjdk-11-jdk wget \
    && apt-get clean

# Install Flutter SDK
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# Install Android SDK Command Line Tools (FIXED STRUCTURE)
ENV ANDROID_HOME="/usr/lib/android-sdk"
ENV PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${PATH}"

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    cd ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O tools.zip && \
    unzip tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    rm tools.zip && \
    mkdir -p ${ANDROID_HOME}/cmdline-tools/latest && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools/* ${ANDROID_HOME}/cmdline-tools/latest/ && \
    chmod +x ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager && \
    yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses || true && \
    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} \
        "platform-tools" \
        "build-tools;34.0.0" \
        "platforms;android-34"

# Set working directory
WORKDIR /app

# Copy source code project
COPY . .

# Build Flutter APK
RUN flutter doctor -v
RUN flutter pub get
RUN flutter build apk --release

CMD ["echo", "✅ Flutter build selesai tanpa error!"]
