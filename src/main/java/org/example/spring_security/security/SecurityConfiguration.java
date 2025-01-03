package org.example.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}12").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}12").roles("ADMIN");
//    }
//    //Phương thức configure(AuthenticationManagerBuilder auth): xác thực thông tin đăng nhập của người dùng.
//

    //xac thuc
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN");

    }

    //khien cho password ma hoa, tro thanh dong bam
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//phan quyen
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http     .csrf().disable()  // Thêm dòng này
                .authorizeHttpRequests()   //authorizeHttpRequests(): bảo mật tất các request đến dự án.
                .requestMatchers("/index").permitAll()
                // requestMatchers("/url"): chỉ định rõ request được bảo vệ theo URI.
                //permitAll(): các request không cần bảo mật, có thể truy cập bất kỳ lúc nào.
                .requestMatchers("/login").permitAll()

                .requestMatchers("/user**").hasRole("USER")  //dùng để phân quyền: các request được bảo mật và chỉ được truy cập nếu người dùng yêu cầu có role tương ứng.
                .requestMatchers("/admin**").hasRole("ADMIN")
                .and()
                .formLogin()  // sử dụng form login mặc đinh. Bạn có thể tự tạo và đưa vào view login riêng của dự án.
                     .loginPage("/login")  // Chỉ định URL của form đăng nhập tùy chỉnh.
                     .loginProcessingUrl("/login")  // Xử lý đăng nhập tại URL này
                .failureHandler((request, response, exception) -> {
                    System.out.println("Login failed: " + exception.getMessage());
                    response.sendRedirect("/login?error");
                })

//                      .defaultSuccessUrl("/index")    // URL chuyển hướng khi login thành công
//                      .failureUrl("/login?error")    // URL chuyển hướng khi login thất bại
//                      .usernameParameter("username") // tên parameter username trong form
//                      .passwordParameter("password") // tên parameter password trong form


                     .permitAll()  // Cho phép tất cả người dùng truy cập vào trang login.




                .and()
                .logout() //Xóa thông tin người dùng trong phiên (Session),  Xóa Cookie xác thực (Authentication Cookies)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  //logoutRequestMatcher(): chỉ định đường link để thực thi hành động logout.
                .logoutSuccessUrl("/index") // URL chuyển hướng sau logout
                .permitAll();
    }
}
//Phương thức configure(HttpSecurity http): cấu hình bảo mật dựa trên các yêu cầu HTTP. Mặc định các yêu cầu đều được bảo mật nhưng chúng ta có thể tự config cho các yêu cầu của dự án.