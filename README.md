# About 🐸

This Lib required for ALL my mods

### <u>Features</u>⭐ : 

* Property utilities
* BlockAndItem utilities
* WorldGen utilities
* Client utils
* Entity Abstractions
* Config
* Interpolations
* Java utils
* And outer

### <u>Announcements</u>📢 :

* EndlessJourney [not finished] (8%)
* VillageUpgrade [not finished] (50%)
* Palladium [Finished] 💠
* H+ [Finished] 💠
* EBE(EnlightenedBlockEntities) [Finished]💠
* GPUTape [Finished] 💠


# Quick Guide For Developers 📖

How to do add my library to dependencies...

## 1. Add `maven.modrinth` to `repositories`
```groovy
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
* For example: version - 1.0.2 

## 3. Add ToadLib to `dependencies` in `mods.toml`
```toml
[[dependencies.{yourModId}]]
modId = "toadlib"
mandatory = true
versionRange = "[{version},)"
ordering = "NONE"
side = "BOTH"
```

* Replace {version} with the desired version of ToadLib.
* For example: {version} - 1.0.2 ; {modid} - toadmod
* ❗NOTICE❗ version from step 2. >= {version} in step 3. Outherwise it will lead to exception
* `build` your mod


This is all. Enjoy your use



