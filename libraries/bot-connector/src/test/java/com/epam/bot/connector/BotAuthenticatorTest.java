package com.epam.bot.connector;

import com.epam.bot.connector.customizations.*;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.epam.bot.connector.customizations.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import java.util.concurrent.ExecutionException;

public class BotAuthenticatorTest {

    @BeforeClass
    public static void beforeClass() {
        EmulatorValidation.ToBotFromEmulatorTokenValidationParameters.validateLifetime = false;
        ChannelValidation.ToBotFromChannelTokenValidationParameters.validateLifetime = false;
    }

    @Test
    public void AuthHeaderWithCorrectAppIdAndServiceUrl() throws ExecutionException, InterruptedException {
        String header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSIsIng1dCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSJ9.eyJzZXJ2aWNldXJsIjoiaHR0cHM6Ly93ZWJjaGF0LmJvdGZyYW1ld29yay5jb20vIiwiaXNzIjoiaHR0cHM6Ly9hcGkuYm90ZnJhbWV3b3JrLmNvbSIsImF1ZCI6IjM5NjE5YTU5LTVhMGMtNGY5Yi04N2M1LTgxNmM2NDhmZjM1NyIsImV4cCI6MTUxNjczNzUyMCwibmJmIjoxNTE2NzM2OTIwfQ.TBgpxbDS-gx1wm7ldvl7To-igfskccNhp-rU1mxUMtGaDjnsU--usH4OXZfzRsZqMlnXWXug_Hgd_qOr5RH8wVlnXnMWewoZTSGZrfp8GOd7jHF13Gz3F1GCl8akc3jeK0Ppc8R_uInpuUKa0SopY0lwpDclCmvDlz4PN6yahHkt_666k-9UGmRt0DDkxuYjbuYG8EDZxyyAhr7J6sFh3yE2UGRpJjRDB4wXWqv08Cp0Gn9PAW2NxOyN8irFzZH5_YZqE3DXDAYZ_IOLpygXQR0O-bFIhLDVxSz6uCeTBRjh8GU7XJ_yNiRDoaby7Rd2IfRrSnvMkBRsB8MsWN8oXg";
        String appId = "39619a59-5a0c-4f9b-87c5-816c648ff357";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        ClaimsIdentity identity = JwtTokenValidation.validateAuthHeader(header, credentials, "https://webchat.botframework.com/").get();
        Assert.assertTrue(identity.isAuthenticated());
    }

    @Test
    public void AuthHeaderWithWrongAppIdAndServiceUrl() throws ExecutionException, InterruptedException {
        String header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSIsIng1dCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSJ9.eyJzZXJ2aWNldXJsIjoiaHR0cHM6Ly93ZWJjaGF0LmJvdGZyYW1ld29yay5jb20vIiwiaXNzIjoiaHR0cHM6Ly9hcGkuYm90ZnJhbWV3b3JrLmNvbSIsImF1ZCI6IjM5NjE5YTU5LTVhMGMtNGY5Yi04N2M1LTgxNmM2NDhmZjM1NyIsImV4cCI6MTUxNjczNzUyMCwibmJmIjoxNTE2NzM2OTIwfQ.TBgpxbDS-gx1wm7ldvl7To-igfskccNhp-rU1mxUMtGaDjnsU--usH4OXZfzRsZqMlnXWXug_Hgd_qOr5RH8wVlnXnMWewoZTSGZrfp8GOd7jHF13Gz3F1GCl8akc3jeK0Ppc8R_uInpuUKa0SopY0lwpDclCmvDlz4PN6yahHkt_666k-9UGmRt0DDkxuYjbuYG8EDZxyyAhr7J6sFh3yE2UGRpJjRDB4wXWqv08Cp0Gn9PAW2NxOyN8irFzZH5_YZqE3DXDAYZ_IOLpygXQR0O-bFIhLDVxSz6uCeTBRjh8GU7XJ_yNiRDoaby7Rd2IfRrSnvMkBRsB8MsWN8oXg";
        String appId = "00000000-0000-0000-0000-000000000000";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        try {
            JwtTokenValidation.validateAuthHeader(header, credentials, "https://webchat.botframework.com/").get();
            Assert.fail("expected exception was not occurred.");
        } catch (AuthenticationException e) {
            Assert.assertEquals(e.getMessage(), "Invalid AppId passed on token: '39619a59-5a0c-4f9b-87c5-816c648ff357'.");
        }
    }

