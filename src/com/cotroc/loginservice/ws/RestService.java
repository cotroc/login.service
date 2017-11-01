package com.cotroc.loginservice.ws;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.cotroc.loginservice.objects.User;
import com.cotroc.loginservice.persistence.UserDB;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/ws")
public class RestService {
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String rootWs() {
    	return "Rest Web Service for Android Login Application";
    }
	
	@Path("/ver")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String serverVersion() {
    	return "Server Version 0.0.1";
    }
	
	@Path("/status")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String serverStatus() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("serverStatus", "onLine");
		return jsonObject.toString();
	}
	
	@Path("/testdb")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> testDb() {
		UserDB userDb = new UserDB();
		return userDb.getAllUsers();
	}
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createUser(User u) throws Exception{
        UserDB user = new UserDB();
        
        return user.createUser(u); 
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public String updateUser(User u) throws Exception {
    	UserDB user = new UserDB();
    	return user.updateUser(u);
    }
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public String deleteUser(User u) throws Exception {
    	UserDB user = new UserDB();
    	return user.deleteUser(u);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/verify")
    public String verfyUser(User u) throws Exception {
    	UserDB user = new UserDB();
    	return user.verifyUser2(u).toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/exist")
    public boolean existUser(String jsonName) { // change to string
    	UserDB user = new UserDB();
    	User u = new User();
		try {
			ObjectMapper mapper = new ObjectMapper();
			u = mapper.readValue(jsonName, User.class);
			printMessage(u.getName() + "u.toString" + u.toString(), "RestService - existUser()");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return user.existUserByName(u.getName());
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activation")
    public String userActivation(@QueryParam("name") String name, @QueryParam("mail") String mail) {
    	UserDB user = new UserDB();
    	return user.userActivation(name, mail);
    	
    }
    
	public void printMessage(String message, String method) {
		System.out.println("Message: " + message + "; from: " + method);
	}
}