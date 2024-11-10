package es.us.dp1.lx_xy_24_25.your_game_name.match;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SocialMatchControllerTest {
     @Autowired
     private WebApplicationContext context;
     private MockMvc mockMvc;
     @Autowired
     MatchService gs;

     static final String BASE_URL="/api/v1/matches";


     @BeforeEach
     public void setup() {
          mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();
     }

     @Test
     @Transactional
     @WithMockUser(username = "Shigeru", authorities = {"ADMIN"})
     public void feasibleMatchCreationTest() throws JsonProcessingException, Exception{
          Match g=creatValidMatch(); // This Match is invalid since it has no name:          
          ObjectMapper objectMapper=new ObjectMapper();

          mockMvc.perform(post(BASE_URL)
                              .with(csrf()).
                              contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(g)))
                         .andExpect(status().isCreated());
          // Comprobamos que se ha grabado efectivamente el juego en la bd:
          assertFalse(gs.getMatchesByName(g.getName()) == null);
     }

     @Test
     @Transactional
     @WithAnonymousUser
     public void feasibleMatchCreationWithoutPriviledgesTest() throws JsonProcessingException, Exception{
          Match g=creatValidMatch(); // This Match is invalid since it has no name:          
          g.setName("Turtles in time!");
          ObjectMapper objectMapper=new ObjectMapper();

          mockMvc.perform(post(BASE_URL)
                              .with(csrf()).
                              contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(g)))
                         .andExpect(status().isUnauthorized());
          // Comprobamos que no se ha grabado el juego en la bd:
          assertTrue(gs.getMatchesByName(g.getName()).isEmpty());
     }
   
     private Match creatValidMatch() {
          Match g=new Match();
          g.setName("Crazy smash bros session");
          return g;
     }
}
