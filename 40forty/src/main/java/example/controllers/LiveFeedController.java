package example.controllers;

import example.models.LiveFeed;
import example.models.OutputMessage;
import example.models.Post;
import example.models.UserAccount;
import example.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LiveFeedController {

    private PostService postService;
//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public OutputMessage send(LiveFeed liveFeed) throws Exception {
//        System.out.println("live feed has fired");
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        return new OutputMessage(liveFeed.getFrom(), liveFeed.getText(), time);
//    }

    @MessageMapping("/live-feed")
    @SendTo("/topic/messages")
    public Post send(Post post) {
        System.out.println("live feed has fired");
        System.out.println(post);
//        postService.addPost(post);
        return post;
    }

    public LiveFeedController() {
    }

    @Autowired
    public LiveFeedController(PostService postService) {
        this.postService = postService;
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }
}
