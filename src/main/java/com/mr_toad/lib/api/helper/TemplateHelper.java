package com.mr_toad.lib.api.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class TemplateHelper {

    public static void addBuildingToPool(Registry<StructureTemplatePool> templatePools, Registry<StructureProcessorList> processorLists, ResourceLocation rl, String name, int weight) {
        StructureTemplatePool pool = templatePools.get(rl);
        if (pool == null) return;

        ResourceLocation emptyProcessor = new ResourceLocation("minecraft", "empty");
        Holder<StructureProcessorList> processorHolder = processorLists.getHolderOrThrow(ResourceKey.create(Registries.PROCESSOR_LIST, emptyProcessor));

        SinglePoolElement piece = SinglePoolElement.single(name, processorHolder).apply(StructureTemplatePool.Projection.RIGID);

        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }

        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }
}
