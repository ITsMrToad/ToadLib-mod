package com.mr_toad.lib.api.client.init;

import com.mr_toad.lib.api.client.screen.config.widget.ConfigEntryWidgetMaker;
import com.mr_toad.lib.api.client.screen.config.widget.ConfigPageWidget;
import com.mr_toad.lib.api.client.screen.config.widget.UnexpectedEntry;
import com.mr_toad.lib.api.client.screen.ex.widget.ExCheckbox;
import com.mr_toad.lib.api.client.screen.ex.widget.ExEditBox;
import com.mr_toad.lib.api.client.screen.ex.widget.ExSlider;
import com.mr_toad.lib.api.client.screen.ex.widget.color.ColorCellsWidget;
import com.mr_toad.lib.api.client.screen.ex.widget.color.ColorPaletteWidget;
import com.mr_toad.lib.api.config.entry.ColorEntry;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.CycledEntry;
import com.mr_toad.lib.api.config.entry.primitive.NumericEntry;
import com.mr_toad.lib.api.config.entry.primitive.StringEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.MtMath;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.ApiStatus;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ConfigEntryWidgetsRegistry {

    private static final Object2ObjectMap<ConfigEntryType, ConfigEntryWidgetMaker<?, ?, ?>> MAKERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<ConfigEntryType, Consumer<GuiEventListener>> TICKERS = new Object2ObjectOpenHashMap<>();

    @ApiStatus.Internal
    public static void init() {
        initMakers();
        initTickers();
    }

    @SuppressWarnings("rawtypes")
    private static void initMakers() {
        registerMaker(ConfigEntryTypes.BOOL, boolean.class, (owner, nextX, nextY, entry) -> {
            ExCheckbox checkbox = new ExCheckbox(nextX, nextY, entry.getTitle(), entry.get(), b -> {
                if (entry.get() != b) {
                    entry.setValue(b);
                }
            });
            checkbox.setTooltip(Tooltip.create(entry.getDescription()));
            return checkbox;
        });

        registerMaker(ConfigEntryTypes.STRING, String.class, (owner, nextX, nextY, entry) -> {
            AtomicReference<String> lastContent = new AtomicReference<>("");
            ExEditBox editBox = new ExEditBox(Minecraft.getInstance().font, nextX, nextY, 220, 20, entry.getTitle());
            editBox.setTooltip(Tooltip.create(entry.getDescription()));
            editBox.setResponder(s -> {
                if (!lastContent.get().equals(s)) {
                    if (!entry.get().equals(s)) {
                        entry.setValue(s);
                    }
                    lastContent.set(s);
                }
            });
            editBox.setValue(entry.get());
            if (entry instanceof StringEntry stringEntry) {
                editBox.setFilter(stringEntry.getFilter());
                editBox.setFormatter(stringEntry.getFormatter());
                editBox.setMaxLength(stringEntry.getMaxLength());
            } else {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to apply custom settings of string value: '{}'", entry);
            }
            return editBox;
        });

        registerMaker(ConfigEntryTypes.ENUM, Enum.class, (owner, nextX, nextY, entry) -> {
            if (entry instanceof CycledEntry cycledEntry) {
                return CycleButton.builder(cycledEntry.getNaming()).withValues(cycledEntry.getValues()).withInitialValue(cycledEntry.get()).withTooltip(o -> Tooltip.create(entry.getDescription())).create(nextX, nextY, 220, 20, entry.getTitle(), (b, v) -> {
                    if (!cycledEntry.get().equals(v)) {
                        cycledEntry.setValue((Enum) v);
                    }
                });
            } else {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "'{}' is not enum!", entry);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.BYTE, byte.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().byteValue(), numericEntry.getMax().byteValue(), entry.get(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(MtMath.bfloor(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not byte!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.SHORT, short.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().shortValue(), numericEntry.getMax().shortValue(), entry.get(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(MtMath.sfloor(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not short!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.INT, int.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().intValue(), numericEntry.getMax().intValue(), entry.get(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(Mth.floor(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not int!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.LONG, long.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().longValue(), numericEntry.getMax().longValue(), entry.get(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(Mth.lfloor(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not long!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.LONG, long.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().longValue(), numericEntry.getMax().longValue(), entry.get(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(Mth.lfloor(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not long!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.BIG_DECIMAL, BigDecimal.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof NumericEntry numericEntry) {
                    ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), CommonComponents.EMPTY, numericEntry.getMin().doubleValue(), numericEntry.getMax().doubleValue(), entry.get().doubleValue(), numericEntry.getStep(), num -> {
                        numericEntry.setValue(BigDecimal.valueOf(num.getValue()));
                    });
                    slider.setTooltip(Tooltip.create(entry.getDescription()));
                    return slider;
                } else {
                    throw new ConfigException("'" + entry + "' is not float/double!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

        registerMaker(ConfigEntryTypes.DEGREE, float.class, (owner, nextX, nextY, entry) -> {
            ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), Component.literal("°"), -180.0F, 180, entry.get(), 1F, num -> entry.setValue((float) num.getValue()));
            slider.setTooltip(Tooltip.create(entry.getDescription()));
            return slider;
        });

        registerMaker(ConfigEntryTypes.PERCENT, double.class, (owner, nextX, nextY, entry) -> {
            ExSlider slider = new ExSlider(nextX, nextY, 220, 19, entry.getTitle(), Component.literal("%"), 0.0D, 1.0D, entry.get(), 0.01D, num -> entry.setValue(num.getValue()));
            slider.setTooltip(Tooltip.create(entry.getDescription()));
            return slider;
        });

        registerMaker(ConfigEntryTypes.COLOR, int.class, (owner, nextX, nextY, entry) -> {
            try {
                if (entry instanceof ColorEntry colorEntry) {
                    ColorEntry.Mode mode = colorEntry.getMode();
                    return switch (mode) {
                        case PALETTE -> {
                            ColorPaletteWidget widget = new ColorPaletteWidget(owner.getMinecraft(), nextX, nextY + 5, colorEntry.get(), entry.getTitle(), colorEntry::setValue);
                            widget.setTooltip(Tooltip.create(entry.getDescription()));
                            widget.setAlphaAllowed(colorEntry.isAllowAlpha());
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
                                ColorCellsWidget widget = ColorCellsWidget.of(colorEntry.getCells(), colorEntry::setValue).addTitle(entry.getTitle()).size(nextX, nextY, 90, 90).setCellSize(cellSize).build();
                                widget.setTooltip(Tooltip.create(entry.getDescription()));
                                yield  widget;
                            }
                        }
                    };
                } else {
                    throw new ConfigException("'" + entry + "' isn't color!");
                }
            } catch (ConfigException | ClassCastException | IllegalArgumentException e) {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "Error making '{}'", entry, e);
                return new UnexpectedEntry(nextX, nextY);
            }
        });

       registerMaker(ConfigEntryTypes.PAGE, Component.class, (owner, nextX, nextY, entry) -> new ConfigPageWidget(owner.widgetSelectionList.getWidth() / 2, nextY, entry.get()));
    }

    private static void initTickers() {
        registerTicker(ConfigEntryTypes.STRING, w -> {
            if (w instanceof EditBox editBox) {
                editBox.tick();
            }
        });
    }

    public static<T, E extends ConfigEntry<T, E>, W extends GuiEventListener & Renderable> void registerMaker(ConfigEntryType type, Class<T> of, ConfigEntryWidgetMaker<T, E, W> maker) {
        MAKERS.put(type, maker);
        ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered maker for '{}' of class '{}'", type, of);
    }

    public static void registerTicker(ConfigEntryType type, Consumer<GuiEventListener> ticker) {
        TICKERS.put(type, ticker);
        ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered ticker for '{}'", type);
    }

    public static Optional<ConfigEntryWidgetMaker<?, ?, ?>> getMakerOf(ConfigEntryType entryType) {
        return Optional.ofNullable(MAKERS.get(entryType));
    }

    public static Optional<Consumer<GuiEventListener>> getTickerOf(ConfigEntryType entryType) {
        return Optional.ofNullable(TICKERS.get(entryType));
    }
}
