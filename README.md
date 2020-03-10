# oauth2 认证服务

## 使用流程 
[OAuth2.0 协议原理（小米整理）](https://dev.mi.com/console/doc/detail?pId=711#_2_1)
#### （A）客户端（Client）向资源拥有者（Resource Owner）发送授权请求（Authorization Request）
```
http://localhost:8083/auth/authorize?redirect_uri=https%3A%2F%2Fopen.bot.tmall.com%2Foauth%2Fcallback%3FskillId%3D11111111%26token%3DXXXXXXXXXX&client_id=AliGenie&response_type=code&state=111

```

| 参数            | 值                                        |
| ------------- | ---------------------------------------- |
| response_type | 表示授权类型，就是上面讲的那四种类型，这里用的是code方式           |
| client_id     | 表示客户端的ID，代表哪个应用请求验证                      |
| redirect_uri  | 表示重定向URI，验证以后的回调地址，一般用来接收返回的code，以及做下一步处理 |
| scope         | 表示申请的权限范围                                |
| state         | 表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值，作为安全校验 |

####  （B）资源拥有者授权许可（Authorization Grant）

####  （C）客户端向验证服务器（Authorization Server）发送通过（B）获取的授权许可

如果以上步骤都通过的话，认证服务器会转向这个会调地址，带上发放的Code码，类似如下：

```
https://open.bot.tmall.com/oauth/callback?skillId=11111111&token=XXXXXXXXXX&code=bca654ab6133ab3cbc55bb751da93b1c&state=111
```

####  （D）客户端收到授权码后，向验证服务器申请令牌，验证通过的话，则返回Access Token给客户端

####  （D）客户端收到授权码后，向验证服务器申请令牌，验证通过的话，则返回Access Token给客户端

客户端请求：

```
http://localhost:8090/oauth/token?client_id=test&client_secret=test&grant_type=authorization_code&code=bca654ab6133ab3cbc55bb751da93b1c&redirect_uri=http://localhost:8000/login&scope=read%20write&state=09876999
```

| 参数            | 值                                        |
| ------------- | ---------------------------------------- |
| grant_type    | 表示授权类型，这里用的是code方式                       |
| client_id     | 表示客户端的ID，代表哪个应用请求验证                      |
| client_secret | 表示客户端的身份                                 |
| redirect_uri  | 表示重定向URI，验证以后的回调地址，必须与步骤保持一致             |
| code          | 表示上一步获得的授权码，必选项                          |
| state         | 表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值，作为安全校验 |

服务端响应：

```
 HTTP/1.1 200 OK
 Content-Type: application/json;charset=UTF-8
 Cache-Control: no-store
 
{
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
       "scope":"read"
 }

```

| 参数            | 值                                     |
| ------------- | ------------------------------------- |
| access_token  | 表示访问令牌，必选项                            |
| token_type    | 表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型 |
| expires_in    | 表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间      |
| refresh_token | 表示更新令牌，用来获取下一次的访问令牌，可选项               |
| scope         | 表示权限范围，如果与客户端申请的范围一致，此项可省略            |

## 数据库表
#### redis
``
oauth2:visit:${visitId}  访问信息表 hash 20分钟过期
``

| 参数            | 值                                     |
| ------------- | ------------------------------------- |
| client_id     | 表示客户端的ID，代表哪个应用请求验证                    |
| state         |   表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值，作为安全校验|
| userId        | 用户id     |
| userType      | 用户类型（对应相应用户平台）             |
| response_type | 表示授权类型，就是上面讲的那四种类型，这里用的是code方式             |
|userSecret     |表示客户端的身份|
|visitId        |由资源服务生成的访问id|
|redirect_uri   |表示重定向URI，验证以后的回调地址，一般用来接收返回的code，以及做下一步处理|
``
oauth2:code:${code} 存储code  string 5分钟过期 value为visitId
``

``
oauth2:token:${token} 存储token与token相关的用户平台和用户信息 stirng类型 存储数据为json
``

| 参数           | 值                                    |
| ------------  | ------------------------------------- |
|accessToken    |token值         |
|refreshToken   |刷新token的值|
|userId         |用户信息|
|clientId       |获取token服务的id|
|userType       |用户类型（对应相应用户平台）|

``
oauth2:refresh-token:${refresh-token} value 为对应token 用于刷新token
``
#### mongo
**需要认证服务合作商用户信息表**

|参数            | 类型         | 字段描述    |
| ------------- | ---------------------|---------------- |
|id|string|自动生成的ID|
|clientId|string|为认证服务商颁发的id|
|clientSecret|string|用于验证用户|
|userType|string|用户所属用户平台|
|authPageName|string|用户认证页面名称（根据不同类型认证对应不同页面)|
|createDate|string |创建日期|

## 增加认证方式方法
1. 通过调用接口 [合作商添加接口](./src/main/java/com/vwmobvoi/oauth2/oauth2server/controller/PartnerManageController.java) 添加合作用户，如需要不同验证方式页面需新增一个页面。
2. 新增service实现[用户认证接口](./src/main/java/com/vwmobvoi/oauth2/oauth2server/service/UserAuthService.java) 并设置上一步添加的userType


