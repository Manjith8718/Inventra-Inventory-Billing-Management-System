package Models;
public class Owner {
     private  int owner_id;
     private String email;
     private String password;

     public Owner(){}
     public Owner(String email, String password) {
         this.email = email;
         this.password = password;
     }

    public Owner(int ownerId, String email, String password) {
        this.owner_id = ownerId;
        this.email = email;
        this.password = password;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setOwnerId(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
