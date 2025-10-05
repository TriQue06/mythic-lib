package net.trique.mythiclib.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MythicSmithingRecipeProvider implements DataProvider {
    private final FabricDataOutput output;

    private static final List<String> MATERIALS = List.of(
            "netherite", "ametrine", "ruby", "topaz", "sapphire", "aquamarine", "jade", "peridot"
    );

    private static final List<String> EQUIPMENT = List.of(
            "sword", "pickaxe", "axe", "shovel", "hoe",
            "helmet", "chestplate", "leggings", "boots"
    );

    public MythicSmithingRecipeProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (String from : MATERIALS) {
            for (String to : MATERIALS) {
                if (from.equals(to)) continue;

                for (String eq : EQUIPMENT) {
                    Identifier id = Identifier.of("mythiclib", from + "_to_" + to + "_" + eq + "_smithing");

                    JsonObject json = smithingTransformJson(
                            "mythicupgrades:" + to + "_upgrade_smithing_template",
                            "mythicupgrades:" + from + "_" + eq,
                            "mythicupgrades:" + to + "_ingot",
                            "mythicupgrades:" + to + "_" + eq
                    );

                    Path path = output.getResolver(DataOutput.OutputType.DATA_PACK, "recipes").resolveJson(id);
                    futures.add(DataProvider.writeToPath(writer, json, path));
                }
            }
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "MythicLib — Full Smithing Recipe Generator (1.21/1.21.1 format)";
    }

    private static JsonObject smithingTransformJson(String templateItem, String baseItem, String additionItem, String resultItem) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:smithing_transform");

        JsonObject addition = new JsonObject();
        addition.addProperty("item", additionItem);
        root.add("addition", addition);

        JsonObject base = new JsonObject();
        base.addProperty("item", baseItem);
        root.add("base", base);

        JsonObject result = new JsonObject();
        result.addProperty("id", resultItem);
        result.addProperty("count", 1);
        root.add("result", result);

        JsonObject template = new JsonObject();
        template.addProperty("item", templateItem);
        root.add("template", template);

        return root;
    }
}
