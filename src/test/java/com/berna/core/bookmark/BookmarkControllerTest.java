package com.berna.core.bookmark;


import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import com.berna.core.model.User;
import javafx.application.Application;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
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


 /*   @Before
    public void setup() throws Exception {
       this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookmarkJpaRepository.deleteAllInBatch();
        this.userJpaRepository.deleteAllInBatch();

        this.user=userJpaRepository.save(new User(name,))

    } */

}