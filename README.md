# IBM MQ Spring Boot App

Example integration with IBM MQ 9 using Spring Integration with JMS. Additionally, sending data from
MQ 9 to RabbitMQ via AMQP.

Data integration layout:

![techlayout](https://github.com/ddubson/ibmmq-boot-app/blob/master/assets/tech-layout.png)

## Pre-requisites

Requirements:

- IBM MQ
- Rabbit MQ 

Export the necessary environment variables

```
export IBM_MQ_JMS_QUEUEMANAGER=
export IBM_MQ_JMS_QUEUENAME=
export IBM_MQ_JMS_HOSTNAME=
export IBM_MQ_JMS_PORT=
export IBM_MQ_JMS_CHANNEL=
export IBM_MQ_JMS_USERNAME=
export IBM_MQ_JMS_PASSWORD=

export RABBITMQ_HOSTNAME=
export RABBITMQ_PORT=
export RABBITMQ_QUEUENAME=
```

## Running

```
./gradlew clean bootRun
```

On Windows:

```
gradlew.bat clean bootRun
```