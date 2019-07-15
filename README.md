# information-manager-system
# 打包脚本
mvn -Dmaven.test.skip=true clean package spring-boot:repackage assembly:single
# swagger地址
http://ip:host/swagger-ui.html