# Retail

### Required resources

<br />
KEYCLOAK - <br />
<code>docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.4 start-dev</code>

<br />
ZIPKIN - <br />
<code>docker run -d -p 9411:9411 openzipkin/zipkin</code>