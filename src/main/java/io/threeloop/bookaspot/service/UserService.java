package io.threeloop.bookaspot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.threeloop.bookaspot.model.Role;
import io.threeloop.bookaspot.model.User;
import io.threeloop.bookaspot.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User register(User user) {
    user.setPassword(encodeUserPassword(user.getPassword()));

    if (userRepository.findOneByUsername(user.getUsername()) == null
        && userRepository.findOneByEmail(user.getEmail()) == null) {
      // String activation = createActivationToken(user, false);
      // user.setToken(activation);
      userRepository.save(user);
      return user;
    }

    return null;
  }

  public String encodeUserPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findOneByUsernameOrEmail(username, username);

    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    // if(requireActivation && !user.getToken().equals("1")) {
    // Application.log.error("User [" + username + "] tried to login but is not activated");
    // throw new UsernameNotFoundException(username + " has not been activated yet");
    // }
    // httpSession.setAttribute(CURRENT_USER_KEY, user);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), grantedAuthorities);
  }


  public void autoLogin(String username) {
    UserDetails userDetails = loadUserByUsername(username);
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);
    if (auth.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
  }
  
  public User getLoggedInUser() {
    return userRepository.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
}

}
