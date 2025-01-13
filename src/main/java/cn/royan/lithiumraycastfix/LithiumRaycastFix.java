package cn.royan.lithiumraycastfix;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LithiumRaycastFix implements ModInitializer {
	public static final String MOD_ID = "lithium-raycast-fix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("lithium-raycast-fix is installed");
	}
}