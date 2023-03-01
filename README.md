<h1  align="center">WLRIoTPlatform</h1>

---

## 简介

这是一个支持多协议接入的物联网平台，支持市面上的主流物联网协议如HTTP协议、MQTT协议、CoAP协议。基于SpringBoot和SpringCloud进行后端开发，基于Vue+Vuetify进行前端开发，是一个前后端分离的微服务架构系统。使用RabbitMQ用于异步通信，使用Redis缓存高频访问数据。MySQL索引优化提高性能......

目前仍在开发中，后续功能会尽快更新...


## 开始

### 前端

前端使用[Vue(2.6)](https://v2.cn.vuejs.org/index.html)+[Vuetify](https://vuetify.cn/zh-Hans/getting-started/quick-start/#vue-cli-%E5%AE%89%E8%A3%85)进行开发，使用[Vue-cli](https://cli.vuejs.org/zh/guide/)进行构建，使用yarn进行依赖管理。

在项目目录下打开终端，进入vue_space目录:
```shell
cd vue_space
```

安装依赖:
```
yarn install
```

启动服务:
```
yarn serve
```

默认端口使用19898，可在vue.config.js中修改。
```
http://localhost:19898
```

### 后端

* #### Java ：1.8.0_352

* #### SpringBoot ：2.7.6、2.7.7

* #### SpringCloudAlibaba ：2021.0.1.0

* #### MySQL ：8.0.31

  .sql文件在项目目录中，可直接导入。

  之后请更改各个服务配置文件中的MySQL配置。

  用户密码高度加密，管理员登录邮箱`admin@admin.com`，密码`123456`。

> 注：`0d08db925f7d3332305970bfb4cba6df7aa1c36570f979f0d5b862db70a33237`在逻辑上并**不等于**`123456`的加密，所以将`0d08db925f7d3332305970bfb4cba6df7aa1c36570f979f0d5b862db70a33237`存储在其他用户的数据上，然后使用密码`123456`登录并不能成功，每个用户输入`123456`为密码，数据库都将存储不同的密文。


* #### Redis ：7.0.8

  需正确配置服务中Redis的`host`、`port`、`password`等参数。

* #### RabbitMQ ：3.9.27-management

  需正确配置服务中RabbitMQ的`host`、`port`、`username`、`password`等参数。

* #### Nacos ：2.1.1

  需正确配置服务中Nacos的`server-addr`等参数

* #### Mosquitto ：2.0.15

  可选择其他MQTT Broker

  正确运行后应至少配置**两个**账户：

 1. 平台接入，应设置高权限，设置成功后配置到`DeviceGateway/src/main/java/com/wei/devicegateway/server/MqttDeviceServer.java`中。
 2. 设备接入，应设置低权限(仅可发送接受小部分topic的信息)，设置成功后配置到`ATestSendMessage/src/main/java/com/weilai/atestsendmessage/mqtt/MqttSendMessage.java`或设备、模拟设备中。


* #### MailService

  首先你应该拥有一个开通SMTP服务的邮箱账户，之后在`MailService/src/main/resources/application.yaml`正确配置`host`、`port`、邮箱 等参数。

* #### ATestSendMessage

  这是一个模拟发送消息的测试程序，可用于测试CoAP、MQTT协议的设备接入、数据定时发送等操作，请按需使用。




## 功能架构略图

整体功能架构如下图所示：

![1](https://github.com/WeiLaiR/WLRIoTPlatform/blob/master/Aimg/draw01.png)


## 展示

* 主页：

  ![1](https://github.com/WeiLaiR/WLRIoTPlatform/blob/master/Aimg/home.png)


