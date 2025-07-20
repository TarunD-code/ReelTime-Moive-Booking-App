@echo off
echo ========================================
echo Movie Ticket Booking System Setup
echo ========================================
echo.

echo This script will help you set up environment variables for the application.
echo.

REM Check if .env file exists
if exist .env (
    echo .env file found. Loading environment variables...
    for /f "tokens=1,2 delims==" %%a in (.env) do (
        set %%a=%%b
    )
    echo Environment variables loaded successfully!
) else (
    echo .env file not found. Please create one with your configuration.
    echo See SETUP_GUIDE.md for instructions.
    echo.
    pause
    exit /b 1
)

echo.
echo Starting the application...
echo.

REM Run the application
mvn spring-boot:run

pause 