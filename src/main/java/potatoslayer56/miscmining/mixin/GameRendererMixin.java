package potatoslayer56.miscmining.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.GameRenderer;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
  @Inject(method = "close", at = @At("RETURN"))
  private void onGameRendererClose(CallbackInfo ci) {
    ModRenderPipeline.close();
  }
}
