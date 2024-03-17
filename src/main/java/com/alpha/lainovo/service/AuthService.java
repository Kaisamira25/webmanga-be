package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.LoginDTO;
import com.alpha.lainovo.model.User;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.UserInterface;
import com.alpha.lainovo.utilities.customUserDetails.CustomUserDetails;
import com.alpha.lainovo.utilities.customUserDetails.GetUserInfo;
import com.alpha.lainovo.utilities.token.GenerateToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final GenerateToken generateToken;

    private final UserInterface userInterface;

    private final CreateAndUpdateInterface<Integer, User> update;

    private final PasswordEncoder encoder;

    private final GetUserIdByRequestService getUserIdByRequestService;

    @Value("${jwt.refreshtoken.expiration}")
    private long JWT_REFRESH_EXPIRATION;

    private final CookieService cookieService;

    public Object login(LoginDTO loginDTO, HttpServletRequest request){
        log.info("------> Login start working");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userInterface.findByEmail(customUserDetails.getEmail());
            customUserDetails.setUserId(user.getUserid());
            user.setUserToken(generateToken.generateRefreshToken(customUserDetails));
            update.update(user.getUserid(),user);
            String jwt = generateToken.generateAccessToken(customUserDetails);
            request.getSession().setAttribute("email",loginDTO.email());
            Map<String, String> list = new HashMap<>();
            list.put("accessToken", jwt);
            list.put("refreshToken", user.getUserToken());

            cookieService.add("refresh-token", user.getUserToken(), (int) JWT_REFRESH_EXPIRATION);
            log.info("------> Login success");
            return list;
        }
        log.info("Login null");
        return null;
    }

    public Integer validate(LoginDTO loginDTO){
        User user = userInterface.findByEmail(loginDTO.email());
        if (user == null) {
            log.info("------> Login: User does not exist");
            return 0;
        } else if (encoder.matches(loginDTO.password(), user.getPassword()) && user.getIsVerify()){
            log.info("------> Login successful with email: {}",loginDTO.email());
            return 1;
        }else if (user.getIsVerify() == false){
            log.info("------> Login fail because this email verify == false");
            return 2;
        }
        log.info("------> Login: exceptions: {}",user.getEmail());
        return 3;
    }
    public void logout(HttpServletRequest request){
        Integer userId = getUserIdByRequestService.getUserIdByRequest(request);
        log.info("UserId: {}",userId);
        User user = userInterface.findById(userId);
        log.info("User: {}",userInterface.findById(userId));
        user.setUserToken("");
        update.update(userId,user);
    }
}
