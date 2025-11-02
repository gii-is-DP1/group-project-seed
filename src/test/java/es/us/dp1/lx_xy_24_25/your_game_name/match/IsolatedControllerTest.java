package es.us.dp1.lx_xy_24_25.your_game_name.match;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.configuration.SecurityConfiguration;

@WebMvcTest(value = { MatchController.class}, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class IsolatedControllerTest {

    @MockBean
    MatchService gs;

    @Autowired
    MockMvc mvc;

    static final String BASE_URL="/api/v1/matches";


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void unfeasibleMatchCreationTest() throws JsonProcessingException, Exception{

       
       Match g=new Match(); // This Match is invalid since it has no name:
       
       ObjectMapper objectMapper=new ObjectMapper();
       
       reset(gs);

       mvc.perform(post(BASE_URL)
                 .with(csrf()).
                 contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(g)))
              .andExpect(status().isBadRequest());
       // Comprobamos que no se ha grabado el juego en la BD:
       verify(gs,never()).save(any(Match.class));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void feasibleMatchCreationTest() throws JsonProcessingException, Exception{
       Match g=creatValidMatch(); // This Match is invalid since it has no name:
       ObjectMapper objectMapper=new ObjectMapper();
       reset(gs);
       when(gs.save(any(Match.class))).thenReturn(g);


       mvc.perform(post(BASE_URL)
                 .with(csrf()).
                 contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(g)))
              .andExpect(status().isCreated());
       // Comprobamos que se ha intentado grabar el juego en la bd:
       verify(gs,times(1)).save(any(Match.class));
    }

    private Match creatValidMatch() {
       Match g=new Match();
       g.setName("Crazy smash bros session");
       return g;
    }

}

