package myejb;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import javax.ejb.Stateless;

@Stateless
public class SecretKeyEJB {
//  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//
//  public static Key getKey() {
//    return key;
//  }
//  public SecretKeyEJB(){
//    try (FileOutputStream fos = new FileOutputStream("C:\\Users\\User\\Desktop\\backend\\src\\main\\java\\utils\\secret-key")) {
//      fos.write(key.getEncoded());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  public Key getSecretKey() throws IOException {
    InputStream is = getClass().getResourceAsStream("/secret-key");
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

    return Keys.hmacShaKeyFor(reader.readLine().getBytes());
  }

  public boolean checkToken(Key secretKey, String token, String username) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(token)
          .getBody().getSubject().equals(username);
    } catch (JwtException | IllegalArgumentException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

}
