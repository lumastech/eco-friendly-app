# Project Documentation

This document provides a detailed description of the classes and their functions in the Eco-Friendly App.

## Core Components

### `Api`

This class is responsible for creating and configuring the Retrofit instance for making API calls.

- **`getRetrofit()`**: Creates and configures a `Retrofit` instance with a base URL, a Gson converter factory, and an OkHttpClient with a logging interceptor.
- **`apiCall()`**: Returns an instance of the `ApiInterface`.
- **`getUnsafeOkHttpClient()`**: Creates an `OkHttpClient` that trusts all SSL certificates. This is useful for development but should not be used in production.

### `ApiInterface`

This interface defines the API endpoints for the application using Retrofit annotations. It includes endpoints for:

- **Authentication**: `register`, `login`
- **Courses**: `getPublicCourses`, `getPublicCourse`, `getCourses`, `createCourse`, `updateCourse`, `deleteCourse`, `enrollInCourse`, `completeCourse`
- **Lessons**: `getLessons`, `createLesson`, `getLesson`, `updateLesson`, `deleteLesson`, `completeLesson`
- **Quizzes**: `getQuizzes`, `createQuiz`, `getQuiz`, `updateQuiz`, `deleteQuiz`, `submitQuiz`
- **Posts**: `getPosts`, `createPost`, `getPost`, `updatePost`, `deletePost`
- **Comments**: `getComments`, `createComment`, `getComment`, `updateComment`, `deleteComment`
- **Messages**: `getMessages`, `createMessage`, `getMessage`, `deleteMessage`, `getSentMessages`, `getReceivedMessages`
- **Questions**: `getQuestions`, `createQuestion`, `getQuestion`, `updateQuestion`, `deleteQuestion`

### `ApiErrorInterceptor`

This class implements an `Interceptor` to handle API errors. It intercepts the response, and if the response is not successful, it parses the error body and throws an `ApiException`.

- **`intercept(Chain chain)`**: Intercepts the response and handles errors.
- **`ApiException`**: A custom exception class to represent API errors.

### `Utility`

This class provides utility functions used throughout the application.

- **`writeToFile(String filename, String content)`**: Writes content to a `SharedPreferences` file.
- **`readFile(String filename)`**: Reads content from a `SharedPreferences` file.
- **`dialog(String message)`**: Displays an `AlertDialog` with a message.
- **`checkResponse(int code, String message)`**: Checks the HTTP response code and displays an appropriate message.
- **`getUser()`**: Retrieves the current user'''s data from `SharedPreferences`.
- **`putUser(User user)`**: Stores the user'''s data in `SharedPreferences`.
- **`logout()`**: Clears the user'''s data and navigates to the `LoginActivity`.
- **`setError(View view)`**: Sets an error background for a `View`.
- **`removeError(View view)`**: Removes the error background from a `View`.
- **`token()`**: Retrieves the authentication token from `SharedPreferences`.
- **`getUsername()`**: Retrieves the username from `SharedPreferences`.
- **`showDatePickerDialog(EditText etSaleDate)`**: Displays a `DatePickerDialog`.
- **`vibrate(int duration)`**: Vibrates the device for a specified duration.

## Activities

### `LauncherActivity`

This is the first activity that is launched when the app starts. It checks if the user is authenticated and navigates to either the `LoginActivity` or the `MainActivity`.

- **`onCreate(Bundle savedInstanceState)`**: Initializes the activity and calls the `auth()` method.
- **`onResume()`**: Calls the `auth()` method when the activity is resumed.
- **`auth()`**: Checks the user'''s authentication status and navigates to the appropriate activity.

### `LoginActivity`

This activity allows the user to log in to the application.

- **`onCreate(Bundle savedInstanceState)`**: Initializes the activity and sets up the login form.
- **`loginUser(LoginRequest request)`**: Makes an API call to log in the user.

### `RegisterActivity`

This activity allows the user to register for a new account.

- **`onCreate(Bundle savedInstanceState)`**: Initializes the activity and sets up the registration form.
- **`registerUser(LoginRequest request)`**: Makes an API call to register the user.

### `MainActivity`

This is the main activity of the application, which hosts the navigation drawer and the main content.

