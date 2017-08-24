# IBM MQ Spring Boot App

## Pre-requisites

Export the necessary environment variables

```
export IBM_MQ_JMS_QUEUEMANAGER=
export IBM_MQ_JMS_QUEUENAME=
export IBM_MQ_JMS_HOSTNAME=
export IBM_MQ_JMS_PORT=
export IBM_MQ_JMS_CHANNEL=
export IBM_MQ_JMS_USERNAME=
export IBM_MQ_JMS_PASSWORD=
```

## Running

```
./gradlew clean bootRun
```

On Windows:

```
gradlew.bat clean bootRun
```