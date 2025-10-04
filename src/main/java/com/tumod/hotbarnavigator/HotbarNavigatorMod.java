package com.tumod.hotbarnavigator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HotbarNavigatorMod implements ClientModInitializer {
    
    private static KeyBinding previousSlotKey;
    private static KeyBinding nextSlotKey;
    
    @Override
    public void onInitializeClient() {
        // Registrar tecla Q para slot anterior
        previousSlotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.hotbarnavigator.previous",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Q,
            "category.hotbarnavigator.main"
        ));
        
        // Registrar tecla E para slot siguiente
        nextSlotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.hotbarnavigator.next",
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_E,
            "category.hotbarnavigator.main"
        ));
        
        // Manejar los eventos de teclado
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            // Tecla Q - Slot anterior
            while (previousSlotKey.wasPressed()) {
                int currentSlot = client.player.getInventory().selectedSlot;
                int newSlot = (currentSlot - 1 + 9) % 9;
                client.player.getInventory().selectedSlot = newSlot;
            }
            
            // Tecla E - Slot siguiente  
            while (nextSlotKey.wasPressed()) {
                int currentSlot = client.player.getInventory().selectedSlot;
                int newSlot = (currentSlot + 1) % 9;
                client.player.getInventory().selectedSlot = newSlot;
            }
        });
    }
}
