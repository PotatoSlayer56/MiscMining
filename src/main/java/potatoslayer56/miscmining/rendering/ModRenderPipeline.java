package potatoslayer56.miscmining.rendering;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static potatoslayer56.miscmining.MiscMining.balWaypoint;

public class ModRenderPipeline implements ModInitializer {
  private static ModRenderPipeline instance;

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
  private StagedVertexBuffer.Draw draw;

  public enum waypointTypes {
    SINGLE,
    THREEBYTHREE
  }

  public static ModRenderPipeline getInstance() {
    return instance;
  }

  @Override
  public void onInitialize() {
    instance = this;
    LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
    LevelRenderEvents.BEFORE_RENDER_PASS.register(this::prepare);
    LevelRenderEvents.END_MAIN.register(_ -> STAGED_BUFFER.endFrame());
  }

  private void extractWaypoint(LevelExtractionContext context) {
  }

  private void prepare(LevelTerrainRenderContext context) {
    RenderPipeline renderPipeline = ModRenderPipeline.FILLED_THROUGH_WALLS;
    VertexFormat formatBinding = renderPipeline.getVertexFormatBinding(0);

    assert formatBinding != null;

    PrimitiveTopology primitive = renderPipeline.getPrimitiveTopology();
    StagedVertexBuffer.Draw draw = STAGED_BUFFER.appendDraw(formatBinding, primitive, primitive == PrimitiveTopology.QUADS ? RenderSystem.getProjectionType().vertexSorting() : null);

    this.renderWaypoint(context, draw);

    STAGED_BUFFER.upload();

    this.draw = draw;
  }

  private void renderWaypoint(LevelTerrainRenderContext context, StagedVertexBuffer.Draw draw) {
    Matrix4f matrix = new Matrix4f();
    Vec3 camera = context.levelState().cameraRenderState.pos;

    matrix.translate((float) -camera.x, (float) -camera.y, (float) -camera.z);

    final VertexConsumer builder = STAGED_BUFFER.getVertexBuilder(draw);

    waypoints.forEach(waypointState -> {
      if (waypointState.type() == waypointTypes.SINGLE) {
        this.renderFilledBox(matrix, builder, waypointState.x(), waypointState.y(), waypointState.z(), waypointState.x() + 1, waypointState.y() + 1, waypointState.z() + 1, waypointState.r(), waypointState.g(), waypointState.b(), waypointState.a());
      } else if (waypointState.type() == waypointTypes.THREEBYTHREE) {
        this.renderOutlinedBox(matrix, builder, waypointState.x() - 1, waypointState.y(), waypointState.z() - 1, waypointState.x() + 2, waypointState.y() + 0.25f, waypointState.z() + 2, waypointState.r(), waypointState.g(), waypointState.b(), waypointState.a());
      }
    });

    if (balWaypoint != null){
      this.renderOutlinedBox(matrix, builder, balWaypoint.x() - 1, balWaypoint.y(), balWaypoint.z() - 1, balWaypoint.x() + 2, balWaypoint.y() + 0.25f, balWaypoint.z() + 2, balWaypoint.r(), balWaypoint.g(), balWaypoint.b(), balWaypoint.a());
    }
  }

  private void renderOutlinedBox(Matrix4fc positionMatrix, VertexConsumer buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
    // Sides
    renderFilledBox(positionMatrix, buffer, minX, minY, minZ, minX + 0.05f, maxY, minZ + 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, maxX, minY, minZ, maxX - 0.05f, maxY, minZ + 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, minX, minY, maxZ, minX + 0.05f, maxY, maxZ - 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, maxX, minY, maxZ, maxX - 0.05f, maxY, maxZ - 0.05f, red, green, blue, alpha);

    // Top
    renderFilledBox(positionMatrix, buffer, minX, maxY, minZ, maxX, maxY - 0.05f, minZ + 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, minX, maxY, minZ, minX + 0.05f, maxY - 0.05f, maxZ, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, minX, maxY, maxZ, maxX, maxY - 0.05f, maxZ - 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, maxX, maxY, minZ, maxX - 0.05f, maxY - 0.05f, maxZ, red, green, blue, alpha);

    // Bottom
    renderFilledBox(positionMatrix, buffer, minX, minY, minZ, maxX, minY + 0.05f, minZ + 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, minX, minY, minZ, minX + 0.05f, minY + 0.05f, maxZ, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, minX, minY, maxZ, maxX, minY + 0.05f, maxZ - 0.05f, red, green, blue, alpha);
    renderFilledBox(positionMatrix, buffer, maxX, minY, minZ, maxX - 0.05f, minY + 0.05f, maxZ, red, green, blue, alpha);
  }

  private void renderFilledBox(Matrix4fc positionMatrix, VertexConsumer buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
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

  public void draw(RenderPass renderPass) {
    if (this.draw == null) {
      return;
    }

    StagedVertexBuffer.ExecuteInfo info = STAGED_BUFFER.getExecuteInfo(this.draw);

    if (info == null) {
      return;
    }

    GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
      .writeTransform(RenderSystem.getModelViewMatrixCopy(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);

    renderPass.pushDebugGroup(() -> "Fabric Docs Example Pipeline Rendering");
    renderPass.setPipeline(RenderSystem.getCompiledPipeline(FILLED_THROUGH_WALLS));

    RenderSystem.bindDefaultUniforms(renderPass);
    renderPass.setUniform("DynamicTransforms", dynamicTransforms);

    // Bind texture if applicable:
    // Sampler0 is used for texture inputs in vertices
    // renderPass.setUniform("Sampler0", draw.textureSetup.texure0(), draw.textureSetup.sampler0());

    renderPass.setVertexBuffer(0, info.vertexBuffer().slice());
    renderPass.setIndexBuffer(info.indexBuffer(), info.indexType());

    // The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
    renderPass.drawIndexed(info.indexCount(), 1, info.firstIndex(), info.baseVertex(), 0);
    renderPass.popDebugGroup();
  }
  // #endregion custom_pipelines_drawing_phase

  // #region custom_pipelines_clean_up
  public static void close() {
    STAGED_BUFFER.close();
  }

  public record WaypointRenderState(int x, int y, int z, float r, float g, float b, float a, waypointTypes type) { }
}
