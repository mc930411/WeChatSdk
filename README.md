# WeChatSdk
做了一个简单的类似微信的消息渲染页面SDK，娱乐向

## 一、输出列表

| 模块名称 | 功能                                           | 备注 |
| -------- | ---------------------------------------------- | ---- |
| SDK      | 以aar的形式提供（res、src）                    |      |
| Demo     | 嵌入了SDK的演示Demo，调用SDK接口动态展示UI效果 |      |
| Usage    | SDK使用指南(接口、数据类)                      |      |



## 二、SDK功能解析

### 1、消息类

public Class WeChatMessage{

​		public int messageType;

​		public Object data;

}

文字时，数据存储在哪？图片时，存储在哪？视频时，存储在哪？



加分项：能否增加一个小视频功能。



### 2、微信聊天界面

使用RecycleView还是其他？

每条消息的自定义item，要根据不同的数据类型选择不同的加载控件。（参考微信设计）

消息类型不同，item设计不同。

渲染性能优化，避免过度布局。



### 3、功能管理中心类（单例）

全局唯一单例，避免反复创建管理对象。

#### 3.1、设置消息队列接口

设置需要渲染的消息队列；

#### 3.2、渲染接口。

将消息队列中的内容渲染至界面

- 单条item渲染接口；
- 全局渲染接口；





## 三、拓展性解析

拓展性考虑SDK的可迭代开发、场景拓展。

### 1、消息类型拓展

增加视频、文章链接、分享页面等类型消息时，仅需要开发对应的item.xml以及对应的ViewHolder。

### 2、群聊

SDK的接口支持双人聊天场景，如果需要支持群聊，仅需要将MessageInfo中增加用户数据集合的对象，并在item渲染时将用户的ID和头像uri匹配，即可达到多人聊天的场景。

### 3、接入用户数据集合

当前微信的用户数据对象包括客户昵称、头像、朋友圈集合等数据，如果SDK需要接入用户数据集合仅需要在MessageInfo中新增用户数据对象，并在ViewHolder中做对应的数据匹配。



## 四、兼容性解析

### 1、配置兼容性解析

SDK中使用了AndroidX的recyclerview，需要APP开发者确认有配置gradle

```groovy
dependencies {    
    implementation fileTree(dir: 'libs', include: ['*.aar']) //索引到aar即可  
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
}
```

### 2、数据兼容性解析

SDK中使用的唯一数据类是MessageInfo，其中操作不当导致数据类型无法匹配时，会提示消息格式错误，并给出默认显示，做到消息类型错误时能够界面提示和日志提示。

### 3、SDK冲突解析

本SDK以标准模式启动新界面，可支持后台刷新消息。

未使用Service；

未使用BroadcastReceiver；

未使用AIDL；

未使用插件；

未使用provider（若实现第三方分享功能可能需要）；

目前未预知与其他SDK可能存在的冲突。
