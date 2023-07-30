Setup maven credentials in maven settings.xml

<servers>
    <server>
        <id>registry.hub.docker.com</id>
        <username>username</username>
        <password>password</password>
    </server>
</servers>

Configure environment variables as application.yaml requires
Run docker compose command:

docker-compose pull | docker-compose -f docker-compose.yml up -d

This will create a container with PostgreSQL and AuthServer application