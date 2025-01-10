package com.mr_toad.lib.api.config.entry.type;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigEntryTypes {

    public static final ConfigEntryType BYTE = new ConfigEntryType("byte");
    public static final ConfigEntryType SHORT = new ConfigEntryType("short");
    public static final ConfigEntryType INT = new ConfigEntryType("int");
    public static final ConfigEntryType LONG = new ConfigEntryType("long");
    public static final ConfigEntryType BOOL = new ConfigEntryType("bool");
    public static final ConfigEntryType BIG_DECIMAL = new ConfigEntryType("double_float");
    public static final ConfigEntryType DEGREE = new ConfigEntryType("degree");
    public static final ConfigEntryType PERCENT = new ConfigEntryType("percent");
    public static final ConfigEntryType STRING = new ConfigEntryType("string");
    public static final ConfigEntryType COLOR = new ConfigEntryType("color");
    public static final ConfigEntryType ENUM = new ConfigEntryType("enum");
    public static final ConfigEntryType PAGE = new ConfigEntryType("page");

}
