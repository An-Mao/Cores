# **Cores**

[简体中文](https://github.com/An-Mao/Cores/blob/main/README_ZH.MD)

This mod has a built-in [Core](https://github.com/An-Mao/JavaDevCore) library.

A library mod developed for Minecraft mod development. It packages numerous frequently used, repetitive code blocks and offers several practical features.
Regular players can download the release builds via [[CurseForge](https://www.curseforge.com/minecraft/mc-mods/cores)] or [[Modrinth](https://modrinth.com/mod/cores)].

## Player Features
- **Attribute Fix** : Modifies the default, maximum, and minimum values of attributes. This can be adjusted via the configuration file.
- **Experience Fix** : Modifies experience deduction. When deducting experience levels, it deducts the exact amount of experience points required to level up from level 0 to the target level, instead of using the vanilla calculation of (current level - target level) points.
  Both of the above features can be disabled via the configuration file.
- **Network Listening** : Once a Mod registers a path, it can be accessed via a local port.
- - For security reasons, this feature is disabled by default but can be enabled via configuration.
- - If enabled via a command, it will revert to being disabled upon the next startup.
- - It automatically shuts down after a period of inactivity; set the timeout to -1 to prevent automatic shutdown.
- - By default, access is restricted to the local host (127.0.0.1).
- - By default, only read access to loaded resource files is permitted.

## Developer Features
- **Color Schemes** : When using this mod's `Screen` or certain rendering-related code, you can customize the color palette by changing the color scheme (as shown in the images below).
  Press the **Home** key to open the configuration interface:
  ![Home Screen](https://github.com/An-Mao/Cores/blob/main/image/home_screen.png?raw=true)
  Select a color scheme:
  ![Select Color Scheme](https://github.com/An-Mao/Cores/blob/main/image/home_screen_select_color_scheme.png?raw=true)
  Save and view the results:
  ![Amber](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_amber.png?raw=true)
  ![Sunset](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_sunset.png?raw=true)
  ![Teal](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_teal.png?raw=true)
  ![Twilight](https://github.com/An-Mao/Cores/blob/main/image/home_screen_change_twilight.png?raw=true)
- **Network Listening** : Listens on port `4444` and returns the registered content when accessed by players.
```java
package dev.anye.mc.cores.am.listen;
// For detailed code, please check the dev.anye.mc.cores.am.listen package

// Here is an ImageListen instance  
public class ImageListen extends Listen {

	protected ImageListen() {
		super("assets/cores/html/image/",// Resource path (optional). If provided, files under this path will be automatically loaded upon access.
                "image",// Registration path, used to access this instance (e.g., 127.0.0.1:4444/image/index.html)
                300// Shutdown timeout (seconds). Closes this listener after a period of inactivity.
        );
	}
	// This method has a default implementation that dispatches POST and GET requests to their respective handlers; you can also override it to implement your own logic.
	public void handle(HttpExchange exchange){}
}

// How to register
public class ListenRegister {
	// Get registry
	public static final Register<Listen> LISTEN_REGISTER = new Register<>(Cores.MOD_ID, "listen", builder -> builder.sync(false));
	// Register instances
	public static final DeferredHolder<Listen,ImageListen> IMAGE_LISTEN = LISTEN_REGISTER.register("image", ImageListen::new);
	//public static final DeferredHolder<Listen,ExampleListen> EXAMPLE_LISTEN = LISTEN_REGISTER.register("example", ExampleListen::new);
    
    // Call this method during mod initialization
	public static void register(IEventBus eventBus){
		LISTEN_REGISTER.register(eventBus);
	}
	
	
	// Assuming this is your Mod initialization function (constructor)
	public Cores(IEventBus modEventBus, ModContainer modContainer) {
		ListenRegister.register(modEventBus);
	}
}
```

## Support status by version
    B : Bug fixes only
    X : No further updates
    U : Updates
    L ：Low-frequency updates
    Trend : U -> L -> B -> X
| Version                                | Forge                       | Neoforge                    | Fabric                   |
|----------------------------------------|-----------------------------|-----------------------------|--------------------------|
| <font color=yellow>1.20.1</font>       | <font color=yellow>B</font> | <font color=yellow>B</font> | <font color=red>X</font> |
| <font color=red>1.20.2 - 1.21.0</font> | <font color=red>X</font>    | <font color=red>X</font>    | <font color=red>X</font> |
| <font color=yellow>1.21.1</font>       | <font color=yellow>B</font> | <font color=yellow>B</font> | <font color=red>X</font> |
| <font color=red>1.21.2-26.0</font>     | <font color=red>X</font>    | <font color=red>X</font>    | <font color=red>X</font> |
| <font color=blue>26.1</font>           | <font color=blue>L</font>   | <font color=blue>L</font>   | <font color=red>X</font> |
| **<font color=green>26.2</font>**      | <font color=green>U</font>  | <font color=green>U</font>  | <font color=red>X</font> |
