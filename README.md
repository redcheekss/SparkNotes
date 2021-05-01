# Flink plugin

aliyun flink sql vvp user define connector

由于阿里云 全托管模式 sql开发 连接器未提供RDS PostgreSQL TYPE。 所以需要自定义连接器，并上传至阿里云。

此连接器为JDBC连接器。可连接RDS MYSQL ,RDS PostgreSQL等数据源。(此代码参考至开源社区flink 源码,其他自定义连接器如redis,es等同样可参考开源社区代码)

> 阿里云全托管Flink管理自定义connector:https://help.aliyun.com/document_detail/193520.html?spm=a2c4g.11174283.6.672.7a5773d5COHO0c

使用方式：

将对应连接器打成jar包上传至阿里云。

为了避免JAR包依赖冲突，您需要注意以下几点：

- Flink镜像和Pom依赖的Flink版本请保持一致。
- 请不要上传Runtime层的JAR包，即在依赖中添加`<scope>provided</scope>`。
- 其他第三方依赖请采用Shade方式打包，Shade打包详情参见[Apache Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/index.html)。

其中如果项目中包含第三方依赖，需使用shade打包方式。

即在`pom.xml`文件中添加

```xml
	<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.1.1</version>
                <configuration>
                    <!-- put your configurations here -->
                    <relocations>
                        <!-- 此处通过shaded的打包方式,将第三方依赖驱动org.postgresql
                                 重命名为org.shaded.postgresql -->
                        <relocation>
                            <pattern>org.postgresql</pattern>
                            <shadedPattern>org.shaded.postgresql</shadedPattern>
                        </relocation>
                    </relocations>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

**shaded打包命令**

```shell
mvn clean package org.apache.maven.plugins:maven-shade-plugin:3.1.1:shade -DskipTests
```

