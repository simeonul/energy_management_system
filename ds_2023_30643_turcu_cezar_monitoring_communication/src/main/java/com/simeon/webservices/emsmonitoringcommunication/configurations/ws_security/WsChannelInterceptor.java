package com.simeon.webservices.emsmonitoringcommunication.configurations.ws_security;

import com.simeon.webservices.emsmonitoringcommunication.configurations.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class WsChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand cmd = accessor.getCommand();

        if (StompCommand.CONNECT == cmd) {
            String jwtToken = (String) accessor.getSessionAttributes().get("jwtToken");

            if (jwtToken != null) {
                try {
                    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                    Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

                    String email = String.valueOf(claims.get("email"));
                    String authorities = (String) claims.get("authorities");

                    Authentication auth = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception ex) {
                    throw new BadCredentialsException("The received token is invalid!");
                }
            }
        }
        return message;
    }
}
