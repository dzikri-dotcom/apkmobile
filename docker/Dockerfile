# --- Base image ---
FROM ubuntu:22.04

# --- Environment ---
ENV DEBIAN_FRONTEND=noninteractive
ENV ANDROID_HOME=/usr/lib/android-sdk
ENV FLUTTER_HOME=/usr/local/flutter
ENV PATH=$FLUTTER_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

# --- Install dependencies ---
RUN apt-get update && apt-get install -y \
    git \
    curl \
    unzip \
    xz-utils \
    zip \
    openjdk-11-jdk \
    wget \
    && rm -rf /var/lib/apt/lists/*

# --- Install Flutter ---
RUN git clone https://github.com/flutter/flutter.git $FLUTTER_HOME -b stable

# --- Install Android SDK Command-line Tools ---
RUN mkdir -p $ANDROID_HOME/cmdline-tools && \
    cd $ANDROID_HOME/cmdline-tools && \
    curl -o commandlinetools.zip https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip && \
    unzip commandlinetools.zip -d latest && \
    rm commandlinetools.zip

# --- Accept licenses & install required SDK components ---
RUN yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --sdk_root=$ANDROID_HOME --licenses && \
    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --sdk_root=$ANDROID_HOME \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# --- Set working directory ---
WORKDIR /app

# --- Copy project files ---
COPY . /app

# --- Get Flutter dependencies ---
RUN flutter pub get

# --- Build APK (release mode) ---
RUN flutter build apk --release

# --- Expose port (opsional) ---
EXPOSE 8080

# --- Default command (opsional, untuk web) ---
CMD ["bash"]
