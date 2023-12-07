package com.kyamran.app.view;

import com.kyamran.app.controller.PostController;
import com.kyamran.app.model.Post;
import com.kyamran.app.model.Writer;
import com.kyamran.app.service.WriterService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PostView implements View {
    private static final String OPERATIONS_INFO = """
            ----------------------------------
            1: Save post.
            2: Update post.
            3: Get post by ID.
            4: Delete post.
            5: Get all posts.
            6: Exit.
            ----------------------------------
            """;

    private final Scanner in = new Scanner(System.in);
    private final PostController postController = new PostController();
    private final WriterService writerService = new WriterService();

    @Override
    public void run() {
        try {
            boolean appStatus = true;
            while (appStatus) {
                System.out.println(OPERATIONS_INFO);
                System.out.print("Enter a command: ");
                String operation = in.next();

                switch (operation) {
                    case "1" -> savePostView();
                    case "2" -> updatePostView();
                    case "3" -> getPostByIdView();
                    case "4" -> deletePostByIdView();
                    case "5" -> getAllPostView();
                    case "6" -> appStatus = false;
                    default -> System.out.println("Incorrect, try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Input error.");
        }
    }

    public void savePostView() {
        in.nextLine();
        System.out.print("Input content: ");
        String content = in.nextLine();
        System.out.print("Input writer ID: ");
        Long writerId = null;
        if (in.hasNextLong()) {
            writerId = in.nextLong();
        } else {
            System.out.println("You should enter a valid Writer ID.");
        }
        if (writerId != null) {
            Writer writer = writerService.getById(writerId);
            Post newPost = new Post();
            newPost.setContent(content);
            newPost.setWriter(writer);
            postController.save(newPost);
            System.out.println("Saved!");
        }
    }

    public void updatePostView() {
        System.out.print("Input post ID: ");
        Long id = in.nextLong();
        in.nextLine();
        Post postToUpdate = postController.getById(id);
        if (postToUpdate == null) {
            System.out.println("Post not found!");
            return;
        }

        System.out.print("Input new content: ");
        String content = in.nextLine();
        postToUpdate.setContent(content);
        Post updatedPost = postController.update(postToUpdate);
        System.out.println("Updated post: ");
        System.out.println(updatedPost);
    }



    public void getPostByIdView() {
        System.out.print("Input post ID: ");
        Long id = in.nextLong();
        Post post = postController.getById(id);
        System.out.println(post);
    }

    public void deletePostByIdView() {
        System.out.print("Input post ID: ");
        Long id = in.nextLong();
        postController.deleteById(id);
        System.out.println("Deleted!");
    }

    public void getAllPostView() {
        List<Post> postList = postController.getAll();
        postList.forEach(System.out::println);
    }
}