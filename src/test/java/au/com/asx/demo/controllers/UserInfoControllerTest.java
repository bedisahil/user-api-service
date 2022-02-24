package au.com.asx.demo.controllers;

import au.com.asx.demo.dto.UserDetailDTO;
import au.com.asx.demo.AbstractTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UserInfoControllerTest extends AbstractTests {

    @Test
    public void getUserDetail_withSuccess() throws Exception {
        String uri = "/api/userdetails/10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }

    @Test
    public void getUserDetail_withAphaNumericUserId() throws Exception {
        String uri = "/api/userdetails/10a";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void getUserDetail_withNonExistingUserId() throws Exception {
        String uri = "/api/userdetails/200";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void updateUserDetail_withSuccess() throws Exception {
        String uri = "/api/userdetails/37";
        String request = "{\n" +
                "    \"title\": \"Mr\",\n" +
                "    \"firstname\": \"Sean151\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"390 George St\"\n" +
                "    }\n" +
                "}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

        UserDetailDTO userDetailDTO = mapFromJson(mvcResult.getResponse().getContentAsString(), UserDetailDTO.class);
        Assert.assertEquals("Sean151", userDetailDTO.getFirstname());
    }

    @Test
    public void updateUserDetail_withAphaNumericUserId() throws Exception {
        String uri = "/api/userdetails/37a";
        String request = "{\n" +
                "    \"title\": \"Mr\",\n" +
                "    \"firstname\": \"Sean151\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"390 George St\"\n" +
                "    }\n" +
                "}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void updateUserDetail_withNonExistingUserId() throws Exception {
        String uri = "/api/userdetails/370";
        String request = "{\n" +
                "    \"title\": \"Mr\",\n" +
                "    \"firstname\": \"Sean151\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"390 George St\"\n" +
                "    }\n" +
                "}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }


}
