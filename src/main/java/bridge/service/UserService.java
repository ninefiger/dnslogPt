package bridge.service;

import bridge.mapper.UserMapper;
import bridge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Repository
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }

    public void insertNewUser(User user) {
        User existUser = userMapper.getUserByUsername(user.getUsername());
        if (existUser == null) {
            encryptPassword(user);
            creatLogID(user);
            createUserID(user);
            createapiKey(user);
            userMapper.insert(user);
        } else {
            throw new RuntimeException("用户名已存在！");
        }
    }

    private void encryptPassword(User user) {
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
    }

    private void creatLogID(User user) {
/*        String logID = userMapper.getLastLogID();
        //修改logID为随机的
        if (logID == null) {
            user.setLogid(1);
        } else {
            user.setLogid(logID + 1);
        }*/

        for (int n=1 ; n < 10; n++){
            String logID = getRandomString(4);
            //判断logID是否在库里
            String username = userMapper.getUsernameByLogID(logID);
            System.out.println("username是什么！！！！    " + username);
            if (username == null){
                user.setLogid(logID);
                return;
            }
        }
        //如果10次循环LogId都重复了，则LogId = UserId
        user.setLogid(UUID.randomUUID().toString());
        System.out.println("LogID生成失败，临时定义为UUID。");
    }

    //增加随机的四位logId
    private static String getRandomString(int length) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private void createUserID(User user) {
        String userID = UUID.randomUUID().toString();
        user.setUserid(userID);
    }

    private void createapiKey(User user) {
        String apiKey = UUID.randomUUID().toString().replace("-", "");
        user.setApiKey(apiKey);
    }

    public String getLogIdByName(String username) {
        return userMapper.getUserByUsername(username).getLogid();
    }

    public String getapiKeyByName(String username) {
        return userMapper.getUserByUsername(username).getApiKey();
    }

    public User getUserByApiKey(String apiKey) {
        return userMapper.getUserByToken(apiKey);
    }
}
