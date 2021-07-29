package lazy.baubles.api.render;

import lazy.baubles.api.bauble.IBauble;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;

/**
 * A Bauble Item that implements this will be have hooks to render something on
 * the player while its equipped.
 * This class doesn't extend IBauble to make the API not depend on the Baubles
 * API, but the item in question still needs to implement IBauble.
 */
public interface IRenderBauble extends IBauble {

    /**
     * Called for the rendering of the bauble on the player. The player instance can be
     * acquired through the event parameter. Transformations are already applied for
     * the RenderType passed in. Make sure to check against the type parameter for
     * rendering.
     */
    void onPlayerBaubleRender(Player player, RenderType type, float partialTicks);


    /**
     * A few helper methods for the render.
     */
    final class Helper {

        /**
         * Rotates the render for a bauble correctly if the player is sneaking.
         * Use for renders under {@link RenderType#BODY}.
         */
        public static void rotateIfSneaking(Player player) {
            if (player.isCrouching())
                applySneakingRotation();
        }

        /**
         * Rotates the render for a bauble correctly for a sneaking player.
         * Use for renders under {@link RenderType#BODY}.
         */
        public static void applySneakingRotation() {
            //TODO
            /*GlStateManager._translatef(0F, 0.2F, 0F);
            GlStateManager._rotatef(90F / (float) Math.PI, 1.0F, 0.0F, 0.0F);*/
        }

        /**
         * Shifts the render for a bauble correctly to the head, including sneaking rotation.
         * Use for renders under {@link RenderType#HEAD}.
         */
        public static void translateToHeadLevel(Player player) {
            //TODO
            /*GlStateManager._translatef(0, -player.getEyeHeight(), 0);
            if (player.isCrouching())
                GlStateManager._translatef(0.25F * Mth.sin(player.yHeadRot * (float) Math.PI / 180), 0.25F * Mth.cos(player.yHeadRot * (float) Math.PI / 180), 0F);
*/        }

        /**
         * Shifts the render for a bauble correctly to the face.
         * Use for renders under {@link RenderType#HEAD}, and usually after calling {@link Helper#translateToHeadLevel(PlayerEntity)}.
         */
        public static void translateToFace() {
            //TODO
            /*GlStateManager._rotatef(90F, 0F, 1F, 0F);
            GlStateManager._rotatef(180F, 1F, 0F, 0F);
            GlStateManager._translatef(0f, -4.35f, -1.27f);*/
        }

        /**
         * Scales down the render to a correct size.
         * Use for any render.
         */
        public static void defaultTransforms() {
            //TODO
            /*GlStateManager._translatef(0.0f, 3.0f, 1.0f);
            GlStateManager._scalef(0.55f, 0.55f, 0.55f);*/
        }

        /**
         * Shifts the render for a bauble correctly to the chest.
         * Use for renders under {@link RenderType#BODY}, and usually after calling {@link Helper#rotateIfSneaking(PlayerEntity)}.
         */
        public static void translateToChest() {
            //TODO
            /*GlStateManager._rotatef(180F, 1F, 0F, 0F);
            GlStateManager._translatef(0F, -3.2F, -0.85F);*/
        }
    }

    enum RenderType {
        /**
         * Render Type for the player's body, translations apply on the player's rotation.
         * Sneaking is not handled and should be done during the render.
         *
         * @see IRenderBauble.Helper
         */
        BODY,

        /**
         * Render Type for the player's body, translations apply on the player's head rotations.
         * Sneaking is not handled and should be done during the render.
         *
         * @see IRenderBauble.Helper
         */
        HEAD;
    }
}