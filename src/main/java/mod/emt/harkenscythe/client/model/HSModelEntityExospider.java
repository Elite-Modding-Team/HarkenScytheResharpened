package mod.emt.harkenscythe.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSpider;

public class HSModelEntityExospider extends ModelSpider
{
    public HSModelEntityExospider()
    {
        spiderHead = new ModelRenderer(this);
        spiderHead.setRotationPoint(0.0F, 15.0F, -3.0F);
        spiderHead.cubeList.add(new ModelBox(spiderHead, 32, 4, -4.0F, -4.0F, -8.01F, 8, 6, 8, 0.0F, false));
        spiderHead.cubeList.add(new ModelBox(spiderHead, 41, 18, -3.0F, 2.0F, -8.0F, 6, 2, 0, 0.0F, false));

        spiderNeck = new ModelRenderer(this);
        spiderNeck.setRotationPoint(0.0F, 15.0F, 0.0F);
        spiderNeck.cubeList.add(new ModelBox(spiderNeck, 0, 0, -3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F, false));

        spiderBody = new ModelRenderer(this);
        spiderBody.setRotationPoint(0.0F, 15.0F, 9.0F);
        spiderBody.cubeList.add(new ModelBox(spiderBody, 0, 12, -5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F, false));

        spiderLeg1 = new ModelRenderer(this, 18, 0);
        spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg1.setRotationPoint(-4.0F, 15.0F, 2.0F);

        spiderLeg2 = new ModelRenderer(this, 18, 0);
        spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg2.setRotationPoint(4.0F, 15.0F, 2.0F);

        spiderLeg3 = new ModelRenderer(this, 18, 0);
        spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg3.setRotationPoint(-4.0F, 15.0F, 1.0F);

        spiderLeg4 = new ModelRenderer(this, 18, 0);
        spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg4.setRotationPoint(4.0F, 15.0F, 1.0F);

        spiderLeg5 = new ModelRenderer(this, 18, 0);
        spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg5.setRotationPoint(-4.0F, 15.0F, 0.0F);

        spiderLeg6 = new ModelRenderer(this, 18, 0);
        spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg6.setRotationPoint(4.0F, 15.0F, 0.0F);

        spiderLeg7 = new ModelRenderer(this, 18, 0);
        spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg7.setRotationPoint(-4.0F, 15.0F, -1.0F);

        spiderLeg8 = new ModelRenderer(this, 18, 0);
        spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        spiderLeg8.setRotationPoint(4.0F, 15.0F, -1.0F);
    }
}