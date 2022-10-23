##指定基础镜像
#FROM openjdk:8
##打包
#RUN mvn clean package -Dmaven.test.skip=true
##拷贝本地文件到镜像的指定目录  并改名
#COPY ./target/iot-alarm-1.0-SNAPSHOT.jar  /tmp/app.jar
## 暴露端口
#EXPOSE 8080
## java项目的启动命令
#ENTRYPOINT java -jar /tmp/app.jar --spring.profiles.active=test

FROM maven:3.6.1 AS compile_stage

#定义工程名称，也是源文件的文件夹名称
ENV PROJECT_NAME iot-alarm
#定义工作目录
ENV WORK_PATH /temp/$PROJECT_NAME

COPY . $WORK_PATH

RUN mvn -version
RUN echo "maven success"

#编译构建
RUN cd $WORK_PATH && mvn clean package -U -DskipTests


### 第二阶段，用第一阶段的jar和jre镜像合成一个小体积的镜像
FROM openjdk:8-jre-alpine

#定义工程名称，也是源文件的文件夹名称
ENV PROJECT_NAME iot-alarm
#定义工程版本
ENV PROJECT_VERSION 1.0-SNAPSHOT

ENV WORK_PATH /temp/$PROJECT_NAME

#工作目录是/app
WORKDIR /app

COPY --from=compile_stage $WORK_PATH/target/${PROJECT_NAME}-${PROJECT_VERSION}.jar ./app.jar
COPY application-test.yml ./application-test.yml

# 暴露端口
EXPOSE 8080
# java项目的启动命令
#ENTRYPOINT java -jar /app/app.jar  --spring.profiles.active=test
ENTRYPOINT java -jar /app/app.jar --spring.config.location=./application-test.yml