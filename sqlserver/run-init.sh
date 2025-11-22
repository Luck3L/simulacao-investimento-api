/opt/mssql/bin/sqlservr & 
 until /opt/mssql/bin/sqlcmd -S localhost -U sa -P Senha@123admin -Q "SELECT 1" > /dev/null 2>&1 
 do 
   echo "Aguardando SQL Server iniciar..." 
   sleep 5 
 done 
 echo "SQL Server está pronto. Executando scripts de inicialização..." 
 /opt/mssql/bin/sqlcmd -S localhost -U sa -P SSenha@123admin -i /var/opt/mssql/scripts/startup/setup.sql 
 echo "Scripts de inicialização concluídos." 
 wait