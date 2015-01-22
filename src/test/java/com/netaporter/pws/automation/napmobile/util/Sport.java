package com.netaporter.pws.automation.napmobile.util;

public enum Sport {

    Sport("Sport", 0),
    All_sportswear("All sportswear", 1),
    Apres_sport("Apres sport", 2),
    Equestrian("Equestrian", 3),
    Golf("Golf", 4),
    Gym_and_crossfit("Gym and crossfit", 5),
    Outdoor("Outdoor", 6),
    Run("Run", 7),
    Swim_and_surf("Swim and surf", 8),
    Tennis("Tennis", 9),
    Yoga_and_dance("Yoga and dance", 9);



    private final String value;
    private final int shopIndex;

    private Sport(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}


