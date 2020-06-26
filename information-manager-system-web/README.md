# information-manager-system-web
# 打包脚本
mvn -Dmaven.test.skip=true clean package install spring-boot:repackage assembly:single
# swagger地址
http://ip:host/swagger-ui.html
# JVM参数
-Dspring.profiles.active=dev      -Dapollo.bootstrap.enabled=true -Dapollo.meta=http://121.36.139.115:8080/ -Dapp.id=information-manager-system-ysehf -Denv=DEV -Didc=default