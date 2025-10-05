package net.trique.mythiclib;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicLib implements ModInitializer {
	public static final String MOD_ID = "mythiclib";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Mythic Lib works properly!");
	}
}