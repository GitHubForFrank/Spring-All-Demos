# Spring Boot WebSocket

<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
<a href="#二Spring-Boot-WebSocket">二、Spring Boot WebSocket</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-消息处理">2.1 消息处理</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-注册服务">2.2 注册服务</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-前端-WebSocket">2.3 前端 WebSocket</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-简单登录的实现">2.4 简单登录的实现</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#25-效果演示">2.5 效果演示</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- 项目模拟一个简单的群聊功能，为区分不同的聊天客户端，登录时候将临时用户名存储在 session 当中；
- 关于 WebSocket 的主要配置在 websocket 文件夹下；
- 模板引擎采用 freemaker；
- 项目以 Web 的方式构建。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-websocket/project-structure.png"/> </div>


### 1.2 基本依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--spring boot webSocket 的依赖包 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```



## 二、Spring Boot WebSocket

### 2.1 消息处理

创建消息处理类 ChatSocket，使用 @ServerEndpoint 声明 websocket 服务：

```java
@ServerEndpoint("/websocket/{username}")
@Component
public class ChatSocket {

    /** 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;

    /** concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识 */
    private static CopyOnWriteArraySet<ChatSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("username") String username) {
        System.out.println("来自客户端 sessionId "+session.getId()+" 的消息:" + message);
        //群发消息
        for(ChatSocket item: webSocketSet){
            try {
                item.sendMessage("(sessionId : "+session.getId()+") "+username +" ："+ message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatSocket.onlineCount--;
    }
}

```

### 2.2 注册服务

配置 ServerEndpointExporter，ServerEndpointExporter 会在运行时候自动注册我们用 @ServerEndpoint 注解声明的 WebSocket 服务：

```java
@Configuration
public class WebSocketConfig {

    /***
     * 检测{@link javax.websocket.server.ServerEndpointConfig}和{@link ServerEndpoint} 类型的 bean，
     * 并在运行时使用标准 Java WebSocket 时注册。
     * 我们在{@link WebSocketConfig}中就是使用@ServerEndpoint 去声明 websocket 服务
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

### 2.3 前端 WebSocket

```html
<!doctype html>
<html lang="en">
<head>
    <title>${Session["username"]}您好！欢迎进入群聊大厅！</title>
</head>
<script type="text/javascript">
    let websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:10190/app/websocket/${Session["username"]}");
    }else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        let message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
</head>
<body>
<h5>${Session["username"]} 您好！欢迎进入群聊大厅！</h5>
<input id="text" type="text"/>
<button onclick="send()">发送消息</button>
<hr/>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>
</body>

</html>

```

### 2.4 简单登录的实现

```html
<!doctype html>
<html lang="en">
<head>
    <title>WebSocket登录页面</title>
</head>
<body>

<form action="/app/login" method="post">
    <table border="1" bordercolor="#00ffff" cellpadding=10 cellspacing=0 width=400>
        <tr>
            <!-- 占两列 -->
            <th colspan="2">登录表单</th>
        </tr>
        <tr>
            <td>用户名称</td>
            <td><input type="text" name="username" value=""><br /></td>
        </tr>

        <tr>
            <td>选择聊天页面</td>
            <td>
                <select name="chatPageType">
                    <!-- 默认选择1 -->
                    <option value="1" selected="selected" >简易聊天页面</option>
                    <option value="2" >中等聊天页面</option>
                </select>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="reset" value="清除数据" />
                <input type="submit" value="提交数据"/>
            </th>
        </tr>
    </table>
</form>

</body>
</html>

```

```java
@Slf4j
@Controller
public class LoginController {

    @PostMapping("login")
    public String login(String username, String chatPageType, HttpSession session) {
        log.info("username {},chatPageType {}",username,chatPageType);
        //多种聊天页面的时候用chatPageType做区分
        session.setAttribute("username", username);
        return "chat";
    }

    @GetMapping
    public String index() {
        return "index";
    }

}

```

### 2.5 效果演示

Login URL : `http://localhost:10190/app/`

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-websocket/chat-play.gif"/> </div>

