package com.berna.core.bookmark;


import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import com.berna.core.model.User;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookmarkControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf-8"));

        private MockMvc mockMvc;

        private String name="Berna";

        private HttpMessageConverter mappingJackson2HttpMessageConverter;

        private User user;

        private List<Bookmark> bookmarkList = new ArrayList<Bookmark>();

        @Autowired
        private BookmarkJpaRepository bookmarkJpaRepository;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private UserJpaRepository userJpaRepository;


        @Autowired
        void setConverters(HttpMessageConverter<?>[] converters){
            this.mappingJackson2HttpMessageConverter= Arrays.asList(converters).stream()
                    .filter(hmc->hmc instanceof MappingJackson2HttpMessageConverter)
                    .findAny()
                    .orElse(null);
            assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
        }


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookmarkJpaRepository.deleteAllInBatch();
        this.userJpaRepository.deleteAllInBatch();

        this.user=userJpaRepository.save(new User(name,"DID",15000L));
        this.bookmarkList.add(bookmarkJpaRepository.save(new Bookmark(user,"http://bookmark.com/10/"+name,"Description")));
    }

    @Test
    public void userNotFound() throws Exception {
          mockMvc.perform(post("/george/bookmarks/")
                  .content(this.json(new Bookmark(null,null,null)))
                  .contentType(contentType))
                  .andExpect(status().isNotFound());
    }

    @Test
    public void readBookmark() throws Exception{
         mockMvc.perform(get("/"+name+"/bookmarks/"+this.bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect((ResultMatcher) jsonPath("$.id",is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect((ResultMatcher) jsonPath("$uri",is("http://bookmark.com/10/"+user)))
                .andExpect((ResultMatcher) jsonPath("$description",is("Description")));
    }

    @Test
    public void readBookmarks() throws Exception{
            mockMvc.perform(get("/"+name+ "/bookmarks"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$",hasSize(1)))
                    .andExpect((ResultMatcher) jsonPath("$[0].id",is(this.bookmarkList.get(0).getId().intValue())))
                    .andExpect((ResultMatcher) jsonPath("$[0].uri",is("http://bookmark.com/10/"+ name)))
                    .andExpect((ResultMatcher) jsonPath("$[0].description",is("Description")));

    }



    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
   }
}