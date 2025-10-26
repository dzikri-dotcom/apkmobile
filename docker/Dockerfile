FROM ubuntu:22.04

# 1️⃣ Install dependencies
RUN apt-get update && apt-get install -y \
    git curl wget unzip xz-utils zip openjdk-11-jdk libglu1-mesa \
    && rm -rf /var/lib/apt/lists/*

# 2️⃣ Install Flutter SDK
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# 3️⃣ Install Android SDK
ENV ANDROID_HOME="/usr/lib/android-sdk"
ENV PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${PATH}"

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools \
    && cd ${ANDROID_HOME}/cmdline-tools \
    && curl -sSL https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -o tools.zip \
    && unzip -q tools.zip -d cmdline-tools \
    && rm tools.zip \
    && mkdir -p ${ANDROID_HOME}/cmdline-tools/latest \
    && mv cmdline-tools/* ${ANDROID_HOME}/cmdline-tools/latest/ \
    && yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} --licenses || true \
    && ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# 4️⃣ Set working directory
WORKDIR /app

# 5️⃣ Copy project
COPY . .

# 6️⃣ Build Flutter APK
RUN flutter doctor
RUN flutter pub get
RUN flutter build apk --release

CMD ["echo", "✅ Flutter build complete!"]
