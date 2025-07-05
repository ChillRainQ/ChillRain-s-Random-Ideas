package com.chillrain.chillrainrandomideas.integration.de.client.model;

import com.chillrain.chillrainrandomideas.integration.de.client.handler.ResourceHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelChaoticArmor
 *
 * @author Chill_Rain 2025/06/27
 */
public class ModelChaoticArmor extends ModelBiped {
    public ModelRenderObj head;
    public ModelRenderObj body;
    public ModelRenderObj rightArm;
    public ModelRenderObj leftArm;
    public ModelRenderObj belt;
    public ModelRenderObj rightLeg;
    public ModelRenderObj leftLeg;
    public ModelRenderObj rightBoot;
    public ModelRenderObj leftBoot;

    public ModelChaoticArmor(float f, boolean isHelmet, boolean isChestPiece, boolean isLeggings, boolean isdBoots) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);

        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

        this.head = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_helmet.obj"),
                ResourceHandler.getResource("textures/models/armor/ChaoticHelmet.png"));

        this.body = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_body.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_body.png"));

        this.rightArm = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_right_arm.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_right_arm.png"));

        this.leftArm = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_left_arm.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_left_arm.png"));

        this.belt = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_belt.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_belt.png"));

        this.rightLeg = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_right_leg.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_right_leg.png"));

        this.leftLeg = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_left_leg.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_left_leg.png"));

        this.rightBoot = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_right_boot.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_right_boot.png"));
        this.leftBoot = new ModelRenderObj(
                this,
                ResourceHandler.getResource("models/armor/chaotic_left_boot.obj"),
                ResourceHandler.getResource("textures/models/armor/chaotic_left_boot.png"));

        this.bipedHead.cubeList.clear();
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        body.offsetY = 0.755F;
        rightArm.offsetY = 0.755F;
        leftArm.offsetY = 0.755F;

        head.offsetY = 0F;
        head.offsetX = -0.05F;
        head.offsetZ = 0.05F;
        body.offsetY = 0.75F;
        body.offsetZ = -0.06F;
        rightArm.offsetY = 0.8F;
        rightArm.offsetX = -0.2F;
        rightArm.offsetZ = -0.04F;
        leftArm.offsetY = 0.8F;
        leftArm.offsetX = 0.2F;
        leftArm.offsetZ = -0.04F;
        belt.offsetY = 0.756F;
        belt.offsetZ = -0.04F;
        rightLeg.offsetY = 0.6F;
        rightLeg.offsetX = -0.085F;
        leftLeg.offsetY = 0.6F;
        leftLeg.offsetX = 0.085F;
        rightBoot.offsetY = 0.76F;
        rightBoot.offsetX = -0.03F;
        leftBoot.offsetY = 0.76F;
        leftBoot.offsetX = 0.03F;

        leftLeg.scale = 1F / 14F;
        rightLeg.scale = 1F / 14F;
        leftBoot.scale = 1F / 14F;
        rightBoot.scale = 1F / 14F;

        leftArm.scale = 1F / 13.7F;
        rightArm.scale = 1F / 13.7F;

        if (isHelmet) {
            this.bipedHead.addChild(head);
        }
        if (isChestPiece) {
            this.bipedBody.addChild(body);
            this.bipedLeftArm.addChild(leftArm);
            this.bipedRightArm.addChild(rightArm);
        }
        if (isLeggings) {
            this.bipedLeftLeg.addChild(leftLeg);
            this.bipedRightLeg.addChild(rightLeg);
            this.bipedBody.addChild(belt);
        }
        if (isdBoots) {
            this.bipedLeftLeg.addChild(leftBoot);
            this.bipedRightLeg.addChild(rightBoot);
        }
    }
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity == null) {
            isSneak = false;
            isRiding = false;
            isChild = false;
            aimedBow = false;

            this.bipedRightArm.rotateAngleX = 0F;
            this.bipedRightArm.rotateAngleY = 0F;
            this.bipedRightArm.rotateAngleZ = 0F;
            this.bipedLeftArm.rotateAngleX = 0F;
            this.bipedLeftArm.rotateAngleY = 0F;
            this.bipedLeftArm.rotateAngleZ = 0F;

            bipedBody.rotateAngleX = 0F;
            bipedBody.rotateAngleY = 0F;
            bipedBody.rotateAngleZ = 0F;

            bipedHead.rotateAngleX = 0F;
            bipedHead.rotateAngleY = 0F;
            bipedHead.rotateAngleZ = 0F;

            bipedLeftLeg.rotateAngleX = 0F;
            bipedLeftLeg.rotateAngleY = 0F;
            bipedLeftLeg.rotateAngleZ = 0F;

            bipedRightLeg.rotateAngleX = 0F;
            bipedRightLeg.rotateAngleY = 0F;
            bipedRightLeg.rotateAngleZ = 0F;

            setRotationAngles(0, 0, 0, 0, 0, 0, null);
        } else {
            super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        }
        this.bipedHead.render(1F / 10F);
        this.bipedRightArm.render(1F / 15F);
        this.bipedLeftArm.render(1F / 15F);
        this.bipedBody.render(1F / 15F);
        this.bipedRightLeg.render(1F / 16F);
        this.bipedLeftLeg.render(1F / 16F);
    }
//    @Override
//    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
//        if (entity == null) {
//            isSneak = false;
//            isRiding = false;
//            isChild = false;
//            aimedBow = false;
//
//            this.bipedRightArm.rotateAngleX = 0F;
//            this.bipedRightArm.rotateAngleY = 0F;
//            this.bipedRightArm.rotateAngleZ = 0F;
//            this.bipedLeftArm.rotateAngleX = 0F;
//            this.bipedLeftArm.rotateAngleY = 0F;
//            this.bipedLeftArm.rotateAngleZ = 0F;
//
//            bipedBody.rotateAngleX = 0F;
//            bipedBody.rotateAngleY = 0F;
//            bipedBody.rotateAngleZ = 0F;
//
//            bipedHead.rotateAngleX = 0F;
//            bipedHead.rotateAngleY = 0F;
//            bipedHead.rotateAngleZ = 0F;
//
//            bipedLeftLeg.rotateAngleX = 0F;
//            bipedLeftLeg.rotateAngleY = 0F;
//            bipedLeftLeg.rotateAngleZ = 0F;
//
//            bipedRightLeg.rotateAngleX = 0F;
//            bipedRightLeg.rotateAngleY = 0F;
//            bipedRightLeg.rotateAngleZ = 0F;
//
//            setRotationAngles(0, 0, 0, 0, 0, 0, null);
//        } else super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
//
//        this.bipedHead.render(1F / 13F);
//        this.bipedRightArm.render(1F / 15F);
//        this.bipedLeftArm.render(1F / 15F);
//        this.bipedBody.render(1F / 15F);
//        this.bipedRightLeg.render(1F / 16F);
//        this.bipedLeftLeg.render(1F / 16F);
//    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
                                  float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        this.bipedBody.rotateAngleX = 0.0F;
        this.bipedRightLeg.rotationPointZ = 0.1F;
        this.bipedLeftLeg.rotationPointZ = 0.1F;
        this.bipedRightLeg.rotationPointY = 12.0F;
        this.bipedLeftLeg.rotationPointY = 12.0F;
        this.bipedHead.rotationPointY = 0.0F;
        this.bipedHeadwear.rotationPointY = 0.0F;
        this.leftLeg.rotationPointZ = 0F;
        this.rightLeg.rotationPointZ = 0F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
    }
}
