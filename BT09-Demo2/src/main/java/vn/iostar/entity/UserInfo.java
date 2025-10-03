package vn.iostar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;   // cột name trong DB

    @Column(nullable=false, unique=true)
    private String email;  // cột email trong DB

    @Column(nullable=false)
    private String password; // cột password trong DB

    @Column(nullable=false)
    private String roles; // cột roles trong DB

    // getter & setter đầy đủ
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
}
