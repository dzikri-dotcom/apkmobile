FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && apt-get install -y git curl unzip xz-utils zip

# Install Flutter manually
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter -b stable
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

WORKDIR /app
COPY ../app /app
RUN flutter pub get
RUN flutter build web --release
