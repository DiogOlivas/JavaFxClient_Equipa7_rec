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
    
    public Long getUser() {
		return userId;
	}

	public void setUser(Long userId) {
		this.userId = userId;
	}

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getDescricao() {
        return desc;
    }

    public void setDescricao(String descricao) {
        this.desc = descricao;
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
