package mod.emt.harkenscythe.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

// TODO: Add 3D fire on arms and clean up
// Courtesy of Foreck
public class HSModelEntitySkeletonShaman extends ModelSkeleton
{
    public ModelRenderer beads;
    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;
    public ModelRenderer cube_r3;
    public ModelRenderer cube_r4;
    public ModelRenderer cube_r5;
    public ModelRenderer rightArm;
    public ModelRenderer rightFire_r1;
    public ModelRenderer leftArm;
    public ModelRenderer leftFire_r1;

    public HSModelEntitySkeletonShaman()
    {
        this(0.0F, false);
    }

    public HSModelEntitySkeletonShaman(float modelSize, boolean p_i46303_2_)
    {
        textureWidth = 64;
        textureHeight = 32;

        if (!p_i46303_2_)
        {
            bipedBody = new ModelRenderer(this);
            bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
            bipedBody.cubeList.add(new ModelBox(bipedBody, 16, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, false));

            beads = new ModelRenderer(this);
            beads.setRotationPoint(0.0F, 24.0F, 0.0F);
            bipedBody.addChild(beads);


            cube_r1 = new ModelRenderer(this);
            cube_r1.setRotationPoint(-5.1452F, -21.6779F, -3.1731F);
            beads.addChild(cube_r1);
            setRotationAngle(cube_r1, -0.784F, 0.6467F, 0.7466F);
            cube_r1.cubeList.add(new ModelBox(cube_r1, 50, 20, -1.5F, -1.5F, -1.9F, 2, 2, 2, 0.0F, false));

            cube_r2 = new ModelRenderer(this);
            cube_r2.setRotationPoint(5.1452F, -21.6779F, -3.1731F);
            beads.addChild(cube_r2);
            setRotationAngle(cube_r2, -0.784F, -0.6467F, -0.7466F);
            cube_r2.cubeList.add(new ModelBox(cube_r2, 50, 20, -0.5F, -1.5F, -1.9F, 2, 2, 2, 0.0F, true));

            cube_r3 = new ModelRenderer(this);
            cube_r3.setRotationPoint(3.5F, -21.0314F, -3.513F);
            beads.addChild(cube_r3);
            setRotationAngle(cube_r3, -1.0498F, -0.1899F, -0.3744F);
            cube_r3.cubeList.add(new ModelBox(cube_r3, 50, 20, -1.5F, -1.0F, -1.0F, 2, 2, 2, 0.0F, true));

            cube_r4 = new ModelRenderer(this);
            cube_r4.setRotationPoint(-3.5F, -21.0314F, -3.513F);
            beads.addChild(cube_r4);
            setRotationAngle(cube_r4, -1.0498F, 0.1899F, 0.3744F);
            cube_r4.cubeList.add(new ModelBox(cube_r4, 50, 20, -0.5F, -1.0F, -1.0F, 2, 2, 2, 0.0F, false));

            cube_r5 = new ModelRenderer(this);
            cube_r5.setRotationPoint(0.0F, -20.5F, -3.6F);
            beads.addChild(cube_r5);
            setRotationAngle(cube_r5, 0.2182F, 0.0F, 0.0F);
            cube_r5.cubeList.add(new ModelBox(cube_r5, 49, 26, -1.5F, -1.0F, -2.0F, 3, 3, 3, 0.0F, false));

            rightFire_r1 = new ModelRenderer(this);
            rightFire_r1.setRotationPoint(1.0F, 4.5F, 0.0F);
            rightArm.addChild(rightFire_r1);
            setRotationAngle(rightFire_r1, 0.0F, -1.5708F, 0.0F);
            rightFire_r1.cubeList.add(new ModelBox(rightFire_r1, 33, 2, -1.809F, -3.5489F, -1.02F, 3, 10, 3, 0.0F, false));

            leftFire_r1 = new ModelRenderer(this);
            leftFire_r1.setRotationPoint(11.0F, 4.5F, 0.0F);
            leftArm.addChild(leftFire_r1);
            setRotationAngle(leftFire_r1, 0.0F, 1.5708F, 0.0F);
            leftFire_r1.cubeList.add(new ModelBox(leftFire_r1, 33, 2, -1.191F, -3.5489F, -12.97F, 3, 10, 3, 0.0F, false));

            bipedHead = new ModelRenderer(this);
            bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
            bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
            bipedHead.cubeList.add(new ModelBox(bipedHead, 45, 9, -2.0F, -12.0F, -4.125F, 4, 6, 0, 0.0F, false));
        }
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();

        if (this.isChild)
        {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
        }
        else
        {
            if (entityIn.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
        }

        GlStateManager.popMatrix();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
