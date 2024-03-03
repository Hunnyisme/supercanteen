package com.jetchiu.supercanteen.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.commonservice.UserServiceImp;
import com.jetchiu.supercanteen.entity.Admin;
import com.jetchiu.supercanteen.entity.UserEntity;
import com.jetchiu.supercanteen.mapper.AdminMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;

@Component
public class LoginFilter extends OncePerRequestFilter {
    @Autowired
    UserServiceImp userServiceImp;
@Value("${keystring}")
    String key;
@Autowired
AdminMapper adminMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String token=request.getHeader("Authorization");
      if(token!=null){
          token=token.substring(7);
          System.out.println("拦截器接受token:"+token);
          try {
              System.out.println("密钥是"+key);
              Jws<Claims> jws=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
              if(jws.getBody().get("identity")==null)
              {
                  String account=(String)jws.getBody().get("account");
                  System.out.println("account是"+account);
                  if(account!=null){
                      UserDetails userDetails = (UserDetails) userServiceImp.loadUserByUsername(account);
                      Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
                      SecurityContextHolder.getContext().setAuthentication(authentication);
                  }
              }else{
                  String account=(String)jws.getBody().get("account");
                  QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
                  queryWrapper.eq("account",account);
                  Admin admin= adminMapper.selectOne(queryWrapper);
                  System.out.println("该用户是管理员");
                  if(account!=null){
                      User user = new User(account,admin.getPassword(), Collections.emptyList());
                      Authentication authentication=new UsernamePasswordAuthenticationToken(user,user.getPassword(),Collections.emptyList());
                      SecurityContextHolder.getContext().setAuthentication(authentication);
                  }
              }

          }catch (Exception e){
              System.out.println(e);
          }
      }
        filterChain.doFilter(request,response);
    }
}
