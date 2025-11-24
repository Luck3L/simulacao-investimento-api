FROM mcr.microsoft.com/mssql/server:2022-CU21-ubuntu-22.04
ENV PATH="$PATH:/opt/mssql-tools/bin"
USER root
# Instala mssql-tools
RUN apt-get update && \
    apt-get install -y curl gnupg && \
    curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - && \
    curl https://packages.microsoft.com/config/ubuntu/22.04/prod.list > /etc/apt/sources.list.d/mssql-release.list && \
    apt-get update && \
    ACCEPT_EULA=Y apt-get install -y mssql-tools unixodbc-dev && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /var/opt/mssql && \
    chown -R 10001:0 /var/opt/mssql && \
    chmod -R 770 /var/opt/mssql

USER mssql
WORKDIR /app
COPY --chmod=755 init/run-init.sh .
COPY init/setup.sql .
CMD ["./run-init.sh"]