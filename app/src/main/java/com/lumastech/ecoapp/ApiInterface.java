package com.lumastech.ecoapp;


import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.AuthResponse;
import com.lumastech.ecoapp.Models.Comment;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.Models.Message;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.Models.Question;
import com.lumastech.ecoapp.Models.Quiz;
import com.lumastech.ecoapp.Models.LoginRequest;
import com.lumastech.ecoapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    // Authentication headers constant
    String AUTH_HEADER = "Authorization: Bearer ";

    ///////////////////////
    // PUBLIC ROUTES
    ///////////////////////

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("register")
    Call<ApiResponse<AuthResponse>> register(@Body LoginRequest request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("login")
    Call<ApiResponse<AuthResponse>> login(@Body LoginRequest request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("courses")
    Call<ApiResponse<List<Course>>> getPublicCourses();

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("courses/{courseId}")
    Call<ApiResponse<Course>> getPublicCourse(@Path("courseId") int courseId);

    ///////////////////////
    // AUTHENTICATED ROUTES
    ///////////////////////

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("user")
    Call<ApiResponse<User>> getCurrentUser(@Header("Authorization") String token);

    // COURSES
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("courses")
    Call<ApiResponse<List<Course>>> getCourses(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("courses")
    Call<ApiResponse<Course>> createCourse(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("courses/{courseId}")
    Call<ApiResponse<Course>> updateCourse(@Header("Authorization") String token, @Path("courseId") int courseId, @Body Course request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("courses/{courseId}")
    Call<ApiResponse<Void>> deleteCourse(@Header("Authorization") String token, @Path("courseId") int courseId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("courses/{courseId}/enroll")
    Call<ApiResponse<Course>> enrollInCourse(@Header("Authorization") String token, @Path("courseId") int courseId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("courses/{courseId}/complete")
    Call<ApiResponse<Course>> completeCourse(@Header("Authorization") String token, @Path("courseId") int courseId);

    // LESSONS
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("lessons")
    Call<ApiResponse<List<Lesson>>> getLessons(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("lessons")
    Call<ApiResponse<Lesson>> createLesson(@Header("Authorization") String token, @Body Lesson request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("lessons/{lessonId}")
    Call<ApiResponse<Lesson>> getLesson(@Header("Authorization") String token, @Path("lessonId") int lessonId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("lessons/{lessonId}")
    Call<ApiResponse<Lesson>> updateLesson(@Header("Authorization") String token, @Path("lessonId") int lessonId, @Body Lesson request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("lessons/{lessonId}")
    Call<ApiResponse<Void>> deleteLesson(@Header("Authorization") String token, @Path("lessonId") int lessonId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("lessons/{lessonId}/complete")
    Call<ApiResponse<Lesson>> completeLesson(@Header("Authorization") String token, @Path("lessonId") int lessonId);

    // QUIZZES
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("quizzes")
    Call<ApiResponse<List<Quiz>>> getQuizzes(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("quizzes")
    Call<ApiResponse<Quiz>> createQuiz(@Header("Authorization") String token, @Body Quiz request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("quizzes/{quizId}")
    Call<ApiResponse<Quiz>> getQuiz(@Header("Authorization") String token, @Path("quizId") int quizId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("quizzes/{quizId}")
    Call<ApiResponse<Quiz>> updateQuiz(@Header("Authorization") String token, @Path("quizId") int quizId, @Body Quiz request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("quizzes/{quizId}")
    Call<ApiResponse<Void>> deleteQuiz(@Header("Authorization") String token, @Path("quizId") int quizId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("quizzes/{quizId}/submit")
    Call<ApiResponse<Quiz>> submitQuiz(@Header("Authorization") String token, @Path("quizId") int quizId, @Body Quiz request);

    // POSTS
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("posts")
    Call<ApiResponse<List<Post>>> getPosts(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("posts")
    Call<ApiResponse<Post>> createPost(@Header("Authorization") String token, @Body Post request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("posts/{postId}")
    Call<ApiResponse<Post>> getPost(@Header("Authorization") String token, @Path("postId") int postId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("posts/{postId}")
    Call<ApiResponse<Post>> updatePost(@Header("Authorization") String token, @Path("postId") int postId, @Body Post request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("posts/{postId}")
    Call<ApiResponse<Void>> deletePost(@Header("Authorization") String token, @Path("postId") int postId);

    // COMMENTS
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("posts/{postId}/comments")
    Call<ApiResponse<List<Comment>>> getComments(@Header("Authorization") String token, @Path("postId") int postId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("posts/{postId}/comments")
    Call<ApiResponse<Comment>> createComment(@Header("Authorization") String token, @Path("postId") int postId, @Body Comment request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("comments/{commentId}")
    Call<ApiResponse<Comment>> getComment(@Header("Authorization") String token, @Path("commentId") int commentId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("comments/{commentId}")
    Call<ApiResponse<Comment>> updateComment(@Header("Authorization") String token, @Path("commentId") int commentId, @Body Comment request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("comments/{commentId}")
    Call<ApiResponse<Void>> deleteComment(@Header("Authorization") String token, @Path("commentId") int commentId);

    // MESSAGES
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("messages")
    Call<ApiResponse<List<Message>>> getMessages(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("messages")
    Call<ApiResponse<Message>> createMessage(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("messages/{messageId}")
    Call<ApiResponse<Message>> getMessage(@Header("Authorization") String token, @Path("messageId") int messageId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("messages/{messageId}")
    Call<ApiResponse<Void>> deleteMessage(@Header("Authorization") String token, @Path("messageId") int messageId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("messages/sent")
    Call<ApiResponse<List<Message>>> getSentMessages(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("messages/received")
    Call<ApiResponse<List<Message>>> getReceivedMessages(@Header("Authorization") String token);

    // QUESTIONS
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("questions")
    Call<ApiResponse<List<Question>>> getQuestions(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("questions")
    Call<ApiResponse<Question>> createQuestion(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("questions/{questionId}")
    Call<ApiResponse<Question>> getQuestion(@Header("Authorization") String token, @Path("questionId") int questionId);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @PUT("questions/{questionId}")
    Call<ApiResponse<Question>> updateQuestion(@Header("Authorization") String token, @Path("questionId") int questionId, @Body Question request);

    @Headers({"Content-Type: application/json", "accept: application/json"})
    @DELETE("questions/{questionId}")
    Call<ApiResponse<Void>> deleteQuestion(@Header("Authorization") String token, @Path("questionId") int questionId);
}