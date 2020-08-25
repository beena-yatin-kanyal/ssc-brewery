package guru.sfg.brewery.security.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class AuthenticationFailureBadCredentialsListener {

    @EventListener
    public void listenFailure(AuthenticationFailureBadCredentialsEvent badCredentialsEvent) {
        log.debug("Login attempt failed due to the bad credentials provided by the user.");
        if (badCredentialsEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {
            final UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) badCredentialsEvent.getSource();

            if (authenticationToken.getPrincipal() instanceof String) {
                final String principalName = (String) authenticationToken.getPrincipal();
                if (!StringUtils.isEmpty(principalName)) {
                    log.debug("Attempted name= " + principalName);
                }
            }

            if (authenticationToken.getDetails() instanceof WebAuthenticationDetails) {
                final WebAuthenticationDetails details = (WebAuthenticationDetails) authenticationToken.getDetails();
                log.debug("Source IP: " + details.getRemoteAddress());
            }
        }
    }
}
