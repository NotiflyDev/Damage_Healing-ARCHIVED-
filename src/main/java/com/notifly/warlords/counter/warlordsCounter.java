package com.notifly.warlords.counter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Mod(modid = warlordsCounter.MODID, version = warlordsCounter.VERSION, name = warlordsCounter.NAME)
public class warlordsCounter {
    public static final String MODID = "warlordsCounter";
    public static final String VERSION = "1.1";
    public static final String NAME = "Warlords Damage/Healing Counter";
    public static String warlords = "False";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void displayMsg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (message.contains("The gates will fall in ")) {
            damage.totalDamage = 0; // resets damage counter
            healing.totalHealing = 0;   // resets healing counter
            warlords = "True";
        }

        if (message.contains(" joined the lobby!")) {
            warlords = "False";
        }

        damage.damageMethod(message);
        healing.healingMethod(message);

    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if (warlords == "True") {
            FontRenderer fRender = Minecraft.getMinecraft().fontRendererObj;
            fRender.drawString(EnumChatFormatting.RED + "Damage: " + EnumChatFormatting.BOLD + damage.totalDamage, 5, 5, 0, true);
            fRender.drawString(EnumChatFormatting.GREEN + "Healing: " + EnumChatFormatting.BOLD + healing.totalHealing, 5, 20, 0, true);
        }

    }
}