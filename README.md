Setup maven credentials in maven settings.xml

<servers>
    <server>
        <id>registry.hub.docker.com</id>
        <username>username</username>
        <password>password</password>
    </server>
    <server>
        <id>registry-1.docker.io</id>
        <username>username</username>
        <password>password</password>
    </server>
</servers>

Configure environment variables as application.yaml requires
Run docker compose command:

docker-compose pull | docker-compose -f docker-compose.yml up -d

This will create a container with PostgreSQL and AuthServer application

Any new change to be pushed to registry run maven build command as

mvn clean install jib:build -Denv.port=8080

To generate build allowed to run it in docker

mvn clean install jib:build -Denv.port=8080 -Pproduction