package dev.namako.bananaskin.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
  private MatrixStack matrixStack;
  private int startX;
  private int startY;
  private int count;
  private boolean half;

  @Shadow private int scaledWidth;

  @Inject(method = "renderHealthBar", at = @At("HEAD"))
  public void onDrawHeart(MatrixStack matrices, PlayerEntity p, int x, int y, int lines, int index, float max, int last, int health, int absorption, boolean blinking, CallbackInfo ci) {
    matrixStack = matrices;
    startX = scaledWidth / 2 + 10 + 9 * 8;
    startY = y - 10;
    float saturation = p.getHungerManager().getSaturationLevel();
    count = Math.round(saturation) / 2;
    half = Math.round(saturation) % 2 == 1;
    drawFood();
  }

  private void drawFood() {
    int i;
    for(i=0;i<count;i++) {
      drawTexture(matrixStack, startX + i * -8, startY , 124, 27, 9,9);
      drawTexture(matrixStack, startX + i * -8, startY , 52, 27, 9,9);
    }
    if(half) {
      drawTexture(matrixStack, startX + i * -8, startY , 124, 27, 9,9);
      drawTexture(matrixStack, startX + i * -8, startY , 61, 27, 9,9);
    }
  }
}
