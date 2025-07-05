package com.chillrain.chillrainrandomideas.client.interfaces;

import net.minecraftforge.client.model.IModelCustom;

import java.util.List;

public interface CModelCustom extends IModelCustom {
    /** 获取所有材质组名称 */
    List<String> getGroupNames();

    /** 检查是否存在指定材质组 */
    boolean hasGroup(String name);
}