- **`onCreate(Bundle savedInstanceState)`**: Initializes the activity, sets up the toolbar, navigation drawer, and floating action button.
- **`onCreateOptionsMenu(Menu menu)`**: Inflates the options menu.
- **`onSupportNavigateUp()`**: Handles the up navigation.
- **`onButtonClicked(int id)`**: Handles navigation events from fragments.
- **`onOptionsItemSelected(MenuItem item)`**: Handles options menu item clicks.

## Fragments

### `HomeFragment`

This fragment displays the home screen of the application, including the user'''s points, a list of courses, and buttons to navigate to other sections of the app.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and sets up the click listeners.
- **`setModules(View view)`**: Sets up the `RecyclerView` for displaying the list of courses.

### `CoursesFragment`

This fragment displays a list of all available courses.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and fetches the list of courses.
- **`fetchCourses()`**: Makes an API call to get the list of courses.
- **`setViews()`**: Sets up the `RecyclerView` with the fetched courses.

### `LessonsFragment`

This fragment displays a list of lessons for a specific course.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and fetches the list of lessons.
- **`fetchData()`**: Makes an API call to get the list of lessons.
- **`setViews()`**: Sets up the `RecyclerView` with the fetched lessons.

### `LessonFragment`

This fragment displays the content of a single lesson.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and displays the lesson content.

### `QuizFragment`

This fragment displays a quiz for a specific lesson.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and sets up the quiz.
- **`setView()`**: Sets up the quiz view with the current question and options.
- **`updateOptView()`**: Updates the view of the options based on the user'''s choice.
- **`disableOpt()`**: Disables the quiz options.
- **`enableOpt()`**: Enables the quiz options.
- **`refreshOpt()`**: Resets the quiz options.
- **`popBack(int id)`**: Pops the back stack.
- **`fetchData()`**: Makes an API call to get the quiz data.
- **`completeLeason(long id)`**: Makes an API call to mark the lesson as complete.

### `ForumsFragment`

This fragment displays a list of forum posts.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and fetches the list of posts.
- **`fetchCourses()`**: Makes an API call to get the list of posts.
- **`setViews()`**: Sets up the `RecyclerView` with the fetched posts.
- **`getDummyData()`**: Returns a list of dummy posts for testing.

### `ForumFragment`

This fragment displays a single forum post and its comments.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and displays the post and comments.
- **`postComment(String comment)`**: Makes an API call to post a new comment.

### `QuestionsFragment`

This fragment displays a list of questions and answers.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and fetches the list of questions.
- **`fetchData()`**: Makes an API call to get the list of questions.
- **`setViews()`**: Sets up the `RecyclerView` with the fetched questions.
- **`getDummyData()`**: Returns a list of dummy questions for testing.

### `AskFragment`

This fragment allows the user to ask a new question.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and sets up the form for asking a question.
- **`postQuestion()`**: Makes an API call to post a new question.

### `ChatsFragment`

This fragment displays a list of chats.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and sets up the `RecyclerView` with a list of chats.
- **`generateDummyChats()`**: Returns a list of dummy chats for testing.

### `ChatFragment`

This fragment displays a single chat conversation.

- **`onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`**: Inflates the layout for the fragment.
- **`onViewCreated(View view, Bundle savedInstanceState)`**: Initializes the views and sets up the `RecyclerView` with the chat messages.

## Adapters

### `CourseAdapter`

A `RecyclerView.Adapter` for displaying a list of courses.

### `LessonAdapter`

A `RecyclerView.Adapter` for displaying a list of lessons.

### `PostAdapter`

A `RecyclerView.Adapter` for displaying a list of forum posts.

### `CommentAdapter`

A `RecyclerView.Adapter` for displaying a list of comments.

### `QuestionAdapter`

A `RecyclerView.Adapter` for displaying a list of questions and answers.

### `ChatsAdapter`

A `RecyclerView.Adapter` for displaying a list of chats.

### `ChatAdapter`

A `RecyclerView.Adapter` for displaying a list of chat messages.

## Models

The application uses the following data models:

- `ApiResponse`: A generic class for API responses.
- `AuthResponse`: A model for authentication responses.
- `Chat`: A model for a chat conversation.
- `Comment`: A model for a comment.
- `Course`: A model for a course.
- `Lesson`: A model for a lesson.
- `LoginRequest`: A model for a login request.
- `Message`: A model for a chat message.
- `Post`: A model for a forum post.
- `Question`: A model for a question.
- `Quiz`: A model for a quiz.
- `RegisterResponse`: A model for a registration response.
- `ResponseData`: A generic class for response data.
- `User`: A model for a user.