package com.chillrain.raindp.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CRHandlerManager {
    boolean open();
}
