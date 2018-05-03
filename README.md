# base
基础服务
v1.0：
基于springboot的REST应用
集成 spring-security mybatis swagger2 redis websocket quartz jwt activiti 
主要功能模块:用户、角色、权限、菜单、日志、基于websocket双工通信、自定义定时任务、基于阿里的云短信服务

说明:
自定义定时任务：任务需要提供http接口，实现定时任务与业务逻辑的解耦
基于阿里的云短信服务：主要提供登录验证，注册验证、绑定手机验证.
redis：用于缓存登录令牌、手机验证码
swagger2：通过注解方式生成api ，并可通过 host/swagger-ui.html查看相关接口


集成 rabbitmq
处理告警通知,


# modify authentication
修改登录认证，
添加PhoneCodeAuthenticationToken、MyUsernameAuthenticationToken。
并将上述两个认证令牌分别与自定义认证器
（CustAuthenticationProvider UsernameAuthenticationProvider）绑定
不再使用自带的 UsernamePasswordAuthenticationToken

# jiguang
加入极光推送

# v1.101
1登录接口返回 用户类型+所属工厂
2添加视频配置接口


