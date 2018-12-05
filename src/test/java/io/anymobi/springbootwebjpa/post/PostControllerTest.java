package io.anymobi.springbootwebjpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class PostControllerTest {


    @Autowired
    PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getPost() throws Exception {

        Post post = new Post();
        post.setTitle("WebMvc Data Jpa");

        postRepository.save(post);

        mockMvc.perform(get("/posts/" + post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("WebMvc Data Jpa"));
    }

    @Test
    public void getPosts() throws Exception {

        createPost();

        mockMvc.perform(get("/posts/")
                .param("page", "3")
                .param("size", "10")
                .param("sort", "created,desc")
                .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.postList[0].title", is("Jpa 31")));
    }

    @Test
    public void save() {
        Post post = new Post();
        post.setTitle("onjsdnjs");
        Post savedPost = postRepository.save(post);

//        savedPost.setTitle("seumstone");

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("leaven");
        Post updatedPost = postRepository.save(postUpdate);

//        postUpdate.setTitle("seumstone");
        updatedPost.setTitle("seumstone");

        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("seumstone");

    }

    private void createPost() {

        int loopCount = 100;

        while (loopCount > 0) {

            Post post = new Post();
            post.setCreated(new Date());
            post.setTitle("Jpa " + loopCount);

            postRepository.save(post);

            loopCount--;

        }

    }

}