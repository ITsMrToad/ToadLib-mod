import com.mr_toad.villageupgrade.core.VillageUpgrade;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ToadlyClientUtils {

  public static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

  public static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(new ResourceLocation(VillageUpgrade.MODID, name), layer);
    }



}
