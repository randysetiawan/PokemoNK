
public class User {
	private int userId, age;
	private String username, name, email, gender, password;
	private static User user = null;
	
	public static void logIn(int userId, String username, String name, int age, String email, String gender, String password) {
		if(user == null) {
			user = new User(userId, username, name, age, email, gender, password);
			System.out.println(user.getUsername() +" successfully log in");
		} else {
			System.out.println(user.getUsername() +" still active");
		}
	}
	
	public static void logOut() {
		if(user != null) {
			System.out.println(user.getUsername() +" successfully log out");
			user = null;
		} else {
			System.out.println("No active user");
		}
	}
	
	public static User getUser() {
		if(user != null) {
			System.out.println("DB -- Retrieving from " +user.getUsername());
			return user;
		} else {
			System.out.println("No active user");
			return null;
		}
	}

	private User(int userId, String username, String name, int age, String email, String gender, String password) {
		this.userId = userId;
		this.age = age;
		this.username = username;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

