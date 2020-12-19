import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;

public class ApiTests {

//    Cover the next endpoints with tests and include into in CI pipeline:
//    - Upload
//    - GetFileMetadata
//    - Delete file

    @Test
    public void uploadFileTest() {
        // rest assured adds charset specification by default which is not expected by dropbox and
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        File file = new File("src/main/resources/sample.jpg");
        Map<String,String> path = new HashMap<>();
        String apiArgs = "{\"mode\":\"add\",\"autorename\":true,\"mute\":false,\"path\":\"/sample.jpg\"}";

        given()
          .headers("Authorization","Bearer " + Config.bearerToken,
                  "Content-Type", "application/octet-stream",
                  "Dropbox-API-Arg", apiArgs)
          .body(file)
        .when()
          .post("https://content.dropboxapi.com/2/files/upload")
        .then()
          .statusCode(200);
    }

    @Test
    public void getFileMetadataTest() {
        Map<String,String> path = new HashMap<>();
        path.put("path", "/sample.jpg");

        given()
          .headers("Authorization","Bearer " + Config.bearerToken,
                  "Content-Type", "application/json")
          .body(path)
        .when()
          .post("https://api.dropboxapi.com/2/files/get_metadata")
        .then()
          .statusCode(200);
    }

    @Test
    public void deleteFileTest() {
        Map<String,String> path = new HashMap<>();
        path.put("path", "/sample.jpg");

        given()
          .headers("Authorization","Bearer " + Config.bearerToken,
                  "Content-Type", "application/json")
          .body(path)
        .when()
          .post("https://api.dropboxapi.com/2/files/delete_v2")
        .then()
          .statusCode(200);
    }

    public static void main() {

    }
}
