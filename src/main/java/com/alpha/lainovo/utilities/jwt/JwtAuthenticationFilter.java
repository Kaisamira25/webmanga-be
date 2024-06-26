package com.alpha.lainovo.utilities.jwt;

import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.repository.AdminRepository;
import com.alpha.lainovo.repository.CustomerRepository;
import com.alpha.lainovo.utilities.customUserDetails.CustomAdminDetailsService;
import com.alpha.lainovo.utilities.customUserDetails.CustomUserDetailsService;
import com.alpha.lainovo.utilities.customUserDetails.GetCustomerInfo;
import com.alpha.lainovo.utilities.token.ValidateToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

    @Slf4j
    //@RequiredArgsConstructor
    public class JwtAuthenticationFilter extends OncePerRequestFilter {
        @Autowired
        private AdminRepository adminRepository;
        @Autowired
        private CustomerRepository customerRepository;
        @Autowired
        private GetCustomerInfo getCustomerInfo;
        @Autowired
        private ValidateToken validateToken;
        @Autowired
        private CustomUserDetailsService customUserDetailsService;
        @Autowired
        private CustomAdminDetailsService customAdminDetailsService;
        public static String getAccessTokenFromRequestAuthorization(HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");
            // Check Authorization JWT
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")){
                return bearerToken.substring(7);
            }
            return null;
        }
        public static String getRefreshTokenFromCookie(HttpServletRequest request){
            String token = request.getHeader("refreshToken");
    
            if (token != null){
                return token;
            }
            return null;
        }
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            log.info("JwtAuthenticationFilter: doFilterInternal | Start");
            try {
                String jwt = getAccessTokenFromRequestAuthorization(request);
                if (StringUtils.hasText(jwt) && validateToken.validateToken(jwt)){
                    log.info("Validate token: {}",validateToken.validateToken(jwt));
                    Integer customerId = getCustomerInfo.getCustomerId(jwt);
                    Integer adminId = getCustomerInfo.getAdminId(jwt);
                    log.info("customerId: {}",customerId);
                    log.info("adminId: {}",adminId);
    
                    if (customerId == null) {
                        Optional<Admin> admin = adminRepository.findById(adminId);
                        if (admin.isPresent()){
                            UserDetails adminDetails = customAdminDetailsService.loadUserByUsername(admin.get().getAccountName());
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminDetails, null, adminDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    } else {
                        Optional<Customer> customer = customerRepository.findById(customerId);
                        if (customer.isPresent()){
                            UserDetails userDetails = customUserDetailsService.loadUserByUsername(customer.get().getEmail());
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            }catch (Exception e){
                log.error("JwtAuthenticationFilter: doFilterInternal | Error: {}",e.getMessage());
                e.printStackTrace();
            }
            filterChain.doFilter(request,response);
        }
    }
