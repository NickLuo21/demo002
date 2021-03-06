package com.nick.restful;

import com.google.common.base.Preconditions;
import com.nick.model.DemoEntity;
import com.nick.service.DemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Nick on 2017/3/11.
 */
@Path("/demo")
public class DemoResource {

    @Autowired
    private DemoService demoService;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DemoEntity> getAllUsers()
    {
        List<DemoEntity> entities = demoService.getAllEntities();
        return entities;
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DemoEntity getUserById(@PathParam("id") String id)
    {
        Preconditions.checkArgument(StringUtils.isNotEmpty(id), "user id should not be empty!");
        DemoEntity entity = demoService.getEntityById(Long.valueOf(id));
        return entity;
    }
}
