FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && apt-get install -y git curl unzip xz-utils zip

# Install Flutter
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# Set workdir
WORKDIR /app

# Copy semua file project ke container
# Karena Jenkins menjalankan: docker build -f docker/Dockerfile .
# Maka konteks build-nya adalah root project
COPY . .

# Ambil dependency dan build
RUN flutter pub get
RUN flutter build apk --release

CMD ["echo", "✅ Flutter build complete!"]
