## **📖 Quick Guide For Developers**

if you want to add my mod for as a library, you should:

# 1. Add Maven.Jaackson to Repositories

```
repositories {
    maven {
        url = "https://maven.jaackson.me"
    }
}
```

# 2. Add ToadLib to Dependencies

```
dependencies {
    implementation fg.deobf("com.mr_toad.lib:[version]")
}
```
Replace [version] with the desired version of ToadLib.
Version is written {a}-{b}

Where {a} is a version of Minecraft & Where {b} is a version of ToadLib

### For example: 1.19.4-1.0.1

# 3. Add Block of Dependencies in mods.toml

```
[[dependencies.{modId}]]
    modId = "toadlib"
    mandatory = true
    versionRange = "[{version},)" 
    ordering = "BEFORE"
    side = "BOTH"
```
{modid} - your id of mod
{version} - range of accepted versions of Toad Lib

❗Warning❗ if you {version} will be lower than the [version] 
indicated in the second paragraph, this will cause exception


### for example: {modid} - toadmod || {version} - 1.0.1

After doing all these steps, you can build 🔨 your mod with my lib.

Enjoy your use 🐸
