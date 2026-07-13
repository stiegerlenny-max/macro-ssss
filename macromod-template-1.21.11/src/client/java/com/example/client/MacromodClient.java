package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

public class MacromodClient implements ClientModInitializer {

    public static KeyMapping macroKey;

    @Override
    public void onInitializeClient() {
        // Wir übergeben eine leere Kategorie-Instanz, um den Typ-Fehler zu vermeiden
        macroKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.macromod.pvp", 
            InputConstants.Type.KEYSYM, 
            GLFW.GLFW_KEY_X, 
            "key.categories.misc" 
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (macroKey.consumeClick() && client.player != null && client.gameMode != null) {
                
                HitResult hit = client.hitResult;
                
                if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    
                    client.gameMode.useItemOn(client.player, InteractionHand.MAIN_HAND, blockHit);
                    client.gameMode.useItemOn(client.player, InteractionHand.MAIN_HAND, blockHit);
                }
            }
        });
    }
}
