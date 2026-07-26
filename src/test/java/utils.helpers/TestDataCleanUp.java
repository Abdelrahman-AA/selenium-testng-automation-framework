package utils.helpers;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class TestDataCleanUp {

    private static final String changePasswordURL = "https://www.adactinhotelapp.com/ChangePassword.php";

    private TestDataCleanUp() {
    }

    public static boolean resetPasswordViaApi(String capturedSessionId, String temporaryPassword, String originalPassword) {
        if (capturedSessionId == null || capturedSessionId.isEmpty()) {
            return false;
        }

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            String encodedTempPass = URLEncoder.encode(temporaryPassword, StandardCharsets.UTF_8);
            String encodedOrigPass = URLEncoder.encode(originalPassword, StandardCharsets.UTF_8);

            String requestBody = "current_pass=" + encodedTempPass
                    + "&new_password=" + encodedOrigPass
                    + "&re_password=" + encodedOrigPass
                    + "&change_password_Submit=Submit";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(changePasswordURL))
                    .timeout(Duration.ofSeconds(10))
                    .header("Cookie", "PHPSESSID=" + capturedSessionId)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 1. Check HTTP Status 200
            if (response.statusCode() != 200) {
                return false;
            }

            // 2. Validate Response Body text to ensure PHP actually processed it successfully
            String responseBody = response.body();
            return responseBody != null && responseBody.contains("successfully updated");

        } catch (Exception e) {
            return false;
        }
    }
}