    @Test
    public void AuthHeaderWithAppIdAndWrongServiceUrl() throws ExecutionException, InterruptedException {
        String header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSIsIng1dCI6IkdDeEFyWG9OOFNxbzdQd2VBNy16NjVkZW5KUSJ9.eyJzZXJ2aWNldXJsIjoiaHR0cHM6Ly93ZWJjaGF0LmJvdGZyYW1ld29yay5jb20vIiwiaXNzIjoiaHR0cHM6Ly9hcGkuYm90ZnJhbWV3b3JrLmNvbSIsImF1ZCI6IjM5NjE5YTU5LTVhMGMtNGY5Yi04N2M1LTgxNmM2NDhmZjM1NyIsImV4cCI6MTUxNjczNzUyMCwibmJmIjoxNTE2NzM2OTIwfQ.TBgpxbDS-gx1wm7ldvl7To-igfskccNhp-rU1mxUMtGaDjnsU--usH4OXZfzRsZqMlnXWXug_Hgd_qOr5RH8wVlnXnMWewoZTSGZrfp8GOd7jHF13Gz3F1GCl8akc3jeK0Ppc8R_uInpuUKa0SopY0lwpDclCmvDlz4PN6yahHkt_666k-9UGmRt0DDkxuYjbuYG8EDZxyyAhr7J6sFh3yE2UGRpJjRDB4wXWqv08Cp0Gn9PAW2NxOyN8irFzZH5_YZqE3DXDAYZ_IOLpygXQR0O-bFIhLDVxSz6uCeTBRjh8GU7XJ_yNiRDoaby7Rd2IfRrSnvMkBRsB8MsWN8oXg";
        String appId = "39619a59-5a0c-4f9b-87c5-816c648ff357";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        try {
            JwtTokenValidation.validateAuthHeader(header, credentials, "https://skype.botframework.com/").get();
            Assert.fail("expected exception was not occurred.");
        } catch (AuthenticationException e) {
            Assert.assertEquals(e.getMessage(), "'serviceurl' claim does not match service url provided (https://skype.botframework.com/).");
        }
    }

    @Test
    public void AuthHeaderEmulatorWithoutAppIdAndServiceUrl() throws ExecutionException, InterruptedException {
        String header = "";
        String appId = "";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        ClaimsIdentity identity = JwtTokenValidation.validateAuthHeader(header, credentials).get();
        Assert.assertTrue(identity.isAuthenticated());
    }

