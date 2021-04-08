package it.tss.banksystem.ping;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

/**
 *
 * @author airhacks.com
 */
@Path("ping")
public class PingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;    

    @GET
    public String ping() {
        return message ;
    }
    
    @Operation(summary = "test post with json object")
    @RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(example = "{x=10,y=20}")))
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(JsonObject o){
        return Response.ok().build();
    }

}
