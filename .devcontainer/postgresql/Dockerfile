FROM postgres:16

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    postgresql-server-dev-16 \
    git \
    ca-certificates && \
    git clone https://github.com/geckoboard/pg_ulid.git /tmp/pg_ulid && \
    cd /tmp/pg_ulid && \
    make && make install && \
    cd / && \
    rm -rf /tmp/pg_ulid && \
    apt-get purge -y --auto-remove \ 
        build-essential \ 
        postgresql-server-dev-16 \ 
        git \ 
        certificates && \
    rm -rf /var/lib/apt/lists/*


