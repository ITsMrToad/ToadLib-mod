# 1.0.6 Version

Add🔧:
* New logo!
* connected mixins
* dir '/event'
* dir '/time'
* dir '/damagesources'
* Tags: end_mobs, nether_mobs, zombies 
* ChunkTickEvent
* ToadEventFactory
* EntityDataContainer
* BlowUpDataContaine
* BlowUpGoal
* MobFollowAdultMobGoal
* AttackTargetIfAccessedGoal
* StealHPGoal
* DifficultyPredicates
* SpawnLingeringCloudData
* FloatCooldown
* IntegerCooldewn
* DamageSourceWithRandomMessage
* ServerLevelMixin

Remove✂️:
* Boolean1N2 ; Reason : Useless
* NetherMob ; Reason : Replcaed with tag("nether_mobs")

Refactor✏️:
* build.gradle
* accesstransformer.cfg
* ToadEntityUtils
* GenerationWorker
* FenceGateInteractGoal
* ItemStackCollect

# 1.0.5 Version

Add🔧:
* Boolean1N2
* ToadlyArmorItem
* FilledSpawnEggItem 

Remove✂️:
* IFrezzer ; Reason : Useless
* IPlayer ; Reason : Useless

Refactor✏️:
* ToadlyArmorMaterial
* ToadBlockUtils
* EndWaterMobInteractive
* CapeableMob

# 1.0.4 Version

Add🔧:
* dir '/block'
* dir '/item'
* dir '/tab'
* FilledBlockItem
* FilledItem
* FilledBlock

Remove✂️:
* IRobber ; Reason : I found a better way to implement this

Refactor✏️:
* EndWaterBehaviors
* ColorHandlers
* ToadlyArmorMaterial
* ToadlyItemTier
* CrossEntityOpenersCounter
* ICrossEntityContainer
* BlockPropertyValue
* ItemPropertyValue
* ICreativeTabFiller


# 1.0.3 Version

Now available on 1.19.3 🎉

Add🔧:
* IFletchingTableMenu
* ICrossEntityContainer
* CrossEntityOpenersCounter
* ToadClientUtils
* AbstractVIModel

Fixes🔥:
* IFreezer

Refactor✏️:
* ToadlyDataProvider
* BlockPropertyValues
* BehaviorHelper
* ToadOuterUtils

# 1.0.2 Version
Add🔧:
* IRobber
* IPlayer

Refactor✏️:
* IFreezer

# 1.0.1 Version
Fixes🔥:
* BlockPropertyValues
* ItemPropertyValues
* ToadlyDataProvider
* TemplateHelper
* AbstractCapeableIllager
