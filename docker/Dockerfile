# Gunakan base image Ubuntu
FROM ubuntu:22.04

# Non-interaktif agar tidak menunggu input saat build
ENV DEBIAN_FRONTEND=noninteractive

# =============================
# 1. Install basic dependencies
# =============================
RUN apt-get update && apt-get install -y \
    git curl wget unzip xz-utils zip \
    openjdk-11-jdk \
    libglu1-mesa \
    && rm -rf /var/lib/apt/lists/*

# =============================
# 2. Install Flutter SDK
# =============================
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# =============================
# 3. Install Android SDK + build tools
# =============================
ENV ANDROID_HOME="/usr/lib/android-sdk"
ENV PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${PATH}"

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    cd ${ANDROID_HOME}/cmdline-tools && \
    curl -sSL https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -o tools.zip && \
    unzip -q tools.zip -d latest && \
    rm tools.zip && \
    yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} --licenses && \
    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_HOME} \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# =============================
# 4. Set Flutter configuration
# =============================
RUN flutter config --no-analytics
RUN flutter precache
RUN flutter doctor -v

# =============================
# 5. Set working directory
# =============================
WORKDIR /app

# =============================
# 6. Copy project files
# =============================
COPY . .

# =============================
# 7. Build Flutter APK
# =============================
RUN flutter pub get && \
    flutter build apk --release

# =============================
# 8. Default CMD
# =============================
CMD ["echo", "✅ Flutter build complete!"]
