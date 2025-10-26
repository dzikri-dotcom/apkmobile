FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && apt-get install -y \
    git curl unzip xz-utils zip openjdk-11-jdk \
    && apt-get clean

# Install Flutter SDK
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# Install Android SDK (Fixed structure)
ENV ANDROID_HOME="/usr/lib/android-sdk"
ENV PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${PATH}"

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    cd ${ANDROID_HOME}/cmdline-tools && \
    curl -sSL https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -o tools.zip && \
    unzip -q tools.zip && rm tools.zip && \
    mkdir -p ${ANDROID_HOME}/cmdline-tools/latest && \
    mv cmdline-tools/* ${ANDROID_HOME}/cmdline-tools/latest/ || true && \
    chmod +x ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager && \
    yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} --licenses || true && \
    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# Set working directory
WORKDIR /app

# Copy source code project
COPY . .

# Get Flutter dependencies & build release APK
RUN flutter doctor
RUN flutter pub get
RUN flutter build apk --release

CMD ["echo", "✅ Flutter build complete!"]
