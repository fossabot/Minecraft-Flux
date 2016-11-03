package szewek.mcflux.wrapper.tesla;

import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import szewek.mcflux.util.IInjectRegistry;
import szewek.mcflux.util.InjectCond;
import szewek.mcflux.util.InjectRegistry;
import szewek.mcflux.wrapper.EnergyType;
import szewek.mcflux.wrapper.InjectWrappers;

@InjectRegistry(requires = InjectCond.MOD, args = {"tesla", "TESLA"})
public class TeslaInjectRegistry implements IInjectRegistry {
	@Override public void registerInjects() {
		InjectWrappers.registerTileWrapperInject(TeslaInjectRegistry::wrapTeslaTile);
		InjectWrappers.registerEntityWrapperInject(TeslaInjectRegistry::wrapTeslaEntity);
		InjectWrappers.registerWorldWrapperInject(TeslaInjectRegistry::wrapTeslaWorld);
		InjectWrappers.registerItemWrapperInject(TeslaInjectRegistry::wrapTeslaItem);
	}

	private static boolean wrapGlobal(ICapabilityProvider icp, InjectWrappers.Registry reg) {
		try {
			for (EnumFacing f : EnumFacing.VALUES)
				if (TeslaUtils.hasTeslaSupport(icp, f)) {
					reg.add(EnergyType.TESLA, new TeslaCapabilityProvider(icp));
					return true;
				}
		} catch (Exception e) {
			InjectWrappers.reportBadImplementation("TESLA", true, icp, e);
		}
		try {
			if (TeslaUtils.hasTeslaSupport(icp, null)) {
				reg.add(EnergyType.TESLA, new TeslaCapabilityProvider(icp));
				return true;
			}
		} catch (Exception e) {
			InjectWrappers.reportBadImplementation("TESLA", false, icp, e);
		}
		return false;
	}

	private static void wrapMappedTeslaProvider(ICapabilityProvider icp, InjectWrappers.Registry reg) {
		for (ICapabilityProvider icx : reg.capMap.values()) {
			if (wrapGlobal(icx, reg))
				break;
		}
	}

	private static void wrapTeslaTile(TileEntity te, InjectWrappers.Registry reg) {
		if (wrapGlobal(te, reg))
			return;
		wrapMappedTeslaProvider(te, reg);
	}

	private static void wrapTeslaEntity(Entity ent, InjectWrappers.Registry reg) {
		if (wrapGlobal(ent, reg))
			return;
		wrapMappedTeslaProvider(ent, reg);
	}

	private static void wrapTeslaWorld(World w, InjectWrappers.Registry reg) {
		if (wrapGlobal(w, reg))
			return;
		wrapMappedTeslaProvider(w, reg);
	}

	private static void wrapTeslaItem(ItemStack is, InjectWrappers.Registry reg) {
		wrapMappedTeslaProvider(is, reg);
	}
}
