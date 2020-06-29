# information-manager-system-web
# 打包脚本
mvn -Dmaven.test.skip=true clean package install spring-boot:repackage assembly:single
# swagger地址
http://ip:host/swagger-ui.html
# JVM参数
nohup java      -Xms256M -Xmx1024M -Xss512k -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -Xloggc:gc.log      -Dspring.profiles.active=dev -Dloader.path=libs/,config/,front-end/,/opt/data/information-manager-system-web-data/      -Dapollo.bootstrap.enabled=true -Dapollo.meta=http://121.36.139.115:8080/ -Dapp.id=information-manager-system-ysehf -Denv=DEV -Didc=dev      -jar $JAR_NAME &