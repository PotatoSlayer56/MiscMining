package potatoslayer56.miscmining.rendering;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import potatoslayer56.miscmining.MiscMining;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class ModRenderPipeline implements ModInitializer {
  private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
    .withLocation(Identifier.withDefaultNamespace("pipeline/filled_box_through_walls"))
    .withDepthStencilState(Optional.empty())
    .build()
  );

  public static List<WaypointRenderState> waypoints = new ArrayList<>();

  private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
  private static final Vector3f MODEL_OFFSET = new Vector3f();
  private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();
  private static final StagedVertexBuffer STAGED_BUFFER = new StagedVertexBuffer(() -> "Waypoints Buffer", RenderType.SMALL_BUFFER_SIZE);

  @Override
  public void onInitialize() {
    LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
    LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(this::renderAndDrawWaypoint);
  }

  private void extractWaypoint(LevelExtractionContext context) {
  }

  private void renderAndDrawWaypoint(LevelRenderContext context) {
    RenderPipeline renderPipeline = ModRenderPipeline.FILLED_THROUGH_WALLS;
    VertexFormat formatBinding = renderPipeline.getVertexFormatBinding(0);

    assert formatBinding != null;

    PrimitiveTopology primitive = renderPipeline.getPrimitiveTopology();
    StagedVertexBuffer.Draw draw = STAGED_BUFFER.appendDraw(formatBinding, primitive, primitive == PrimitiveTopology.QUADS ? RenderSystem.getProjectionType().vertexSorting() : null);

    this.renderWaypoint(context, draw);

    STAGED_BUFFER.upload();

    StagedVertexBuffer.ExecuteInfo info = STAGED_BUFFER.getExecuteInfo(draw);

    if (info != null) {
      draw(Minecraft.getInstance(), info, renderPipeline);
    }

    STAGED_BUFFER.endFrame();
  }

  private void renderWaypoint(LevelRenderContext context, StagedVertexBuffer.Draw draw) {
    PoseStack matrices = context.poseStack();
    Vec3 camera = context.levelState().cameraRenderState.pos;

    matrices.pushPose();
    matrices.translate(-camera.x, -camera.y, -camera.z);

    final VertexConsumer builder = STAGED_BUFFER.getVertexBuilder(draw);

    waypoints.forEach(waypointState -> {
      this.renderFilledBox(matrices.last().pose(), builder, waypointState.x(), waypointState.y(), waypointState.z(), waypointState.x() + 1, waypointState.y() + 1, waypointState.z() + 1, waypointState.r(), waypointState.g(), waypointState.b(), waypointState.a());
    });
    matrices.popPose();
  }

  private void renderFilledBox(Matrix4f positionMatrix, VertexConsumer buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
    // Front Face
    buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);

    // Back face
    buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

    // Left face
    buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

    // Right face
    buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);

    // Top face
    buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

    // Bottom face
    buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
    buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
  }

  private static void draw(Minecraft client, StagedVertexBuffer.ExecuteInfo info, RenderPipeline pipeline) {
    GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
      .writeTransform(RenderSystem.getModelViewMatrixCopy(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);

    RenderTarget mainTarget = client.gameRenderer.mainRenderTarget();
    GpuTextureView colorTexture = mainTarget.getColorTextureView();

    assert colorTexture != null;

    try (RenderPass renderPass = RenderSystem.getDevice()
      .createCommandEncoder()
      .createRenderPass(() -> MiscMining.MOD_ID + " render pipeline rendering", colorTexture, Optional.empty(), mainTarget.getDepthTextureView(), OptionalDouble.empty())) {
      renderPass.setPipeline(pipeline);

      RenderSystem.bindDefaultUniforms(renderPass);
      renderPass.setUniform("DynamicTransforms", dynamicTransforms);

      renderPass.setVertexBuffer(0, info.vertexBuffer().slice());
      renderPass.setIndexBuffer(info.indexBuffer(), info.indexType());

      renderPass.drawIndexed(info.indexCount(), 1, info.firstIndex(), info.baseVertex(), 0);
    }
  }
  // #endregion custom_pipelines_drawing_phase

  // #region custom_pipelines_clean_up
  public static void close() {
    STAGED_BUFFER.close();
  }

  public record WaypointRenderState(int x, int y, int z, float r, float g, float b, float a) { }
}
