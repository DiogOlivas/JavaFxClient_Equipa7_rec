package lp.JavaFxClient.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
	  private Long id;
		private String username;
		private String email;
		private double budget;
		private List<Long> categoryIds;
		private List<Long> transactionIds;
		
		public UserDTO() {}

		public UserDTO(Long id, String username, String email, double budget, String password, List<Long> categoryIds,
				List<Long> transactionIds) {
			this.id = id;
			this.username = username;
			this.email = email;
			this.budget = budget;
			this.categoryIds = categoryIds;
			this.transactionIds = transactionIds;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public double getBudget() {
			return budget;
		}

		public void setBudget(double budget) {
			this.budget = budget;
		}

		public List<Long> getCategoryIds() {
			return categoryIds;
		}

		public void setCategoryIds(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
		}

		public List<Long> getTransactionIds() {
			return transactionIds;
		}

		public void setTransactionIds(List<Long> transactionIds) {
			this.transactionIds = transactionIds;
		}
		
}


