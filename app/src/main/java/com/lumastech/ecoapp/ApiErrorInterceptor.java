package com.lumastech.ecoapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiErrorInterceptor implements Interceptor {
    private final Gson gson = new Gson();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (!response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String errorBody = "";

            try {
                if (responseBody != null) {
                    errorBody = responseBody.string();
                    // Restore the body after reading it
                    response = response.newBuilder()
                            .body(ResponseBody.create(responseBody.contentType(), errorBody))
                            .build();

                    // Modern Gson parsing approach
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(errorBody).getAsJsonObject();
                    String errorMessage = jsonObject.has("message")
                            ? jsonObject.get("message").getAsString()
                            : response.message();

                    throw new ApiException(
                            response.code(),
                            errorMessage,
                            null
                    );
                }
            } catch (Exception e) {
                throw new ApiException(
                        response.code(),
                        response.message(),
                        null
                );
            }

            // Fallback if all else fails
            throw new ApiException(
                    response.code(),
                    response.message(),
                    null
            );
        }
        return response;
    }
    public static class ApiException extends IOException {
        private final int code;
        private final String description;
        private final Map<String, List<String>> details;

        public ApiException(int code, String description, Map<String, List<String>> details) {
            super(description);
            this.code = code;
            this.description = description;
            this.details = details;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public Map<String, List<String>> getDetails() {
            return details;
        }

        public String getFirstDetail() {
            if (details != null && !details.isEmpty()) {
                List<String> firstErrorList = details.values().iterator().next();
                if (firstErrorList != null && !firstErrorList.isEmpty()) {
                    return firstErrorList.get(0);
                }
            }
            return description;
        }
    }

}
