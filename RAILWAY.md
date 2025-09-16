# Deploying to Railway

## Prerequisites
- GitHub repository with this project
- Railway account (https://railway.app)

## 1) Dockerfile
A production multi-stage Dockerfile is included at the project root. Railway will detect and build it.

## 2) Create Railway Project
- New Project → Deploy from GitHub → select this repo
- Choose Dockerfile service

## 3) Add MySQL Plugin
- In the same Railway project: Add Plugin → MySQL
- Note the variables: `MYSQLHOST`, `MYSQLPORT`, `MYSQLUSER`, `MYSQLPASSWORD`, `MYSQLDATABASE`

## 4) Configure Environment Variables (Service → Variables)
Map your application settings to Railway variables.

Required
- `spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}`
- `spring.datasource.username=${MYSQLUSER}`
- `spring.datasource.password=${MYSQLPASSWORD}`
- `spring.jpa.hibernate.ddl-auto=update`
- `TO_EMAIL=...`
- `EMAIL_PASS=...`
- `FROM_EMAIL_ID=...`
- `TWILIO_ACCOUNT_SID=...`
- `TWILIO_AUTH_TOKEN=...`
- `WHATSAPP_NUMBER=...`
- `TWILIO_SMS_NUMBER=...`
- `TWILIO_WHATSAPP_TEMPLATE=...`

Already configured defaults
- `spring.jms.listener.auto-startup=false` (prevents broker connection attempts)
- `server.port=${PORT:8080}` (Railway sets `PORT`)

## 5) Deploy
- Railway builds the Docker image and runs it
- Open the generated domain once logs show the app started

## 6) Post-deploy
- If you use external SMTP/Twilio, ensure credentials are correct
- If you later add a JMS broker, set `spring.jms.listener.auto-startup=true` and add the broker URL

That’s it—your Spring Boot app (static + server) is live on one URL. 