package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;

public final class SandBlock extends Block {

	public SandBlock(int var1) {
		super(var1);
	}

	private void fall(Level var1, int var2, int var3, int var4) {
		if (!Minecraft.isSinglePlayer) {
			return;
		}
		int var11 = var2;
		int var5 = var3;
		int var6 = var4;

		while (true) {
			int var8 = var5 - 1;
			int var10;
			LiquidType var12;
			if (!((var10 = var1.getTile(var11, var8, var6)) == 0 ? true : (var12 = blocks[var10]
					.getLiquidType()) == LiquidType.water ? true : var12 == LiquidType.lava)
					|| var5 <= 0) {
				if (var5 != var3) {
					if ((var10 = var1.getTile(var11, var5, var6)) > 0
							&& blocks[var10].getLiquidType() != LiquidType.notLiquid) {
						var1.setTileNoUpdate(var11, var5, var6, 0);
					}

					var1.swap(var2, var3, var4, var11, var5, var6);
				}

				return;
			}

			--var5;
		}
	}

	@Override
	public final void onNeighborChange(Level var1, int var2, int var3, int var4, int var5) {
		fall(var1, var2, var3, var4);
	}

	@Override
	public final void onPlace(Level level, int x, int y, int z) {
		fall(level, x, y, z);
	}
}
