package mod.emt.harkenscythe.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

// Courtesy of Foreck
public class HSModelEntityBearer extends ModelBase
{
    private final ModelRenderer head;
    private final ModelRenderer inner_darkness;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer horns;
    private final ModelRenderer left_r1;
    private final ModelRenderer right_r1;
    private final ModelRenderer right_r2;
    private final ModelRenderer right_r3;
    private final ModelRenderer left_r2;
    private final ModelRenderer left_r3;
    private final ModelRenderer jaw;

    public HSModelEntityBearer()
    {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(1.0F, 15.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -7.5F, -10.0F, -7.0F, 13, 14, 14, 0.0F, false));
        head.cubeList.add(new ModelBox(head, -1, -3, 3.5F, 0.0F, -7.0F, 0, 4, 5, 0.0F, false));
        head.cubeList.add(new ModelBox(head, -1, -3, -5.5F, 0.0F, -7.0F, 0, 4, 5, 0.0F, false));
        head.cubeList.add(new ModelBox(head, -14, 50, -7.5F, 0.0F, -7.0F, 13, 0, 14, 0.0F, false));

        inner_darkness = new ModelRenderer(this);
        inner_darkness.setRotationPoint(-0.9F, -7.999F, -3.4F);
        head.addChild(inner_darkness);
        inner_darkness.cubeList.add(new ModelBox(inner_darkness, 40, 55, -6.0F, -2.001F, -0.501F, 12, 9, 0, 0.0F, false));
        inner_darkness.cubeList.add(new ModelBox(inner_darkness, 44, 40, -4.6F, -2.001F, -2.7F, 9, 9, 0, 0.0F, false));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(5.999F, 3.499F, -2.5F);
        inner_darkness.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 58, 55, -2.0F, -5.5F, -12.0F, 3, 9, 0, 0.0F, false));
        cube_r1.cubeList.add(new ModelBox(cube_r1, 58, 55, -2.0F, -5.5F, 0.0F, 3, 9, 0, 0.0F, false));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        inner_darkness.addChild(cube_r2);
        setRotationAngle(cube_r2, 1.5708F, 0.0F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 40, 57, -6.0F, -3.5F, -7.0F, 12, 3, 0, 0.0F, false));
        cube_r2.cubeList.add(new ModelBox(cube_r2, 40, 57, -6.0F, -3.5F, 1.8F, 12, 3, 0, 0.0F, false));

        horns = new ModelRenderer(this);
        horns.setRotationPoint(-1.5F, -3.0697F, -5.7498F);
        head.addChild(horns);
        setRotationAngle(horns, 0.2182F, 0.0F, 0.0F);
        horns.cubeList.add(new ModelBox(horns, 7, 14, -8.5F, -2.8571F, 2.5967F, 18, 4, 4, 0.0F, false));

        left_r1 = new ModelRenderer(this);
        left_r1.setRotationPoint(7.4368F, 27.9697F, -2.9221F);
        horns.addChild(left_r1);
        setRotationAngle(left_r1, 0.0F, 1.0908F, 0.0F);
        left_r1.cubeList.add(new ModelBox(left_r1, 32, 14, -7.4906F, -30.2268F, 3.2253F, 8, 3, 3, 0.0F, true));

        right_r1 = new ModelRenderer(this);
        right_r1.setRotationPoint(-7.4368F, 27.8697F, -2.9221F);
        horns.addChild(right_r1);
        setRotationAngle(right_r1, 0.0F, -1.0908F, 0.0F);
        right_r1.cubeList.add(new ModelBox(right_r1, 32, 16, -0.0476F, -30.2268F, 2.3383F, 8, 3, 3, 0.0F, false));

        right_r2 = new ModelRenderer(this);
        right_r2.setRotationPoint(-8.0469F, 26.8697F, -4.8293F);
        horns.addChild(right_r2);
        setRotationAngle(right_r2, 0.0F, -2.0944F, 0.0F);
        right_r2.cubeList.add(new ModelBox(right_r2, 38, 15, -0.1766F, -28.7268F, -0.5734F, 6, 2, 2, 0.0F, false));

        right_r3 = new ModelRenderer(this);
        right_r3.setRotationPoint(-6.7806F, -1.8571F, -4.8735F);
        horns.addChild(right_r3);
        setRotationAngle(right_r3, 0.0F, 1.4399F, 0.0F);
        right_r3.cubeList.add(new ModelBox(right_r3, 40, 17, -0.5F, 0.5F, -2.5F, 6, 1, 1, 0.0F, false));

        left_r2 = new ModelRenderer(this);
        left_r2.setRotationPoint(6.7806F, -1.7571F, -4.8735F);
        horns.addChild(left_r2);
        setRotationAngle(left_r2, 0.0F, -1.4399F, 0.0F);
        left_r2.cubeList.add(new ModelBox(left_r2, 40, 17, -5.3695F, 0.5F, -3.4914F, 6, 1, 1, 0.0F, true));

        left_r3 = new ModelRenderer(this);
        left_r3.setRotationPoint(8.0469F, 26.9697F, -4.8293F);
        horns.addChild(left_r3);
        setRotationAngle(left_r3, 0.0F, 2.0944F, 0.0F);
        left_r3.cubeList.add(new ModelBox(left_r3, 38, 15, -6.3234F, -28.7268F, 0.2926F, 6, 2, 2, 0.0F, true));

        jaw = new ModelRenderer(this);
        jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(jaw);
        jaw.cubeList.add(new ModelBox(jaw, 5, 30, -5.5F, -1.0F, -7.0F, 9, 9, 10, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        head.render(scale);

        GlStateManager.popMatrix();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}