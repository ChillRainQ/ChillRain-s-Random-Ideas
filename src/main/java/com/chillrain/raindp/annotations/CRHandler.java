package com.chillrain.raindp.annotations;

import com.chillrain.raindp.interfaces.IHandlerManager;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CRHandler {
    boolean open();
    Class<? extends IHandlerManager> managerClazz();
}
