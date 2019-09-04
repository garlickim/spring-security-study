package com.garlickim.SpringSecurityStudy.account;

import com.garlickim.SpringSecurityStudy.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * <pre>
 * 1. Summary : MockMvc를 사용하여 Spring-Security 테스트
 * 2. Details : MockMvc를 사용하여 Spring-Security 테스트
 *
 * 3. LastModifiedDate :
 * 4. LastReviewedDate :
 * 5. History
 *    2019-09-04 오후 8:53 .. writing comments
 *
 * </pre>
 * @return
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    @WithAnonymousUser  // anotation 방식으로 user 정보 set 하는 방법
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test   // with 함수를 사용하여 직접 user 정보를 set 하는 방법
    public void test_index_user() throws Exception
    {
        this.mockMvc.perform(get("/").with(user("garlic").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUser   // user 정보를 담고 있는 WithUser interface를 생성하여 @WithUser annotation으로 테스트 하는 방법
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "garlic", roles = "ADMIN")
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional  // DB 테스트 시, 데이터가 dependancy 하지 않도록 @Transaction을 붙여주는 것이 좋다.
    public void login_success() throws Exception {
        String username = "garlic";
        String password = "1234";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_success2() throws Exception {
        String username = "garlic";
        String password = "1234";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional()
    public void login_fail() throws Exception {
        String username = "garlic";
        String password = "1234";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
