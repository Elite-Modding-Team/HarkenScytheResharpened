package mod.emt.harkenscythe.client.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelHSSoul extends ModelBase
{
    private final ModelRenderer cube;
    private final ModelRenderer glass = new ModelRenderer(this, "glass");

    public ModelHSSoul()
    {
        this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        this.glass.render(scale);
        float f = 0.875F;
        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.glass.render(scale);
        GlStateManager.scale(f, f, f);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.cube.render(scale);
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f1 = 0.76F;
        GlStateManager.color(0.5F * f1, 0.25F * f1, 0.8F * f1, 1.0F);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.pushMatrix();
        float f2 = 0.125F;
        GlStateManager.scale(f2, f2, f2);
        float f3 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GlStateManager.translate(f3, 0.0F, 0.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
        this.cube.render(scale);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(f2, f2, f2);
        f3 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GlStateManager.translate(-f3, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        this.cube.render(scale);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.popMatrix();
    }
}
