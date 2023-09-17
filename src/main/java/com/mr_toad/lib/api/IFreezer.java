package com.mr_toad.lib.api;

import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.parser.Entity;

public interface IFreezer<T extends Mob> {
    @Nullable T getIceCube();
    
    void setIceCube(@Nullable T target);
}
