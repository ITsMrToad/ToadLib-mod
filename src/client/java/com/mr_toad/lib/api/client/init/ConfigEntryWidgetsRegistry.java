package com.mr_toad.lib.api.client.init;

import com.mr_toad.lib.api.client.screen.config.ToadConfigScreen;
import com.mr_toad.lib.api.client.screen.config.widget.ConfigEntryWidgetMaker;
import com.mr_toad.lib.api.client.screen.config.widget.UnexpectedEntry;
import com.mr_toad.lib.api.client.screen.ex.widget.ExCheckboxV2;
import com.mr_toad.lib.api.client.screen.ex.widget.ExEditBox;
import com.mr_toad.lib.api.client.screen.ex.widget.ExSlider;
import com.mr_toad.lib.api.client.screen.ex.widget.color.ColorCellsWidget;
import com.mr_toad.lib.api.client.screen.ex.widget.color.ColorPaletteWidget;
import com.mr_toad.lib.api.client.utils.StringWidgetBuilder;
import com.mr_toad.lib.api.config.entry.ColorEntry;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.EnumEntry;
import com.mr_toad.lib.api.config.entry.primitive.NumericEntry;
import com.mr_toad.lib.api.config.entry.primitive.StringEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.MtMath;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ConfigEntryWidgetsRegistry {

    private static final Object2ObjectMap<ConfigEntryType<?>, ConfigEntryWidgetMaker<?, ?, ?>> MAKERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<ConfigEntryType<?>, Consumer<GuiEventListener>> TICKERS = new Object2ObjectOpenHashMap<>();

    @ApiStatus.Internal
    public static void init() {
        initMakers();
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    private static void initMakers() {
        registerMaker(ConfigEntryTypes.BOOL, boolean.class, (owner, nextX, entry) -> {
            ExCheckboxV2 checkbox = new ExCheckboxV2(nextX, 0, 140, entry.getTitle(), Minecraft.getInstance().font, entry.getDisplayValue(), b -> {
                if (entry.get() != b) {
                    entry.setValue(b);
                    owner.fillEntries();
                }
            });
            checkbox.setTooltip(tooltip(entry));
            checkbox.active = !entry.isDeprecated();
            return checkbox;
        });

        registerMaker(ConfigEntryTypes.STRING, String.class, (owner, nextX, entry) -> {
            AtomicReference<String> lastContent = new AtomicReference<>("");
            ExEditBox editBox = new ExEditBox(Minecraft.getInstance().font, nextX, 0, 220, 20, entry.getTitle());
            editBox.setTooltip(tooltip(entry));
            if (entry instanceof StringEntry stringEntry) {
                editBox.setMaxLength(stringEntry.getMaxLength());
            } else {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to apply custom settings of string value: '{}'", entry);
            }
            editBox.setValue(entry.getDisplayValue());
            editBox.setResponder(s -> {
                if (!lastContent.get().equals(s)) {
                    if (!entry.get().equals(s)) {
                        entry.setValue(s);
                        owner.fillEntries();
                    }
                    lastContent.set(s);
                }
            });
            editBox.active = !entry.isDeprecated();
            return editBox;
        });

        registerMaker(ConfigEntryTypes.ENUM, Enum.class, (owner, nextX, entry) -> {
            if (entry instanceof EnumEntry<?> enumEntry) {
                return makeEnum(nextX, owner, enumEntry);
            } else {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "'{}' is not enum entry!", entry);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.BYTE, byte.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().byteValue(), numericEntry.getMax().byteValue(), entry.getDisplayValue(), numericEntry.getStep(), num -> {
                        entry.setValue(MtMath.bfloor(num.getValue()));
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not byte!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.SHORT, short.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().shortValue(), numericEntry.getMax().shortValue(), entry.getDisplayValue(), numericEntry.getStep(), num -> {
                        entry.setValue(MtMath.sfloor(num.getValue()));
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not short!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.INT, int.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().intValue(), numericEntry.getMax().intValue(), entry.getDisplayValue(), numericEntry.getStep(), num -> {
                        entry.setValue(Mth.floor(num.getValue()));
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not int!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.LONG, long.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().longValue(), numericEntry.getMax().longValue(), entry.getDisplayValue(), numericEntry.getStep(), num -> {
                        entry.setValue(Mth.lfloor(num.getValue()));
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not long!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.BIG_DECIMAL, BigDecimal.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().floatValue(), numericEntry.getMax().floatValue(), entry.getDisplayValue().floatValue(), numericEntry.getStep(), num -> {
                        entry.setValue(BigDecimal.valueOf(num.getValue()));
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not float/double!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.DEGREE, float.class, (owner, nextX, entry) -> {
            ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), Component.literal("°"), -180.0F, 180, entry.getDisplayValue(), 1F, num -> {
                entry.setValue(num.getValue());
                owner.fillEntries();
            });
            slider.setTooltip(tooltip(entry));
            slider.active = !entry.isDeprecated();
            return slider;
        });

        registerMaker(ConfigEntryTypes.PERCENT, double.class, (owner, nextX, entry) -> {
            ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), Component.literal("%"), 0.0F, 1.0F, entry.getDisplayValue().floatValue(), 0.01F, num -> {
                entry.setValue((double) num.getValue());
                owner.fillEntries();
            });
            slider.setTooltip(tooltip(entry));
            slider.active = !entry.isDeprecated();
            return slider;
        });

        registerMaker(ConfigEntryTypes.COLOR, int.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof ColorEntry colorEntry) {
                    ColorEntry.Mode mode = colorEntry.getMode();
                    return switch (mode) {
                        case PALETTE -> {
                            ColorPaletteWidget widget = new ColorPaletteWidget(Minecraft.getInstance(), nextX, 0, colorEntry.getDisplayValue(), entry.getTitle(), e -> {
                                entry.setValue(e);
                                owner.fillEntries();
                            });
                            widget.setTooltip(tooltip(entry));
                            widget.setAlphaAllowed(colorEntry.isAllowAlpha());
                            widget.active = !entry.isDeprecated();
                            yield widget;
                        }
                        case CELLS -> {
                            if (colorEntry.getCells().isEmpty()) {
                                throw new ConfigException("Cells color config entry is empty!");
                            } else {
                                int cellSize = colorEntry.getCellSize();
                                if (cellSize == 0) {
                                    ToadLib.LOGGER.warn(ToadLib.CONFIG, "Cells of '{}' is invisible!", entry);
                                }
                                ColorCellsWidget widget = ColorCellsWidget.of(colorEntry.getCells(), e -> {
                                    entry.setValue(e);
                                    owner.fillEntries();
                                }).addTitle(entry.getTitle()).size(nextX, 0, 90, 90).setCellSize(cellSize).build();
                                widget.setTooltip(tooltip(entry));
                                widget.active = !entry.isDeprecated();
                                yield widget;
                            }
                        }
                    };
                } else {
                    throw new ConfigException("'" + entry + "' isn't color!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });
        registerMaker(ConfigEntryTypes.PAGE, Component.class, (owner, _, entry) -> new StringWidgetBuilder(entry.getDisplayValue()).center(owner.widgetSelectionList.getRowWidth()).build());

        registerMaker(ConfigEntryTypes.DOUBLE, Double.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, (float) numericEntry.getMin().doubleValue(), (float) numericEntry.getMax().doubleValue(), (float) entry.getDisplayValue().doubleValue(), numericEntry.getStep(), num -> {
                        entry.setValue((double) num.getValue());
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not float/double!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });

        registerMaker(ConfigEntryTypes.FLOAT, Float.class, (owner, nextX, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, 0, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().floatValue(), numericEntry.getMax().floatValue(), entry.getDisplayValue(), numericEntry.getStep(), num -> {
                        entry.setValue(num.getValue());
                        owner.fillEntries();
                    });
                    slider.setTooltip(tooltip(entry));
                    slider.active = !entry.isDeprecated();
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not float/double!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, 0);
            }
        });
    }

    public static <T, E extends ConfigEntry<T, E>, W extends GuiEventListener & Renderable> void registerMaker(ConfigEntryType<?> type, Class<T> of, ConfigEntryWidgetMaker<T, E, W> maker) {
        MAKERS.put(type, maker);
        if (ToadLib.printDebug()) {
            ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered maker for '{}' of class '{}'", type, of);
        }
    }

    public static void registerTicker(ConfigEntryType<?> type, Consumer<GuiEventListener> ticker) {
        TICKERS.put(type, ticker);
        if (ToadLib.printDebug()) {
            ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered ticker for '{}'", type);
        }
    }

    public static Optional<ConfigEntryWidgetMaker<?, ?, ?>> getMakerOf(ConfigEntryType<?> entryType) {
        return Optional.ofNullable(MAKERS.get(entryType));
    }

    public static Optional<Consumer<GuiEventListener>> getTickerOf(ConfigEntryType<?> entryType) {
        return Optional.ofNullable(TICKERS.get(entryType));
    }

    private static <E extends Enum<E>> CycleButton<@NotNull E> makeEnum(int nextX, ToadConfigScreen owner, EnumEntry<E> entry) {
        CycleButton<@NotNull E> buttonWidget = CycleButton.builder(entry.getNaming(), entry.getDisplayValue()).withValues(entry.getValues()).withTooltip(_ -> tooltip(entry)).create(nextX, 0, 220, 20, entry.getTitle(), (_, v) -> {
            if (!entry.get().equals(v)) {
                entry.setValue(v);
                owner.fillEntries();
            }
        });
        buttonWidget.active = !entry.isDeprecated();
        return buttonWidget;
    }

    private static Tooltip tooltip(ConfigEntry<?, ?> entry) {
        return entry.getDescription() == null ? null : Tooltip.create(entry.getDescription());
    }
}
