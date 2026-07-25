# About 🐸
![fo](https://cdn.modrinth.com/data/cached_images/8457cf2b01e18302ef61da3256328bfb4b94ad14.png)
![fa](https://cdn.modrinth.com/data/cached_images/6c152554bb3141433b6dca087131f96f00a876ba.png)
![qu](https://cdn.modrinth.com/data/cached_images/554809ee9ae740db0707b7cd1420d937b490cd6b.png)
![neo](https://cdn.modrinth.com/data/cached_images/5d6378864df2d8f2699289ec7f439e26b5c8db60.png)
<br/>
Library for my mods. Contains utilities & abstractions.
MtJava part of toadlib - custom lib for java.
Required for ALL(Exclude forks) my mods.

### Mods📑 :

* VillageUpgrade [unfinished] (50%) - Village/Overworld content
* EndlessJourney [unfinished] (8%) - End update
* [MovieMaker](https://modrinth.com/mod/moviemaker) [Alpha] ✨ - Large-scale creative mod
* [H+](https://modrinth.com/mod/h_plus) [Finished] 💠 - Content and hardcore
* [Palladium](https://modrinth.com/mod/mpalladium) [Finished] 💠- Optimization
* [EBE(EnlightenedBlockEntities)]([https://modrinth.com/mod/ebe-forge])[DISCONTINUED] 🟥
* [GPUTape](https://modrinth.com/mod/gputape/) [Finished] 💠- Optimization

**Available languanges:** 🌐
- English(en_us) - Mr.Toad
- Russian(ru_ru) - Mr.Toad
- Turkish(tr_tr) - MissionWAR
- Ukrainian(uk_ua) - StarmanMine142
<br/>
Want to add localization? Welcome to pull requests!

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
```groovy
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


Read more about the integration [here](https://github.com/ITsMrToad/ToadLib-mod/wiki/Lib-integration)



