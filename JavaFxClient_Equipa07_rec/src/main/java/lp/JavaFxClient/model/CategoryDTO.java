package lp.JavaFxClient.model;

import java.util.List; //

public class CategoryDTO {
    private Long id;
    private Long userId;
    private String name;
    private String desc;
    private double budget;
    private List<Long> transactionIds;

    public CategoryDTO() {}

    public CategoryDTO(Long id, Long userId, String name, String desc, double budget, List<Long> transactionIds) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.desc = desc;
        this.budget = budget;
        this.transactionIds = transactionIds;
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserid() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Long> getTransactions() {
        return transactionIds;
    }

    public void setGastos(List<Long> transactionIds) {
        this.transactionIds = transactionIds;
    }
}