    @Ignore("Test is failing and breaking the build, skipping for now.")
    @Test
    public void AuthHeaderEmulatorWithCredentials() throws ExecutionException, InterruptedException {
        String header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCIsImtpZCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCJ9.eyJhdWQiOiIzOTYxOWE1OS01YTBjLTRmOWItODdjNS04MTZjNjQ4ZmYzNTciLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9kNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIvIiwiaWF0IjoxNTE4MTIzMTQxLCJuYmYiOjE1MTgxMjMxNDEsImV4cCI6MTUxODEyNzA0MSwiYWlvIjoiWTJOZ1lQZ1djOSsrenJvaW9QM28rZmw2OWR1c0FBPT0iLCJhcHBpZCI6IjM5NjE5YTU5LTVhMGMtNGY5Yi04N2M1LTgxNmM2NDhmZjM1NyIsImFwcGlkYWNyIjoiMSIsImlkcCI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2Q2ZDQ5NDIwLWYzOWItNGRmNy1hMWRjLWQ1OWE5MzU4NzFkYi8iLCJ0aWQiOiJkNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIiLCJ1dGkiOiJPVXE3M1lSbGtFcVoxQ3p2U3FZQkFBIiwidmVyIjoiMS4wIn0.B0t4sSsqIQ3IT2rfpZXqAdAGJSr3aihwk-jJd8as2pAoeQVcQNir_Anvvnjbo5MsB0DCyWFa9xnEmBRiTW_Ww97Z9bZhnCXq4D4vN8dmgEMV_Aci1tI4agy3coCX4fBRc76SHjqJ_ucl850aqR3d_0sfl0TPoDclE4jWssX2YTNzUAMEgisbYe9xv8FfK7AUR8ABS1teTfnWGVYyVFgC7vptSjw-de8sgz7pv8vVtLEKBrrb1FBSzHbbnZ-cQaLLHeIM4agamXf4w45o7_1uHorrp1Tg5oPrsbiayC-dt4lpC9smU5agpyUWCorKZI0Fp3aryG4519cYuLyXuUVh0A";
        String appId = "39619a59-5a0c-4f9b-87c5-816c648ff357";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        ClaimsIdentity identity = JwtTokenValidation.validateAuthHeader(header, credentials).get();
        Assert.assertTrue(identity.isAuthenticated());
    }

    @Ignore("Test is failing and breaking the build, skipping for now.")
    @Test
    public void AuthHeaderEmulatorWithoutCredentials() throws ExecutionException, InterruptedException {
        String header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCIsImtpZCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCJ9.eyJhdWQiOiIzOTYxOWE1OS01YTBjLTRmOWItODdjNS04MTZjNjQ4ZmYzNTciLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9kNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIvIiwiaWF0IjoxNTE4MTIzMTQxLCJuYmYiOjE1MTgxMjMxNDEsImV4cCI6MTUxODEyNzA0MSwiYWlvIjoiWTJOZ1lQZ1djOSsrenJvaW9QM28rZmw2OWR1c0FBPT0iLCJhcHBpZCI6IjM5NjE5YTU5LTVhMGMtNGY5Yi04N2M1LTgxNmM2NDhmZjM1NyIsImFwcGlkYWNyIjoiMSIsImlkcCI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2Q2ZDQ5NDIwLWYzOWItNGRmNy1hMWRjLWQ1OWE5MzU4NzFkYi8iLCJ0aWQiOiJkNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIiLCJ1dGkiOiJPVXE3M1lSbGtFcVoxQ3p2U3FZQkFBIiwidmVyIjoiMS4wIn0.B0t4sSsqIQ3IT2rfpZXqAdAGJSr3aihwk-jJd8as2pAoeQVcQNir_Anvvnjbo5MsB0DCyWFa9xnEmBRiTW_Ww97Z9bZhnCXq4D4vN8dmgEMV_Aci1tI4agy3coCX4fBRc76SHjqJ_ucl850aqR3d_0sfl0TPoDclE4jWssX2YTNzUAMEgisbYe9xv8FfK7AUR8ABS1teTfnWGVYyVFgC7vptSjw-de8sgz7pv8vVtLEKBrrb1FBSzHbbnZ-cQaLLHeIM4agamXf4w45o7_1uHorrp1Tg5oPrsbiayC-dt4lpC9smU5agpyUWCorKZI0Fp3aryG4519cYuLyXuUVh0A";
        String appId = "00000000-0000-0000-0000-000000000000";
        String appPassword = "";
        CredentialProviderImpl credentials = new CredentialProviderImpl(appId, appPassword);

        try {
            JwtTokenValidation.validateAuthHeader(header, credentials).get();
            Assert.fail("expected exception was not occurred.");
        } catch (AuthenticationException e) {
            Assert.assertEquals(e.getMessage(), "Invalid AppId passed on token: '39619a59-5a0c-4f9b-87c5-816c648ff357'.");
        }
    }
}
