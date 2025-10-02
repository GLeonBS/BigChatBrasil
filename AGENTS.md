# BigChatBrasil Agent Guidelines

## Build/Test Commands
- **Build**: `mvn clean install` or `./mvnw clean install`
- **Test**: `mvn test` or `./mvnw test`
- **Run**: `./mvnw spring-boot:run`
- **Single test**: `mvn test -Dtest=TestClassName`

## Code Style Guidelines
- **Java Version**: 17 with Spring Boot 3.4.1
- **Dependencies**: Use Lombok extensively (@Data, @AllArgsConstructor, @Service)
- **Imports**: Custom imports first, then jakarta/javax, then lombok
- **Naming**: PascalCase for classes, camelCase for methods/variables
- **IDs**: Use UUID for entity IDs
- **Validation**: @NotNull, @NotBlank, @Size for bean validation
- **Enums**: Use EnumType.STRING in JPA
- **Architecture**: Repository pattern with Use Cases for business logic
- **Controllers**: Return ResponseEntity with appropriate HTTP status codes
- **Mapping**: Use BeanUtils.copyProperties for DTO/Entity conversion
- **Error Handling**: Throw custom exceptions, handle in controllers with try-catch
- **Testing**: JUnit 5 + Mockito + AssertJ, test methods named `shouldBe...` or `shouldBeThrow...`
- **Security**: JWT authentication with @SecurityRequirement annotations