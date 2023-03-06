FROM mysql:5.7

EXPOSE 3309

COPY my.cnf /etc/mysql/conf.d
COPY initialize_data.sql /docker-entrypoint-initdb.d
