package com.example.sparta.dibsTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sparta.domain.dibs.controller.DibsController;
import com.example.sparta.domain.dibs.service.DibsService;
import com.example.sparta.domain.store.controller.StoreController;
import com.example.sparta.domain.store.dto.CreateStoreRequestDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.global.MockSpringSecurityFilter;
import com.example.sparta.global.config.WebSecurityConfig;
import com.example.sparta.global.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {DibsController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class DibsControllerTest {
  public MockMvc mvc;
  public Principal mockPrincipal;
  @MockBean
  DibsService Service;
  @Autowired
  public WebApplicationContext context;
  @Autowired
  public ObjectMapper objectMapper;
  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity(new MockSpringSecurityFilter()))
        .build();
  }

  private void mockUserSetup() {
    // Mock 테스트 유져 생성
    User testUser = new User("user", "user", "user@user.com","여기 : 삽니다");

    UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
    mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
  }

  @Test
  @DisplayName("Create dibs")
  void test1() throws Exception {
    //given
    this.mockUserSetup();

    // when - then
    this.mvc.perform(post("/dibs/store/1")
            .principal(mockPrincipal)
        )
        .andExpect(status().is(201))
        .andDo(print());
  }

  @Test
  @DisplayName("remove dibs")
  void test2() throws Exception {
    //given
    this.mockUserSetup();

    // when - then
    this.mvc.perform(delete("/dibs/store/1")
            .principal(mockPrincipal)
        )
        .andExpect(status().is(200))
        .andDo(print());
  }
}
