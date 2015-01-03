/*
 * This file is part of Blue Power.
 *
 *     Blue Power is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Blue Power is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Blue Power.  If not, see <http://www.gnu.org/licenses/>
 */

package com.bluepowermod.part.wire.redstone;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.qmunity.lib.vec.Vec3i;

import com.bluepowermod.api.redstone.IBundledDevice;
import com.bluepowermod.api.redstone.IRedstoneDevice;
import com.bluepowermod.api.redstone.IRedstoneProvider;
import com.bluepowermod.block.machine.BlockLampRGB;

public class RedstoneProviderVanilla implements IRedstoneProvider {

    @Override
    public IRedstoneDevice getRedstoneDevice(World world, int x, int y, int z, ForgeDirection face, ForgeDirection side) {

        return DummyRedstoneDevice.getDeviceAt(new Vec3i(x, y, z, world));
    }

    @Override
    public IBundledDevice getBundledDevice(World world, int x, int y, int z, ForgeDirection face, ForgeDirection side) {

        if (world.getBlock(x, y, z) instanceof BlockLampRGB)
            return BlockLampRGB.RGBLampBundledDevice.getDeviceAt(world, x, y, z);

        return null;
    }

}
