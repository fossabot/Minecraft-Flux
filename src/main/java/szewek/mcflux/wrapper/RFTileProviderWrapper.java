package szewek.mcflux.wrapper;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.nbt.NBTBase;
import szewek.mcflux.api.IEnergyProducer;

public class RFTileProviderWrapper implements IEnergyProducer {
	private final IEnergyProvider provider;
	
	public RFTileProviderWrapper(IEnergyProvider provider) {
		this.provider = provider;
	}

	@Override
	public int getEnergy() {
		return provider.getEnergyStored(null);
	}

	@Override
	public int getEnergyCapacity() {
		return provider.getEnergyStored(null);
	}

	@Override
	public NBTBase serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
	}

	@Override
	public int extractEnergy(int amount, boolean simulate) {
		return provider.extractEnergy(null, amount, simulate);
	}

}