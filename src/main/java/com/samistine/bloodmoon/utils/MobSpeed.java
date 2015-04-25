/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samistine.bloodmoon.utils;

/**
 *
 * @author Samuel
 */

public enum MobSpeed {
    BLAZE("Blaze", )
    ZOMBIE("Zombie", 0),
    SKELETON("Skeleton", 0);
    
    private final String name;
    private final int speed;

    private MobSpeed(final String name, final int speed) {
        this.name = name;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}