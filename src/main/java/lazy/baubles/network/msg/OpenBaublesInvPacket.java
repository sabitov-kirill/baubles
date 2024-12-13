package lazy.baubles.network.msg;

import lazy.baubles.client.util.GuiProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OpenBaublesInvPacket {

    public OpenBaublesInvPacket(FriendlyByteBuf buf) {
    }

    public OpenBaublesInvPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var playerEntity = ctx.get().getSender();
            if (playerEntity != null) {
                playerEntity.closeContainer();
                NetworkHooks.openScreen(playerEntity, new GuiProvider(), buf -> {});
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
