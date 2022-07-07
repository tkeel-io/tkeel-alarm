#指定基础镜像
FROM openjdk:8
#拷贝本地文件到镜像的指定目录  并改名
COPY ./target/iot-alarm-1.0-SNAPSHOT.jar  /tmp/app.jar
# 暴露端口
EXPOSE 8080
# java项目的启动命令
ENTRYPOINT java -jar /tmp/app.jar --spring.profiles.active=test