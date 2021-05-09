package com.MiningSpecialEffectTracker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class MiningSpecialEffectTrackerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(MiningSpecialEffectTrackerPlugin.class);
		RuneLite.main(args);
	}
}