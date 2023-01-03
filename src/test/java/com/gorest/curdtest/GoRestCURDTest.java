package com.gorest.curdtest;

import com.gorest.testbase.TestBase;
import com.gorest.userinfo.UserSteps;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static com.gorest.utils.TestUtils.getRandomValue;
import static org.hamcrest.Matchers.hasValue;



    @RunWith(SerenityRunner.class)
    public class GoRestCURDTest extends TestBase {
        static String name = "Manan";
        static String email = "manan123" + getRandomValue() + "@gmail.com";
        static String gender = "male";
        static String status = "active";

        static int id;


        @Steps
        UserSteps userSteps;

        @Title("This method will get all users records")
        @Test
        public void test001() {
            userSteps.getUserIDs();
        }

        @Title("This method will create a new users record and verify it by its ID")
        @Test
        public void test002() {
            ValidatableResponse getId = userSteps.createUserRecord(name, email, gender, status);
            id = getId.extract().path("id");
            ValidatableResponse response = userSteps.getUserIDs();
            ArrayList<?> userId = response.extract().path("id");
            Assert.assertTrue(userId.contains(id));
        }

        @Title("This method will update the existing record")
        @Test
        public void test003() {
            status = "inactive";
            userSteps.userRecordUpdate(id, name, email, gender, status);
            ValidatableResponse response = userSteps.getSingleUser(id).statusCode(200);
            HashMap<String, ?> userRecord = response.extract().path("");
            Assert.assertThat(userRecord, hasValue("inactive"));
        }

        @Title("This method will delete the existing record")
        @Test
        public void test004() {
            userSteps.deleteUserRecord(id).statusCode(204);
            userSteps.getSingleUser(id).statusCode(404);
        }
    }

