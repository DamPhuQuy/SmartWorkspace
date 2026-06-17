# Refactor backend codebase

Refactor current codebase

## Clean Architecture

The project must follow the clean architecture codebase for each feature

The architecture:

```md
workspace/
└── features/
└── auth/
├── application/
│ ├── dto/
│ ├── ports/
│ │ ├── in/
│ │ └── out/
│ └── service/
│
├── domain/
│ ├── enum/
│ └── model/
│
└── infrastructure/
├── web/
│ └── dto/
│
├── persistence/
│ ├── jpaRepo/
│ ├── entity/
│ └── adapter/
│
└── config/
└── (manual bean configuration)
....
└──config/
└──common/
```

## Use docker with .env

Create Dockerfile in both frontend/ and backend/, docker-compose.yaml

The using image is alpine

In .env, put JWT secret key in .env and config in application.yaml

## Authentication and authorization

Create a RefreshToken entity with the following fields:

- id (UUID or Long)
- token (unique string)
- user (ManyToOne relationship)
- expiryDate
- revoked
- createdAt

Implement refresh token workflow:

User login returns:
access token (JWT)
refresh token
Access token should have a short expiration time (e.g. 15 minutes).
Refresh token should have a longer expiration time (e.g. 30 days).
Provide an endpoint to exchange a valid refresh token for a new access token.
Support refresh token revocation on logout.
Validate expiration and revocation status before issuing a new access token.

Create Role entity:

- id
- name

Create Permission entity:

- id
- name

Implement Role-Based Access Control (RBAC).
Permissions should follow the format: "something:something" (e.g: course:create)

A User can have one or multiple Roles.
Authorization must be integrated with Spring Security.
Protect endpoints using permissions rather than role names whenever possible.

## Handle exception

Add handle exception with class GlobalExceptionHandler with @RestControllerAdvice and handle specific exception with @ExceptionHandler
