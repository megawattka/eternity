package org.mgwt.eternity.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.mgwt.eternity.Eternity;
import org.spongepowered.asm.mixin.Mixin;
import org.apache.http.impl.client.CloseableHttpClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.URI;

@Environment(EnvType.CLIENT)
@Mixin(CloseableHttpClient.class)
public abstract class CloseableHttpClientMixin {
    @Inject(method = "execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/client/methods/CloseableHttpResponse;", at = @At("HEAD"))
    private void onExecute(HttpUriRequest request, CallbackInfoReturnable<CloseableHttpResponse> cir) {
        URI uri = request.getURI();
        String infos = request.getMethod() + " " + uri;
        Eternity.INSTANCE.getLogger().debug("Apache HTTP executing: {}", infos);
    }

    @Inject(method = "execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/client/methods/CloseableHttpResponse;", at = @At("RETURN"))
    private void onExecuteEnd(HttpUriRequest request, CallbackInfoReturnable<CloseableHttpResponse> cir) {
        CloseableHttpResponse resp = cir.getReturnValue();
        StatusLine status = resp.getStatusLine();
        int code = status.getStatusCode();
        Eternity.INSTANCE.getLogger().debug("{} {} {}", request.getURI(), code, status.getReasonPhrase());
    }
}
