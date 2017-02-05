package com.notifly.warlords.counter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Dan on 02/02/2017.
 */
public class healing {

    public static Integer totalHealing = 0;
    public static Integer healingDigit = 0;
    public static Integer healing = 0;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void displayMsg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    @SubscribeEvent
    public static void healingMethod (String message){

        // Healing counter

        if (warlordsCounter.warlords == "True") {

            if (message.contains("Your ") && message.contains(" healed ") && message.contains(" for ") && message.contains(" health.")) {

                if (!message.contains(" critically ")) {
                    String endOfMsg = message.substring(message.length() - 12, (message.length()));
                    if (Character.isDigit(endOfMsg.charAt(0))) {
                        healingDigit = 1000; // healing > 1000
                        healing = Integer.parseInt(endOfMsg.substring(0, 4));
                        totalHealing += healing;
                    }

                    if (Character.isWhitespace(endOfMsg.charAt(0))) {
                        healingDigit = 100; // healing >= 100, healing < 1000
                        healing = Integer.parseInt(endOfMsg.substring(1, 4));
                        totalHealing += healing;
                    }

                    if (endOfMsg.startsWith("r")) {
                        healingDigit = 10; // healing >= 10, healing < 100
                        healing = Integer.parseInt(endOfMsg.substring(2, 4));
                        totalHealing += healing;
                    }

                    if (endOfMsg.startsWith("o")) {
                        healingDigit = 1; // damage < 10
                        healing = Integer.parseInt(endOfMsg.substring(3, 4));
                        totalHealing += healing;
                    }
                }

                if (message.contains(" critically healed ")) {
                    String endOfMsg = message.substring(message.length() - 13, (message.length()));

                    if (Character.isDigit(endOfMsg.charAt(0))) {
                        healingDigit = 1000; // healing > 1000
                        healing = Integer.parseInt(endOfMsg.substring(0, 4));
                        totalHealing += healing;
                    }

                    if (Character.isWhitespace(endOfMsg.charAt(0))) {
                        healingDigit = 100; // healing >= 100, healing < 1000
                        healing = Integer.parseInt(endOfMsg.substring(1, 4));
                        totalHealing += healing;
                    }
                }
            }
        }
    }
}
