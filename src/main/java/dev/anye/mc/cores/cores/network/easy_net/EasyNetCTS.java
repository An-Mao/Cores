package dev.anye.mc.cores.cores.network.easy_net;

import dev.anye.mc.cores.cores.network.Net;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EasyNetCTS {
    private final CompoundTag dat;
    public EasyNetCTS(CompoundTag dat){
        this.dat = dat;
    }
    public EasyNetCTS(FriendlyByteBuf buf){
        this.dat = buf.readNbt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(dat);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(()->{
            String key = this.dat.getString(Net.EASY_NET_KEY);
            EasyNet easyNet = EasyNetRegister.REGISTRY.get().getValue(new ResourceLocation(key));
            if (easyNet != null){
                easyNet.server(ctx,this.dat);
            }
        });
    }
}
