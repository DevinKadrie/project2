package example.controllers;

import example.models.Post;
import example.models.UserAccount;
import example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Posts model. This class defines the
 * endpoints for a webapp to get information from the database.
 * in this case we will use the post controller for when a user
 * wants to create, update and/or delete a posts. Also we will be
 * getting all of the posts to fill out the main feed.
 * TODO: create, get, getall, update and delete
 */
@RestController
@RequestMapping("/posts")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
//@SessionAttributes("loggedInUser")
public class PostController {

    private PostService postService;
    private UserService userService;
    private UploadService uploadService;

    /**
     * Create new post. This endpoint will receive a post
     * from a user.
     *
     */
    @PostMapping(value = "/create-post")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Post createNewPost(@RequestBody Post incomingPost, HttpSession session) {
        System.out.println("creating a new post...");

        UserAccount author = (UserAccount) session.getAttribute("currentUser");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // The ids get messed up unless it's created newly in the server
        Post newPost = new Post(incomingPost.getText(), currentTime, incomingPost.getMediaURL(), author);

        postService.addPost(newPost);

        System.out.println("...post created!");
        return newPost;
    }

    /**
     * Return all of the posts for every registered user
     * that has posted something. will be used in the
     * regular feed where a user can see other users
     * posts
     *
     * @return list of all posts
     */
    @GetMapping(value = "/getAllPosts")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Post> getAllPosts() {

        System.out.println("getting all posts");

        return postService.getAllPosts();
    }

    /**
     * Return all of the posts related to a specific user posts
     * by using their id. This will be used to populate a user
     * profile feed where they can see all of their posts.
     *
     * @param id userID
     * @return list of post
     */
    @GetMapping(value = "/getAllUserPost")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Post> getAllUsersPosts(@RequestParam("id") int id) {

        return postService.getAllUserPosts(id);
    }

    /**
     * Delete a post. TODO: flesh out more
     * @param deletePost
     */
    @DeleteMapping(value = "/deletePost")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@RequestBody Post deletePost) {

        postService.deletePosts(deletePost);
    }

    /**
     * No args constructors
     */
    public PostController() {

    }

    /**
     * Initialize a post service implementation
     *
     * @param postService PostsServiceImpl
     */
    public PostController(PostServiceImpl postService) {

        this.postService = postService;
    }

    /**
     * Get post service impl
     *
     * @return PostServiceImpl
     */
    public PostService getPostService() {
        return postService;
    }

    /**
     * Set post service implementation
     *
     * @param postService PostServiceImpl
     */
    @Autowired
    public void setPostService(PostService postService) {

        this.postService = postService;
    }

    /**
     * Get user service
     *
     * @return UserService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Set user service
     *
     * @param userService UserService
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
