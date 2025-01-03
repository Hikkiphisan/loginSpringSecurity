package org.example.spring_security.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}12").roles("USER")
                .and()
                .withUser("admin").password("{noop}12").roles("ADMIN");
    }
    //Phương thức configure(AuthenticationManagerBuilder auth): xác thực thông tin đăng nhập của người dùng.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()   //authorizeHttpRequests(): bảo mật tất các request đến dự án.
                .requestMatchers("/index").permitAll()
                // requestMatchers("/url"): chỉ định rõ request được bảo vệ theo URI.
                //permitAll(): các request không cần bảo mật, có thể truy cập bất kỳ lúc nào.
                .requestMatchers("/user**").hasRole("USER")  //dùng để phân quyền: các request được bảo mật và chỉ được truy cập nếu người dùng yêu cầu có role tương ứng.
                .requestMatchers("/admin**").hasRole("ADMIN")
                .and()
                .formLogin()  // sử dụng form login mặc đinh. Bạn có thể tự tạo và đưa vào view login riêng của dự án.
                     .loginPage("/login_sample")  // Chỉ định URL của form đăng nhập tùy chỉnh.
                     .permitAll()  // Cho phép tất cả người dùng truy cập vào trang login.
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));  //logoutRequestMatcher(): chỉ định đường link để thực thi hành động logout.
    }
}
//Phương thức configure(HttpSecurity http): cấu hình bảo mật dựa trên các yêu cầu HTTP. Mặc định các yêu cầu đều được bảo mật nhưng chúng ta có thể tự config cho các yêu cầu của dự án.