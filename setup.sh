#!/bin/bash

echo "========================================"
echo "Movie Ticket Booking System Setup"
echo "========================================"
echo

echo "This script will help you set up environment variables for the application."
echo

# Check if .env file exists
if [ -f .env ]; then
    echo ".env file found. Loading environment variables..."
    export $(cat .env | grep -v '^#' | xargs)
    echo "Environment variables loaded successfully!"
else
    echo ".env file not found. Please create one with your configuration."
    echo "See SETUP_GUIDE.md for instructions."
    echo
    read -p "Press Enter to continue..."
    exit 1
fi

echo
echo "Starting the application..."
echo

# Run the application
mvn spring-boot:run 