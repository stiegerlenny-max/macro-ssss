package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class MacromodClient implements ClientModInitializer {

    public static KeyBinding macroKey;

    @Override
    public void onInitializeClient() {
        // Registriert die Taste 'X' für dein Makro
        macroKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.macromod.pvp", 
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_X, // Hier kannst du die Taste anpassen
            "category.macromod"
        ));

        // Prüft jeden Tick, ob die Taste gedrückt ist
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (macroKey.wasPressed() && client.player != null && client.interactionManager != null) {
                
                HitResult hit = client.crosshairTarget;
                
                if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    
                    // Klick 1: Platziert Obsidian oder den Anchor
                    client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, blockHit);
                    
                    // Klick 2: Setzt den Crystal drauf oder lädt den Anchor
                    client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, blockHit);
                }
            }
        });
    }
}
