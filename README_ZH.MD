# **Cores**

[English](https://github.com/An-Mao/Cores/blob/main/README.MD)

此Mod已内置[Core](https://github.com/An-Mao/JavaDevCore)

为我的世界Mod开发的代码库Mod，内置了许多经常使用的重复性的代码，并提供一些实用的功能。  
普通玩家可以通过【[CurseForge](https://www.curseforge.com/minecraft/mc-mods/cores)】或【[Modrinth](https://modrinth.com/mod/cores)】下载成品    

## 普通功能
 - 属性修复：修改属性的默认值、最大值、最小值。可通过配置文件调整。
 - 经验修复：修改扣除经验等级时，扣除从0至所扣等级的点数，而不是（已有等级-扣除等级）
上述功能均可通过配置文件关闭。
 - 网络监听：当Mod注册一个路径之后可以通过本地端口进行访问。
 - - 出于安全考虑，此功能默认禁用，可以通过更改配置来启用。
 - - 通过命令启用的，下次启动时依旧会禁用。
 - - 一段时间不活动，会自动关闭，如果不想超时关闭，将timeout设置为-1。
 - - 默认仅允许本地访问（127.0.0.1）。
 - - 默认仅允许读取加载的资源文件。

## 代码功能
 - 配色方案：在使用本Mod的Screen或部分渲染相关的代码时，可以通过更改配色方案来更改配色（如图所示）。
按Home键打开配置界面
![Home界面](https://github.com/An-Mao/Cores/blob/main/image/home_screen.png?raw=true)
选择配色方案
![配色方案选择](https://github.com/An-Mao/Cores/blob/main/image/home_screen_select_color_scheme.png?raw=true)
保存后查看效果
![Amber](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_amber.png?raw=true)
![Sunset](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_sunset.png?raw=true)
![Teal](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_teal.png?raw=true)
![Twilight](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_twilight.png?raw=true)
 - 网络监听：监听端口（4444），当玩家访问时返回注册的内容。
```java
package dev.anye.mc.cores.am.listen;
// 详细代码请查看dev.anye.mc.cores.am.listen部分

// 下面是一个ImageListen实例  
public class ImageListen extends Listen {

	protected ImageListen() {
		super("assets/cores/html/image/",//资源路径，非必须，如果有，在访问时，会自动加载路径下的文件
                "image",//注册路径，需要通过此路径来访问此实例，例如127.0.0.1:4444/image/index.html
                300//关闭时间，一段时间不活动后关闭此监听
        );
	}
	//此方法已默认实现将post,get请求分发到对应方法，你也可以覆盖此方法来自己实现
	public void handle(HttpExchange exchange){}
}

// 如何注册
public class ListenRegister {
	//获取注册表
	public static final Register<Listen> LISTEN_REGISTER = new Register<>(Cores.MOD_ID, "listen", builder -> builder.sync(false));
	//注册实例
	public static final DeferredHolder<Listen,ImageListen> IMAGE_LISTEN = LISTEN_REGISTER.register("image", ImageListen::new);
	//public static final DeferredHolder<Listen,ExampleListen> EXAMPLE_LISTEN = LISTEN_REGISTER.register("example", ExampleListen::new);
    
    //在mod初始化中调用此方法
	public static void register(IEventBus eventBus){
		LISTEN_REGISTER.register(eventBus);
	}
	
	
	//假设这是你的Mod初始化函数
	public Cores(IEventBus modEventBus, ModContainer modContainer) {
		ListenRegister.register(modEventBus);
	}
}
```

## 各版本支持情况
    B : 仅修复bug
    X : 不再更新
    U : 正常更新
    L ：低更新状态
    趋势 : U -> L -> B -> X
| Version                                | Forge                       | Neoforge                    | Fabric                   |
|----------------------------------------|-----------------------------|-----------------------------|--------------------------|
| <font color=yellow>1.20.1</font>       | <font color=yellow>B</font> | <font color=yellow>B</font> | <font color=red>X</font> |
| <font color=red>1.20.2 - 1.21.0</font> | <font color=red>X</font>    | <font color=red>X</font>    | <font color=red>X</font> |
| <font color=yellow>1.21.1</font>       | <font color=yellow>B</font> | <font color=yellow>B</font> | <font color=red>X</font> |
| <font color=red>1.21.2-26.0</font>     | <font color=red>X</font>    | <font color=red>X</font>    | <font color=red>X</font> |
| <font color=blue>26.1</font>           | <font color=blue>L</font>   | <font color=blue>L</font>   | <font color=red>X</font> |
| **<font color=green>26.2</font>**      | <font color=green>U</font>  | <font color=green>U</font>  | <font color=red>X</font> |
