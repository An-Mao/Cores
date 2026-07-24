package dev.anye.mc.cores.amlib.network.tip$gui;

import dev.anye.mc.cores.amlib.gui.TipGui;
import dev.anye.mc.cores.amlib.network.easy_net.EasyNet;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TipGuiNetSTC extends EasyNet {
    @Override
    public void client(Supplier<NetworkEvent.Context> contextSupplier, CompoundTag dat) {
        TipGui.addMsg(dat.getString("msg"),dat.getLong("time"));
    }
}
