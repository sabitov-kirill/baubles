package lazy.baubles.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lazy.baubles.client.gui.PlayerExpandedScreen;
import lazy.baubles.network.msg.OpenBaublesInvPacket;
import lazy.baubles.network.msg.OpenNormalInvPacket;
import lazy.baubles.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("rawtypes")
public class BaublesButton extends AbstractButton {

    private final AbstractContainerScreen parentGui;
    private final Minecraft minecraft;

    public BaublesButton(AbstractContainerScreen parentGui, int x, int y, int width, int height) {
        super(x + parentGui.getGuiLeft(), parentGui.getGuiTop() + y, width, height, Component.empty());
        this.parentGui = parentGui;
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void onPress() { // onPress
        if (parentGui instanceof InventoryScreen) {
            PacketHandler.INSTANCE.sendToServer(new OpenBaublesInvPacket());
        } else {
            if (this.minecraft.player != null) {
                PacketHandler.INSTANCE.sendToServer(new OpenNormalInvPacket());
                this.displayNormalInventory(this.minecraft.player);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.minecraft == null)
            return;

        if (this.minecraft.player != null && !this.minecraft.player.isCreative()) {
            if (this.visible) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, PlayerExpandedScreen.BACKGROUND);

                this.isHovered = mouseX >= this.getX() &&
                        mouseY >= this.getY() &&
                        mouseX < this.getX() + this.getWidth() &&
                        mouseY < this.getY() + this.getHeight();

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.translate(0, 0, 200);

                if (!isHovered) {
                    guiGraphics.blit(PlayerExpandedScreen.BACKGROUND,
                            this.getX(), this.getY(),
                            200, 48, 10, 10);
                } else {
                    guiGraphics.blit(
                            PlayerExpandedScreen.BACKGROUND,
                            this.getX(), this.getY(),
                            210, 48, 10, 10);
                    guiGraphics.drawString(
                            this.minecraft.font,
                            Component.translatable("button.displayText").getString(),
                            this.getX() + 5, this.getY() + this.getHeight(),
                            0xffffff);
                }

                poseStack.popPose();
            }
        }
    }

    public void displayNormalInventory(Player player) {
        Minecraft.getInstance().setScreen(new InventoryScreen(player));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}