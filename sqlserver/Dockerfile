FROM mcr.microsoft.com/mssql/server:2022-CU21-ubuntu-22.04

WORKDIR /var/opt/mssql

COPY ./sqlserver/run-init.sh /var/opt/mssql/scripts/startup/run-init.sh
COPY ./sqlserver/setup.sql /var/opt/mssql/scripts/startup/setup.sql

RUN chmod +x /var/opt/mssql/scripts/startup/run-init.sh

ENTRYPOINT ["/var/opt/mssql/scripts/startup/run-init.sh"]