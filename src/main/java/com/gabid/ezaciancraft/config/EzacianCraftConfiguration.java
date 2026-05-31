package com.gabid.ezaciancraft.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class EzacianCraftConfiguration {
    public static Configuration config;

    public static final String CAT_MACHINES = "Machines";
    public static final String CAT_ESSENTIA = "Essentia";
    public static final String CAT_WORLDGEN = "World Generation";

    //Default Configs and Values
    //Essentia
    public static int wirelessInterfacesMaxWorkThreads = 8;
    public static double wirelessInterfacesFillPenaltyMultiplier = 25d;
    public static double wirelessInterfacesSuctionBonusMultiplier = 10d;

    //Machines
    //*Alchemical Mixer
    public static int mixerBaseProcessingTimeSpeed = 8;

    //*SVM alchemical furnace
    public static int alembicsMaxHeight = 8;
    public static float processSpeedBellowsBonus = 2.5f;
    public static int visMaxCapacity = 512;

    //*Wireless essentia interfaces machines
    public static int wirelessInputInterfaceWorkRadius = 16;
    public static int wirelessOutputInterfaceWorkRadius = 16;

    //*Extended Workbench
    public static int extraDiscountWorkbenchValue = 5;

    public static void initConfig(File file) {
        if (config == null) {
            config = new Configuration(file);
            loadConfigurations();
        }
    }

    public static void loadConfigurations() {
        config.load();
        syncConfigurations();
    }

    public static void syncConfigurations() {
        //Essentia
        //*Wireless related configs
        wirelessInterfacesMaxWorkThreads = config.get(
                CAT_ESSENTIA,
                "wirelessInterfacesMaxWorkThreads",
                8,
                "How many operations of things can do a interface at the same time."
        ).getInt();

        wirelessInterfacesFillPenaltyMultiplier = config.get(
                CAT_ESSENTIA,
                "wirelessInterfacesFillPenaltyMultiplier",
                25d,
                "The multiplier what increases the score penalty of the essentia search in how much is filled."
        ).getDouble();

        wirelessInterfacesSuctionBonusMultiplier = config.get(
                CAT_ESSENTIA,
                "wirelessInterfacesSuctionBonusMultiplier",
                10d,
                "The multiplier what increases the score bonus of the essentia search in how much suction is requesting."
        ).getDouble();


        // ---- //

        //Machines
        //*Alchemical Mixer
        mixerBaseProcessingTimeSpeed = config.get(
                CAT_MACHINES,
                "mixerBaseProcessingSpeedTick",
                8,
                "The max time needed to process the essentia in the Alchemical Mixer."
        ).getInt();

        //*SVM alchemical furnace
        alembicsMaxHeight = config.get(
                CAT_MACHINES,
                "alembicsMaxHeight",
                8,
                "How the max height that can the furnace hold in alembics."
        ).getInt();

        processSpeedBellowsBonus = (float) config.get(
                CAT_MACHINES,
                "processSpeedBellowsBonus",
                2.5D,
                "the base speed multiplied by the bellows for the processing speed."
        ).getDouble();

        visMaxCapacity = config.get(
                CAT_MACHINES,
                "visMaxCapacity",
                512,
                "The max capacity which the furnace can hold."
        ).getInt();

        //*wireless
        wirelessInputInterfaceWorkRadius = config.get(
                CAT_MACHINES,
                "wirelessInputInterfaceWorkRadius",
                16,
                "The max radius that the interface can search for sources in block units.",
                1,
                16
        ).getInt();

        wirelessOutputInterfaceWorkRadius = config.get(
                CAT_MACHINES,
                "wirelessOutputInterfaceWorkRadius",
                16,
                "The max radius that the interface can search for sources in block units.",
                1,
                16
        ).getInt();

        //*extended workbench
        extraDiscountWorkbenchValue = config.get(
                CAT_MACHINES,
                "extraDiscountWorkbenchValue",
                5,
                "The initial base discount value that the workbench can work.",
                1,
                20
        ).getInt();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void save() {
        config.save();
    }
}
