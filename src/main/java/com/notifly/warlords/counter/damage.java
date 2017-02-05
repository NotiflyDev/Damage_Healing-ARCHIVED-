package com.notifly.warlords.counter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class damage{
    public static Integer totalDamage = 0;
    public static Integer damageDigit = 0;
    public static Integer damage = 0;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void displayMsg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    @SubscribeEvent
    public static void damageMethod (String message){

        // Damage counter

        if (warlordsCounter.warlords == "True") {

            if (message.contains("You") && message.contains(" hit ") && message.contains(" for ") && message.contains(" damage.")) {

                if (!message.contains(" critical ")) {
                    String endOfMsg = message.substring(message.length() - 12, (message.length()));
                    if (Character.isDigit(endOfMsg.charAt(0))) {
                        damageDigit = 1000; // damage > 1000
                        damage = Integer.parseInt(endOfMsg.substring(0, 4));
                        totalDamage += damage;
                    }

                    if (Character.isWhitespace(endOfMsg.charAt(0))) {
                        damageDigit = 100; // damage >= 100, damage < 1000
                        damage = Integer.parseInt(endOfMsg.substring(1, 4));
                        totalDamage += damage;
                    }

                    if (endOfMsg.startsWith("r")) {
                        damageDigit = 10; // damage >= 10, damage < 100
                        damage = Integer.parseInt(endOfMsg.substring(2, 4));
                        totalDamage += damage;
                    }

                    if (endOfMsg.startsWith("o")) {
                        damageDigit = 1; // damage < 10
                        damage = Integer.parseInt(endOfMsg.substring(3, 4));
                        totalDamage += damage;
                    }
                }

                if (message.contains("! critical damage.")) {
                    String endOfMsg = message.substring(message.length() - 22, (message.length()));

                    if (Character.isDigit(endOfMsg.charAt(0))) {
                        damageDigit = 1000; // damage => 1000
                        damage = Integer.parseInt(endOfMsg.substring(0, 4));
                        totalDamage += damage;
                    }

                    if (Character.isWhitespace(endOfMsg.charAt(0))) {
                        damageDigit = 100; // damage >= 100, damage < 1000
                        damage = Integer.parseInt(endOfMsg.substring(1, 4));
                        totalDamage += damage;
                    }
                }
            }
        }
    }
}

