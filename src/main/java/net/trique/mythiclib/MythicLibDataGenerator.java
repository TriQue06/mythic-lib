package net.trique.mythiclib;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.trique.mythiclib.datagen.MythicSmithingRecipeProvider;

public class MythicLibDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		FabricDataGenerator.Pack pack = gen.createPack();
		pack.addProvider(MythicSmithingRecipeProvider::new);
	}
}
