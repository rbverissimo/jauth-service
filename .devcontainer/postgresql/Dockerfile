FROM postgres:16

ENV GIT_TERMINAL_PROMPT=0

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    postgresql-server-dev-16 \
    git \
    ca-certificates && \
    git clone https://github.com/VADOSWARE/pg_idkit.git /tmp/pg_idkit --depth 1 && \
    cd /tmp/pg_idkit && \
    make && make install && \
    cd / && \
    rm -rf /tmp/pg_idkit && \
    apt-get purge -y --auto-remove \ 
        build-essential \ 
        postgresql-server-dev-16 \ 
        git \ 
        ca-certificates && \
    rm -rf /var/lib/apt/lists/*


