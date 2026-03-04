@echo off
REM ============================================
REM  StreamForge Config Repo Setup Script
REM  Creates a local Git repo for Spring Cloud Config
REM ============================================

set CONFIG_DIR=%USERPROFILE%\streamforge-config-repo

echo Creating config repository at: %CONFIG_DIR%
mkdir "%CONFIG_DIR%" 2>nul
cd /d "%CONFIG_DIR%"

REM Initialize Git repo
git init

REM Create video-service config
(
echo server:
echo   port: 8081
echo.
echo spring:
echo   application:
echo     name: video-service
echo   datasource:
echo     url: jdbc:h2:mem:videodb
echo     driver-class-name: org.h2.Driver
echo     username: sa
echo     password:
echo   jpa:
echo     hibernate:
echo       ddl-auto: update
echo     show-sql: true
echo.
echo eureka:
echo   client:
echo     service-url:
echo       defaultZone: http://localhost:8761/eureka/
) > video-service.yml

REM Create user-service config
(
echo server:
echo   port: 8082
echo.
echo spring:
echo   application:
echo     name: user-service
echo   datasource:
echo     url: jdbc:h2:mem:userdb
echo     driver-class-name: org.h2.Driver
echo     username: sa
echo     password:
echo   jpa:
echo     hibernate:
echo       ddl-auto: update
echo     show-sql: true
echo.
echo eureka:
echo   client:
echo     service-url:
echo       defaultZone: http://localhost:8761/eureka/
) > user-service.yml

REM Create gateway-service config
(
echo server:
echo   port: 8080
echo.
echo spring:
echo   application:
echo     name: gateway-service
echo   cloud:
echo     gateway:
echo       discovery:
echo         locator:
echo           enabled: true
echo           lower-case-service-id: true
echo.
echo eureka:
echo   client:
echo     service-url:
echo       defaultZone: http://localhost:8761/eureka/
) > gateway-service.yml

REM Commit config files
git add .
git commit -m "Initial config files for StreamForge microservices"

echo.
echo ============================================
echo  Config repository created successfully!
echo  Location: %CONFIG_DIR%
echo ============================================
