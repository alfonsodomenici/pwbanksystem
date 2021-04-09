/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.slowdown;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author alfonso
 */
@Slowerer
@Interceptor
public class SlowererInterceptor {
    
    @Inject
    @ConfigProperty(name = "slowerer.sleep")
    Long sleep;
    
    @AroundInvoke
    public Object slowdown(InvocationContext ic) throws InterruptedException, Exception{
        Thread.sleep(sleep);
        return ic.proceed();
    }
}
