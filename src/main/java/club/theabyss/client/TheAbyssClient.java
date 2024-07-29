package club.theabyss.client;

import club.theabyss.client.global.listeners.GlobalClientListeners;
import club.theabyss.client.networking.receivers.RegisterClientReceivers;
import club.theabyss.client.render.RenderRegistries;
import club.theabyss.client.render.renders.TheAbyssBuiltinItemModelRenderer;
import club.theabyss.server.game.item.items.TheAbyssItems;
import club.theabyss.server.global.utils.AbyssModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class TheAbyssClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegisterClientReceivers.init();
        GlobalClientListeners.init();
        RenderRegistries.register();
        AbyssModelPredicateProvider.registerAbyssModels();
    }

    public static void registerBuiltinItemRenderers(MinecraftClient client) {
        TheAbyssBuiltinItemModelRenderer builtinItemModelRenderer = new TheAbyssBuiltinItemModelRenderer(client.getEntityModelLoader());

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(builtinItemModelRenderer);

        BuiltinItemRendererRegistry.DynamicItemRenderer dynamicItemRenderer = builtinItemModelRenderer::render;

        BuiltinItemRendererRegistry.INSTANCE.register(TheAbyssItems.TWILIGHT_TRIDENT, dynamicItemRenderer);
    }

}
