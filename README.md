# About 🐸

This Lib required for ALL my mods

### <u>Features</u>⭐ : 

* Property utilities
* BlockAndItem utilities
* WorldGen utilities
* Entity Abstractions 
* And outer

### <u>Announcements</u>📢 :

* VillageUpgrade [not finished] (40%) The mod turned out to be extremely unstable and I will have to rewrite it 😢 
* EndlessJourney [not finished] (8%)
* H+ [Beta release - 23 January] (99%) 💠
* Storm [not finished] (50%)
* And Outer


# Quick Guide For Developers 📖

How to do add my library to dependencies...

## 1. Add `maven.modrinth` to `repositories`
```
repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = "https://api.modrinth.com/maven"
            }
        }
        forRepositories(fg.repository) 
        filter {
            includeGroup "maven.modrinth"
        }
    }
}
```

## 2. Add ToadLib<version> to `dependencies` in `build.gradle`
```
implementation fg.deobf("maven.modrinth:toadlib:<version>")
```

* Replace <version> with the desired version of ToadLib.
* For example: version - 1.0.1

## 3. Add ToadLib to `dependencies` in `mods.toml`
```
[[dependencies.{yourModId}]]
modId = "toadlib"
mandatory = true
versionRange = "[{version},)"
ordering = "NONE"
side = "BOTH"
```

* Replace {version} with the desired version of ToadLib.
* For example: {version} - 1.0.1 ; {modid} - toadmod
* ❗NOTICE❗ version from step 2. >= {version} in step 3. Outherwise it will lead to exception
* `build` your mod


This is all. Enjoy your use

### [Support Me On Boosty⚡](https://boosty.to/mr_toad)




