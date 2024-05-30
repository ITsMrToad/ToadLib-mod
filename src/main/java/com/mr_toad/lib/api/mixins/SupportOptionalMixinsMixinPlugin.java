package com.mr_toad.lib.api.mixins;

import com.mr_toad.lib.core.ToadLib;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

//Use this class as mixin plugin if you mod contains '@OptionalMixin'
public abstract class SupportOptionalMixinsMixinPlugin implements IMixinConfigPlugin {

    public static final Marker TOAD_MXS = MarkerFactory.getMarker("MixinsByToadLib");

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        ClassNode node;
        try {
            node = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName);
        } catch (ClassNotFoundException | IOException e) {
            ToadLib.LOGGER.warn(TOAD_MXS, "Cannot find class node", e);
            return false;
        }

        if (node != null && node.invisibleAnnotations != null) {
            for (AnnotationNode annotationNode : node.invisibleAnnotations) {
                if (annotationNode.desc.equals("L" + OptionalMixin.class.getName().replace('.', '/') + ";")) {
                    List<Object> values = annotationNode.values;
                    boolean flag = values.size() < 4 || (boolean) values.get(3);
                    try {
                        String name = values.get(1).toString();
                        MixinService.getService().getBytecodeProvider().getClassNode(name);
                        ToadLib.LOGGER.debug(TOAD_MXS, "Find optional mixin {}", name);
                        if (!flag) {
                            return false;
                        }
                    } catch (ClassNotFoundException | IOException e) {
                        ToadLib.LOGGER.warn(TOAD_MXS, "Cannot find class node", e);
                        if (flag) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return Collections.emptyList();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}
