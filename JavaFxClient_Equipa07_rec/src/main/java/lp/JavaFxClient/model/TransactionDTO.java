package lp.JavaFxClient.model;

import java.time.LocalDate;

public class TransactionDTO {
	private Long id;
    private double value;
    private LocalDate date;
    private String description;
    private Long userId; 
    private String paymentMethod; 
    private Long categoryId; 

    public TransactionDTO() {}

    public TransactionDTO(Long id, double value, LocalDate date, String description, Long userId, String paymentMethod, Long categoryId) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.userId = userId;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.categoryId = categoryId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
