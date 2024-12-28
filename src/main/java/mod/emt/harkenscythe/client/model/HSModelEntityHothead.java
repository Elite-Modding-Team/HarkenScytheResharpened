package mod.emt.harkenscythe.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

// Courtesy of Foreck
public class HSModelEntityHothead extends ModelBase
{
    private final ModelRenderer head;
    private final ModelRenderer jaw;

    public HSModelEntityHothead()
    {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(1.0F, 15.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -7.5F, -10.0F, -7.0F, 13, 14, 14, 0.0F, false));
        head.cubeList.add(new ModelBox(head, -14, 50, -7.5F, 0.0F, -7.0F, 13, 0, 14, 0.0F, false));

        jaw = new ModelRenderer(this);
        jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        jaw.cubeList.add(new ModelBox(jaw, 5, 30, -5.5F, -1.0F, -7.0F, 9, 9, 10, 0.0F, false));

        head.addChild(jaw);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 1.2F, 0.0F);
        head.render(0.01F);

        GlStateManager.popMatrix();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}