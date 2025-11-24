/opt/mssql/bin/sqlservr & 
 until /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Senha@123admin -Q "SELECT 1" > /dev/null 2>&1 
 do 
   echo "Aguardando SQL Server iniciar..." 
   sleep 30 
 done 
 echo "SQL Server está pronto. Executando scripts de inicialização..." 
 /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Senha@123admin -d master -i /app/setup.sql

 echo "Scripts de inicialização concluídos." 
 wait